package com.patzn.paas.cutc.modbus.connection;

import com.patzn.paas.cutc.modbus.wrapper.SerialPortWrapperImpl;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.serial.SerialPortWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ModbusConnection {

    private String portName;
    private int baudRate;
    private ModbusMaster master;
    private final AtomicBoolean connectedFlag;

    public ModbusConnection(String portName, int baudRate) {
        this.portName = portName;
        this.baudRate = baudRate;
        connectedFlag = new AtomicBoolean(false);
    }

    public void connect() {
        try {
            SerialPortWrapper serialPort = new SerialPortWrapperImpl(
                    portName, baudRate, 8, 1, 0, 0, 0
            );
            ModbusFactory factory = new ModbusFactory();
            this.master = factory.createRtuMaster(serialPort);
            // 超时时间（ms）
            master.setTimeout(500);
            // 重试次数
            master.setRetries(3);
            // 初始化主站，只有init后才可以读写
            master.init();
            // 设置连接状态
            connectedFlag.set(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        if (master != null) {
            master.destroy();
        }
        connectedFlag.set(false);
    }

    public boolean isConnected() {
        return connectedFlag.get();
    }

    public void reset(String portName, int baudRate) {
        // 已连接的，先断开连接
        if (isConnected()) {
            disconnect();
        }
        this.portName = portName;
        this.baudRate = baudRate;
    }

    public void changeBaudRate(int baudRate) {
        if (this.baudRate == baudRate) {
            // 无需修改
            return;
        }
        // 已连接的，先断开连接
        if (isConnected()) {
            disconnect();
        }
        // 修改波特率
        this.baudRate = baudRate;
    }

    /**
     * 读取[01 Coil Status 0x]类型 开关数据
     *
     * @param slaveId slaveId
     * @param offset  位置
     * @return 读取值
     */
    public Boolean readCoilStatus(int slaveId, int offset) throws ModbusTransportException, ErrorResponseException {
        BaseLocator<Boolean> locator = BaseLocator.coilStatus(slaveId, offset);
        return master.getValue(locator);
    }


    /**
     * 读取[02 Input Status 1x]类型 开关数据
     *
     * @param slaveId slaveId
     * @param offset  位置
     * @return 读取值
     */
    public Boolean readInputStatus(int slaveId, int offset) throws ModbusTransportException, ErrorResponseException {
        BaseLocator<Boolean> loc = BaseLocator.inputStatus(slaveId, offset);
        return master.getValue(loc);
    }


    /**
     * 读取[03 Holding Register 4x]模拟量数据
     *
     * @param slaveId  slave Id
     * @param offset   位置
     * @param dataType 数据类型,来自com.serotonin.modbus4j.code.DataType
     * @return 读取值
     */
    public Number readHoldingRegister(int slaveId, int offset, int dataType) throws ModbusTransportException, ErrorResponseException {
        BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveId, offset, dataType);
        return master.getValue(loc);
    }


    /**
     * 读取[04 Input Registers 3x]类型 模拟量数据
     *
     * @param slaveId  slaveId
     * @param offset   位置
     * @param dataType 数据类型,来自com.serotonin.modbus4j.code.DataType
     * @return 读取值
     */
    public Number readInputRegisters(int slaveId, int offset, int dataType) throws ModbusTransportException, ErrorResponseException {
        BaseLocator<Number> loc = BaseLocator.inputRegister(slaveId, offset, dataType);
        return master.getValue(loc);
    }


    public static void main(String[] args) {
        ModbusConnection connection = new ModbusConnection("COM2", 9600);
        try {
            connection.connect();
            Number value = connection.readHoldingRegister(1, 1, DataType.TWO_BYTE_INT_SIGNED);
            log.info("读取结果：{}", value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

}
