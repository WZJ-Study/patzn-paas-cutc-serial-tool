package com.patzn.paas.cutc.ui.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import org.springframework.stereotype.Component;

@Component
public class SettingsWindowModel {


    private final ObjectProperty<Image> closeWindowButtonImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> applySettingButtonImage = new SimpleObjectProperty<>();

    private final IntegerProperty intervalSeconds = new SimpleIntegerProperty();

    private final StringProperty callbackHookUrl = new SimpleStringProperty();
    private final BooleanProperty callbackHookEnabledFlag = new SimpleBooleanProperty(true);

    private final StringProperty portName = new SimpleStringProperty();
    private final IntegerProperty baudRate = new SimpleIntegerProperty();
    private final IntegerProperty slaveId = new SimpleIntegerProperty();

    private final BooleanProperty listeningAddressEnabledFlag = new SimpleBooleanProperty(true);
    private final IntegerProperty listeningAddress = new SimpleIntegerProperty();
    private final StringProperty functionType = new SimpleStringProperty();
    private final IntegerProperty dataType = new SimpleIntegerProperty();

    private final BooleanProperty listeningAddressEnabledFlag2 = new SimpleBooleanProperty(false);
    private final IntegerProperty listeningAddress2 = new SimpleIntegerProperty();
    private final StringProperty functionType2 = new SimpleStringProperty();
    private final IntegerProperty dataType2 = new SimpleIntegerProperty();

    private final BooleanProperty listeningAddressEnabledFlag3 = new SimpleBooleanProperty(false);
    private final IntegerProperty listeningAddress3 = new SimpleIntegerProperty();
    private final StringProperty functionType3 = new SimpleStringProperty();
    private final IntegerProperty dataType3 = new SimpleIntegerProperty();

    private final BooleanProperty listeningAddressEnabledFlag4 = new SimpleBooleanProperty(false);
    private final IntegerProperty listeningAddress4 = new SimpleIntegerProperty();
    private final StringProperty functionType4 = new SimpleStringProperty();
    private final IntegerProperty dataType4 = new SimpleIntegerProperty();

    public Image getCloseWindowButtonImage() {
        return closeWindowButtonImage.get();
    }

    public ObjectProperty<Image> closeWindowButtonImageProperty() {
        return closeWindowButtonImage;
    }

    public void setCloseWindowButtonImage(Image closeWindowButtonImage) {
        this.closeWindowButtonImage.set(closeWindowButtonImage);
    }

    public Image getApplySettingButtonImage() {
        return applySettingButtonImage.get();
    }

    public ObjectProperty<Image> applySettingButtonImageProperty() {
        return applySettingButtonImage;
    }

    public void setApplySettingButtonImage(Image applySettingButtonImage) {
        this.applySettingButtonImage.set(applySettingButtonImage);
    }

    public int getIntervalSeconds() {
        return intervalSeconds.get();
    }

    public IntegerProperty intervalSecondsProperty() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(int intervalSeconds) {
        this.intervalSeconds.set(intervalSeconds);
    }


    public String getCallbackHookUrl() {
        return callbackHookUrl.get();
    }

    public StringProperty callbackHookUrlProperty() {
        return callbackHookUrl;
    }

    public void setCallbackHookUrl(String callbackHookUrl) {
        this.callbackHookUrl.set(callbackHookUrl);
    }

    public boolean isCallbackHookEnabledFlag() {
        return callbackHookEnabledFlag.get();
    }

    public BooleanProperty callbackHookEnabledFlagProperty() {
        return callbackHookEnabledFlag;
    }

    public void setCallbackHookEnabledFlag(boolean callbackHookEnabledFlag) {
        this.callbackHookEnabledFlag.set(callbackHookEnabledFlag);
    }

    public String getPortName() {
        return portName.get();
    }

    public StringProperty portNameProperty() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName.set(portName);
    }

    public int getBaudRate() {
        return baudRate.get();
    }

    public IntegerProperty baudRateProperty() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate.set(baudRate);
    }


    public int getSlaveId() {
        return slaveId.get();
    }

    public IntegerProperty slaveIdProperty() {
        return slaveId;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId.set(slaveId);
    }


    public boolean isListeningAddressEnabledFlag() {
        return listeningAddressEnabledFlag.get();
    }

    public BooleanProperty listeningAddressEnabledFlagProperty() {
        return listeningAddressEnabledFlag;
    }

    public void setListeningAddressEnabledFlag(boolean listeningAddressEnabledFlag) {
        this.listeningAddressEnabledFlag.set(listeningAddressEnabledFlag);
    }

    public int getListeningAddress() {
        return listeningAddress.get();
    }

    public IntegerProperty listeningAddressProperty() {
        return listeningAddress;
    }

    public void setListeningAddress(int listeningAddress) {
        this.listeningAddress.set(listeningAddress);
    }

    public String getFunctionType() {
        return functionType.get();
    }

    public StringProperty functionTypeProperty() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType.set(functionType);
    }

    public int getDataType() {
        return dataType.get();
    }

    public IntegerProperty dataTypeProperty() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType.set(dataType);
    }

    public boolean isListeningAddressEnabledFlag2() {
        return listeningAddressEnabledFlag2.get();
    }

    public BooleanProperty listeningAddressEnabledFlag2Property() {
        return listeningAddressEnabledFlag2;
    }

    public void setListeningAddressEnabledFlag2(boolean listeningAddressEnabledFlag2) {
        this.listeningAddressEnabledFlag2.set(listeningAddressEnabledFlag2);
    }

    public int getListeningAddress2() {
        return listeningAddress2.get();
    }

    public IntegerProperty listeningAddress2Property() {
        return listeningAddress2;
    }

    public void setListeningAddress2(int listeningAddress2) {
        this.listeningAddress2.set(listeningAddress2);
    }

    public String getFunctionType2() {
        return functionType2.get();
    }

    public StringProperty functionType2Property() {
        return functionType2;
    }

    public void setFunctionType2(String functionType2) {
        this.functionType2.set(functionType2);
    }

    public int getDataType2() {
        return dataType2.get();
    }

    public IntegerProperty dataType2Property() {
        return dataType2;
    }

    public void setDataType2(int dataType2) {
        this.dataType2.set(dataType2);
    }

    public boolean isListeningAddressEnabledFlag3() {
        return listeningAddressEnabledFlag3.get();
    }

    public BooleanProperty listeningAddressEnabledFlag3Property() {
        return listeningAddressEnabledFlag3;
    }

    public void setListeningAddressEnabledFlag3(boolean listeningAddressEnabledFlag3) {
        this.listeningAddressEnabledFlag3.set(listeningAddressEnabledFlag3);
    }

    public int getListeningAddress3() {
        return listeningAddress3.get();
    }

    public IntegerProperty listeningAddress3Property() {
        return listeningAddress3;
    }

    public void setListeningAddress3(int listeningAddress3) {
        this.listeningAddress3.set(listeningAddress3);
    }

    public String getFunctionType3() {
        return functionType3.get();
    }

    public StringProperty functionType3Property() {
        return functionType3;
    }

    public void setFunctionType3(String functionType3) {
        this.functionType3.set(functionType3);
    }

    public int getDataType3() {
        return dataType3.get();
    }

    public IntegerProperty dataType3Property() {
        return dataType3;
    }

    public void setDataType3(int dataType3) {
        this.dataType3.set(dataType3);
    }

    public boolean isListeningAddressEnabledFlag4() {
        return listeningAddressEnabledFlag4.get();
    }

    public BooleanProperty listeningAddressEnabledFlag4Property() {
        return listeningAddressEnabledFlag4;
    }

    public void setListeningAddressEnabledFlag4(boolean listeningAddressEnabledFlag4) {
        this.listeningAddressEnabledFlag4.set(listeningAddressEnabledFlag4);
    }

    public int getListeningAddress4() {
        return listeningAddress4.get();
    }

    public IntegerProperty listeningAddress4Property() {
        return listeningAddress4;
    }

    public void setListeningAddress4(int listeningAddress4) {
        this.listeningAddress4.set(listeningAddress4);
    }

    public String getFunctionType4() {
        return functionType4.get();
    }

    public StringProperty functionType4Property() {
        return functionType4;
    }

    public void setFunctionType4(String functionType4) {
        this.functionType4.set(functionType4);
    }

    public int getDataType4() {
        return dataType4.get();
    }

    public IntegerProperty dataType4Property() {
        return dataType4;
    }

    public void setDataType4(int dataType4) {
        this.dataType4.set(dataType4);
    }
}
