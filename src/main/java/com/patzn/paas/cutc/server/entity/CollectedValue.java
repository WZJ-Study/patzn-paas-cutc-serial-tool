package com.patzn.paas.cutc.server.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CollectedValue {

    /**
     * 上位机IP地址
     */
    private String ipAddr;

    /**
     * 上位机主机名称
     */
    private String hostName;

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
        return String.format("【%s】%s", collectTime, actualValue);
    }

}
