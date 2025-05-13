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
    private final IntegerProperty listeningAddress = new SimpleIntegerProperty();
    private final StringProperty functionType = new SimpleStringProperty();


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

}
