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

    public ModbusConnection(String portName, int baudRate) {
        this.portName = portName;
        this.baudRate = baudRate;
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
            master.setRetries(1);
            // 初始化主站，只有init后才可以读写
            master.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        if (master != null) {
            master.destroy();
        }
    }

    public void tryDisconnect() {
        try {
            disconnect();
        } catch (Exception e) {
            log.error("==== tryDisconnect ==== 断开连接失败！", e);
        }
    }

    public boolean isInitialized() {
        if (master != null) {
            return master.isInitialized();
        }
        return false;
    }

    public void reset(String portName, int baudRate) {
        if (Objects.equals(this.portName, portName) && this.baudRate == baudRate) {
            // 不需要重置
            return;
        }

        // 已连接的，先断开连接
        if (isInitialized()) {
            disconnect();
        }

        // 修改配置
        this.portName = portName;
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


}
