package com.patzn.paas.cutc.modbus.task;

import com.patzn.paas.cutc.component.TaskExecutor;
import com.patzn.paas.cutc.constants.Constants;
import com.patzn.paas.cutc.modbus.ModbusManager;
import com.patzn.paas.cutc.modbus.connection.ModbusConnection;
import com.patzn.paas.cutc.modbus.enums.DataTypeEnum;
import com.patzn.paas.cutc.server.entity.ModbusRegisterValue;
import com.patzn.paas.cutc.ui.model.SettingsWindowModel;
import com.patzn.paas.cutc.utils.date.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CollectTask implements Runnable {

    /**
     * Modbus管理器
     */
    private final ModbusManager modbusManager;

    /**
     * 设置弹窗区域模型
     */
    private final SettingsWindowModel settingsWindowModel;

    public CollectTask(ModbusManager modbusManager,
                       SettingsWindowModel settingsWindowModel) {
        this.modbusManager = modbusManager;
        this.settingsWindowModel = settingsWindowModel;
    }

    @Override
    public void run() {
        List<ModbusRegisterValue> resultList = this.collectActualValue();
        if (CollectionUtils.isEmpty(resultList)) {
            return;
        }

        String displayValue = resultList.stream()
                .map(ModbusRegisterValue::displayValue)
                .collect(Collectors.joining("，"));

        // 回显采集结果
        this.modbusManager.displayBottomText(String.format("【%s】%s", DateUtils.nowStr(), displayValue));

        // 采集结果触发回调钩子
        TaskExecutor.execute(this.modbusManager.createCallbackHookTask(resultList));
    }

    private List<ModbusRegisterValue> collectActualValue() {
        try {
            ModbusConnection connection = this.modbusManager.getConnection();
            if (null == connection) {
                log.info("==== collectActualValue ==== 串口连接为空，跳过！");
                this.modbusManager.displayBottomText("空连接！");
                return null;
            }
            if (!connection.isInitialized()) {
                log.info("==== collectActualValue ==== 串口连接未建立，跳过！");
                this.modbusManager.displayBottomText("连接未建立！");
                return null;
            }
            List<ModbusRegisterValue> resultList = new LinkedList<>();
            if (settingsWindowModel.isListeningAddressEnabledFlag()) {
                ModbusRegisterValue result = this.collectActualValues(connection,
                        settingsWindowModel.getSlaveId(),
                        settingsWindowModel.getListeningAddress(),
                        settingsWindowModel.getFunctionType(),
                        settingsWindowModel.getDataType());
                if (result != null) {
                    result.setPosition("1#");
                    resultList.add(result);
                }
            }
            if (settingsWindowModel.isListeningAddressEnabledFlag2()) {
                ModbusRegisterValue result = this.collectActualValues(connection,
                        settingsWindowModel.getSlaveId(),
                        settingsWindowModel.getListeningAddress2(),
                        settingsWindowModel.getFunctionType2(),
                        settingsWindowModel.getDataType2());
                if (result != null) {
                    result.setPosition("2#");
                    resultList.add(result);
                }
            }
            if (settingsWindowModel.isListeningAddressEnabledFlag3()) {
                ModbusRegisterValue result = this.collectActualValues(connection,
                        settingsWindowModel.getSlaveId(),
                        settingsWindowModel.getListeningAddress3(),
                        settingsWindowModel.getFunctionType3(),
                        settingsWindowModel.getDataType3());
                if (result != null) {
                    result.setPosition("3#");
                    resultList.add(result);
                }
            }
            if (settingsWindowModel.isListeningAddressEnabledFlag4()) {
                ModbusRegisterValue result = this.collectActualValues(connection,
                        settingsWindowModel.getSlaveId(),
                        settingsWindowModel.getListeningAddress4(),
                        settingsWindowModel.getFunctionType4(),
                        settingsWindowModel.getDataType4());
                if (result != null) {
                    result.setPosition("4#");
                    resultList.add(result);
                }
            }
            return resultList;
        } catch (Exception e) {
            log.error("==== collectActualValue ==== 数据采集失败！", e);
            this.modbusManager.displayBottomText("数据采集失败！" + e.getMessage());
        }
        return null;
    }


    private ModbusRegisterValue collectActualValues(ModbusConnection connection, Integer slaveId, Integer address, String functionType, Integer dataType) throws Exception {
        if (connection == null || slaveId == null || address == null || functionType == null || dataType == null) {
            return null;
        }

        DataTypeEnum dataTypeEnum = DataTypeEnum.ofValue(dataType);
        if (dataTypeEnum == null) {
            dataTypeEnum = DataTypeEnum.TWO_BYTE_INT_SIGNED;
        }
        String actualValue = null;
        switch (functionType) {
            case Constants.MODBUS_FUNCTION_01:
                Boolean coilStatus = connection.readCoilStatus(slaveId, address);
                actualValue = coilStatus == null ? Constants.EMPTY_STR : coilStatus.toString();
                break;
            case Constants.MODBUS_FUNCTION_02:
                Boolean inputStatus = connection.readInputStatus(slaveId, address);
                actualValue = inputStatus == null ? Constants.EMPTY_STR : inputStatus.toString();
                break;
            case Constants.MODBUS_FUNCTION_03:
                Number holdingRegister = connection.readHoldingRegister(slaveId, address, dataTypeEnum.getValue());
                actualValue = holdingRegister == null ? Constants.EMPTY_STR : holdingRegister.toString();
                break;
            case Constants.MODBUS_FUNCTION_04:
                Number inputRegisters = connection.readInputRegisters(slaveId, address, dataTypeEnum.getValue());
                actualValue = inputRegisters == null ? Constants.EMPTY_STR : inputRegisters.toString();
                break;
        }
        log.info("==== collectActualValue ==== 数据采集结果：{}", actualValue);

        // 生成采集结果对象
        ModbusRegisterValue result = new ModbusRegisterValue();
        result.setPortName(settingsWindowModel.getPortName());
        result.setBaudRate(String.valueOf(settingsWindowModel.getBaudRate()));
        result.setSlaveId(String.valueOf(slaveId));
        result.setListeningAddress(String.valueOf(address));
        result.setFunctionType(functionType);
        result.setDataType(dataTypeEnum.getName());
        result.setDataTypeName(dataTypeEnum.getDisplayName());
        result.setActualValue(actualValue);
        result.setCollectTime(DateUtils.nowStr());
        return result;
    }
}
