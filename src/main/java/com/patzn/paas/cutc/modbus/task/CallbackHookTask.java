package com.patzn.paas.cutc.modbus.task;

import com.patzn.paas.cutc.config.ServerConfig;
import com.patzn.paas.cutc.constants.Constants;
import com.patzn.paas.cutc.server.entity.ModbusRegisterValue;
import com.patzn.paas.cutc.server.vo.CallbackHookVO;
import com.patzn.paas.cutc.ui.model.SettingsWindowModel;
import com.patzn.paas.cutc.utils.json.JacksonUtils;
import com.patzn.paas.cutc.utils.retry.RetryHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
public class CallbackHookTask implements Runnable {

    private final List<ModbusRegisterValue> resultList;

    private final boolean enabledFlag;

    private final String callbackHookUrl;

    private RestTemplate restTemplate;

    public CallbackHookTask(List<ModbusRegisterValue> resultList, SettingsWindowModel settingsWindowModel, ServerConfig serverConfig) {
        this.resultList = resultList;
        if (settingsWindowModel == null) {
            this.enabledFlag = true;
        } else {
            this.enabledFlag = settingsWindowModel.isCallbackHookEnabledFlag();
        }
        if (settingsWindowModel == null || settingsWindowModel.getCallbackHookUrl() == null) {
            this.callbackHookUrl = serverConfig.buildUrl(Constants.DEFAULT_CALLBACK_HOOK_URI);
        } else {
            this.callbackHookUrl = settingsWindowModel.getCallbackHookUrl();
        }
        if (this.enabledFlag) {
            restTemplate = new RestTemplate();
        }
    }

    @Override
    public void run() {
        if (!this.enabledFlag) {
            log.info("==== CallbackHookTask ==== 已禁用回调钩子URL，跳过！");
            return;
        }
        if (CollectionUtils.isEmpty(resultList)) {
            log.info("==== CallbackHookTask ==== 没有需要发送的结果数据，跳过！");
            return;
        }
        CallbackHookVO vo = CallbackHookVO.of(resultList);
        String jsonData = JacksonUtils.toJSONString(vo);
        log.info("==== CallbackHookTask ==== 回调钩子URL：{} \n准备发送的json数据: {}", callbackHookUrl, jsonData);
        RetryHelper.execute(context -> callback(jsonData));
    }



    public boolean callback(String jsonData) {
        try {
            RequestEntity<String> requestEntity = RequestEntity.method(HttpMethod.POST, new URI(callbackHookUrl))
                    .contentType(MediaType.APPLICATION_JSON).body(jsonData);
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("==== callback ==== 回调钩子URL成功！对方返回结果：{}", responseEntity.getBody());
                return true;
            } else {
                log.error("==== callback ==== 回调钩子URL失败！对方返回结果：{}", responseEntity.getBody());
                throw new RuntimeException(responseEntity.getBody());
            }
        } catch (URISyntaxException e) {
            log.error("==== callback ==== 回调钩子URL失败！", e);
            throw new RuntimeException(e);
        }
    }
}
