package com.patzn.paas.cutc.server.vo;

import com.patzn.paas.cutc.server.entity.ModbusRegisterValue;
import com.patzn.paas.cutc.utils.date.DateUtils;
import com.patzn.paas.cutc.utils.network.IpHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CallbackHookVO {

    public static CallbackHookVO of(List<ModbusRegisterValue> resultList) {
        CallbackHookVO vo = new CallbackHookVO();
        vo.setIpAddr(IpHelper.LOCAL_IP);
        vo.setHostName(IpHelper.HOST_NAME);
        vo.setData(resultList);
        vo.setSendTime(DateUtils.nowStr());
        return vo;
    }

    private String ipAddr;
    private String hostName;
    private List<ModbusRegisterValue> data;
    private String sendTime;


}
