package com.patzn.paas.cutc.modbus.wrapper;


import com.patzn.paas.cutc.modbus.io.SerialInputStream;
import com.patzn.paas.cutc.modbus.io.SerialOutputStream;
import com.serotonin.modbus4j.serial.SerialPortWrapper;
import jssc.SerialPort;
import jssc.SerialPortException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class SerialPortWrapperImpl implements SerialPortWrapper {
    private final SerialPort port;
    @Getter
    private final String commPortId;
    private final int baudRate;
    private final int dataBits;
    private final int stopBits;
    private final int parity;
    @Getter
    private final int flowControlIn;
    @Getter
    private final int flowControlOut;

    public SerialPortWrapperImpl(String commPortId, int baudRate, int dataBits, int stopBits, int parity, int flowControlIn, int flowControlOut) {
        // 串口号（Windows下为COMx，Linux为/dev/ttyUSB0）
        this.commPortId = commPortId;
        // 波特率
        this.baudRate = baudRate;
        // 数据位
        this.dataBits = dataBits;
        // 停止位（1或2）
        this.stopBits = stopBits;
        // 校验位：0-无，1-奇校验，2-偶校验
        this.parity = parity;
        // 流量控制
        this.flowControlIn = flowControlIn;
        this.flowControlOut = flowControlOut;

        port = new SerialPort(commPortId);
    }

    @Override
    public void close() throws SerialPortException {
        port.closePort();
        log.debug("Serial port {} closed", port.getPortName());
    }

    @Override
    public void open() throws SerialPortException {
        port.openPort();
        port.setParams(this.getBaudRate(), this.getDataBits(), this.getStopBits(), this.getParity());
        port.setFlowControlMode(this.getFlowControlIn() | this.getFlowControlOut());
        log.debug("Serial port {} opened", port.getPortName());
    }

    @Override
    public InputStream getInputStream() {
        return new SerialInputStream(port);
    }

    @Override
    public OutputStream getOutputStream() {
        return new SerialOutputStream(port);
    }


    @Override
    public int getBaudRate() {
        return baudRate;
    }

    @Override
    public int getDataBits() {
        return dataBits;
    }

    @Override
    public int getStopBits() {
        return stopBits;
    }

    @Override
    public int getParity() {
        return parity;
    }
}
