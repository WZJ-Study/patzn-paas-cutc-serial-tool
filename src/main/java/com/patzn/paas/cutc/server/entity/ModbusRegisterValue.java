package com.patzn.paas.cutc.server.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModbusRegisterValue {

    private String position;

    // ================================[ 串口设置 ]================================

    /**
     * 串口-端口名称
     */
    private String portName;

    /**
     * 串口-波特率
     */
    private String baudRate;

    // ================================[ Modbus设置 ]================================

    /**
     * Modbus-下位机ID
     */
    private String slaveId;

    /**
     * Modbus-寄存器Address
     */
    private String listeningAddress;

    /**
     * Modbus-功能类型Function
     */
    private String functionType;

    /**
     * Modbus-数据类型DataType
     */
    private String dataType;

    /**
     * Modbus-数据类型DataType
     */
    private String dataTypeName;

    // ================================[ 采集数据 ]================================

    /**
     * 采集到的实际读数
     */
    private String actualValue;

    /**
     * 采集时间
     */
    private String collectTime;

    public String displayValue() {
        return String.format("%s=%s", listeningAddress, actualValue);
    }

}
