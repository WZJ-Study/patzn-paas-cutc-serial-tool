package com.patzn.paas.cutc.modbus.task;

import com.patzn.paas.cutc.component.TaskExecutor;
import com.patzn.paas.cutc.constants.Constants;
import com.patzn.paas.cutc.modbus.ModbusManager;
import com.patzn.paas.cutc.modbus.connection.ModbusConnection;
import com.patzn.paas.cutc.modbus.enums.DataTypeEnum;
import com.patzn.paas.cutc.server.entity.CollectedValue;
import com.patzn.paas.cutc.ui.model.SettingsWindowModel;
import com.patzn.paas.cutc.utils.date.DateUtils;
import com.patzn.paas.cutc.utils.network.IpHelper;
import com.serotonin.modbus4j.code.DataType;
import lombok.extern.slf4j.Slf4j;

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
        String actualValue = this.collectActualValue();
        if (null == actualValue) {
            return;
        }

        // 生成采集结果对象
        CollectedValue result = new CollectedValue();
        result.setIpAddr(IpHelper.LOCAL_IP);
        result.setHostName(IpHelper.HOST_NAME);
        result.setPortName(settingsWindowModel.getPortName());
        result.setBaudRate(String.valueOf(settingsWindowModel.getBaudRate()));
        result.setSlaveId(String.valueOf(settingsWindowModel.getSlaveId()));
        result.setListeningAddress(String.valueOf(settingsWindowModel.getListeningAddress()));
        result.setFunctionType(settingsWindowModel.getFunctionType());
        result.setActualValue(actualValue);
        result.setCollectTime(DateUtils.nowStr());

        // 回显采集结果
        this.modbusManager.displayBottomText(result.displayValue());

        // 采集结果触发回调钩子
        TaskExecutor.execute(this.modbusManager.createCallbackHookTask(result));
    }

    private String collectActualValue() {
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
            int slaveId = settingsWindowModel.getSlaveId();
            int address = settingsWindowModel.getListeningAddress();
            String functionType = settingsWindowModel.getFunctionType();
            DataTypeEnum dataTypeEnum = DataTypeEnum.ofValue(settingsWindowModel.getDataType());
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
            return actualValue;
        } catch (Exception e) {
            log.error("==== collectActualValue ==== 数据采集失败！", e);
            this.modbusManager.displayBottomText("数据采集失败！" + e.getMessage());
        }
        return null;
    }

}
