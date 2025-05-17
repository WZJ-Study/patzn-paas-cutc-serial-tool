package com.patzn.paas.cutc.ui.model;

import com.patzn.paas.cutc.constants.Constants;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MainWindowModel {

    private final ObjectProperty<Image> mainWindowLogoImage = new SimpleObjectProperty<>();
    private final SimpleStringProperty mainWindowTitleText = new SimpleStringProperty();

    private final ObjectProperty<Image> closeWindowButtonImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> maximizeWindowButtonImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> minimizeWindowButtonImage = new SimpleObjectProperty<>();

    private final ObjectProperty<Image> toggleCollectStatusMenuButtonImage = new SimpleObjectProperty<>();
    private final SimpleStringProperty collectCountDownText = new SimpleStringProperty();
    private final BooleanProperty collectRunningFlag = new SimpleBooleanProperty(false);


    private final ObjectProperty<Image> refreshSerialPortButtonImage = new SimpleObjectProperty<>();
    private final SimpleStringProperty actualValueText = new SimpleStringProperty();

    private final ObservableList<String> serialPortNameList = FXCollections.observableArrayList(Constants.PORT_NAME_LIST);

    public void displayBottomText(String text) {
        if (StringUtils.isBlank(text)) {
            return;
        }
        Platform.runLater(() -> {
            this.setActualValueText(text);
        });
    }


    public Image getMainWindowLogoImage() {
        return mainWindowLogoImage.get();
    }

    public ObjectProperty<Image> mainWindowLogoImageProperty() {
        return mainWindowLogoImage;
    }

    public void setMainWindowLogoImage(Image mainWindowLogoImage) {
        this.mainWindowLogoImage.set(mainWindowLogoImage);
    }



    public String getMainWindowTitleText() {
        return mainWindowTitleText.get();
    }

    public SimpleStringProperty mainWindowTitleTextProperty() {
        return mainWindowTitleText;
    }

    public void setMainWindowTitleText(String mainWindowTitleText) {
        this.mainWindowTitleText.set(mainWindowTitleText);
    }

    public Image getCloseWindowButtonImage() {
        return closeWindowButtonImage.get();
    }

    public ObjectProperty<Image> closeWindowButtonImageProperty() {
        return closeWindowButtonImage;
    }

    public void setCloseWindowButtonImage(Image closeWindowButtonImage) {
        this.closeWindowButtonImage.set(closeWindowButtonImage);
    }

    public Image getMaximizeWindowButtonImage() {
        return maximizeWindowButtonImage.get();
    }

    public ObjectProperty<Image> maximizeWindowButtonImageProperty() {
        return maximizeWindowButtonImage;
    }

    public void setMaximizeWindowButtonImage(Image maximizeWindowButtonImage) {
        this.maximizeWindowButtonImage.set(maximizeWindowButtonImage);
    }

    public Image getMinimizeWindowButtonImage() {
        return minimizeWindowButtonImage.get();
    }

    public ObjectProperty<Image> minimizeWindowButtonImageProperty() {
        return minimizeWindowButtonImage;
    }

    public void setMinimizeWindowButtonImage(Image minimizeWindowButtonImage) {
        this.minimizeWindowButtonImage.set(minimizeWindowButtonImage);
    }


    public Image getToggleCollectStatusMenuButtonImage() {
        return toggleCollectStatusMenuButtonImage.get();
    }

    public ObjectProperty<Image> toggleCollectStatusMenuButtonImageProperty() {
        return toggleCollectStatusMenuButtonImage;
    }

    public void setToggleCollectStatusMenuButtonImage(Image toggleCollectStatusMenuButtonImage) {
        this.toggleCollectStatusMenuButtonImage.set(toggleCollectStatusMenuButtonImage);
    }

    public String getCollectCountDownText() {
        return collectCountDownText.get();
    }

    public SimpleStringProperty collectCountDownTextProperty() {
        return collectCountDownText;
    }

    public void setCollectCountDownText(String collectCountDownText) {
        this.collectCountDownText.set(collectCountDownText);
    }

    public boolean isCollectRunningFlag() {
        return collectRunningFlag.get();
    }

    public BooleanProperty collectRunningFlagProperty() {
        return collectRunningFlag;
    }

    public void setCollectRunningFlag(boolean collectRunningFlag) {
        this.collectRunningFlag.set(collectRunningFlag);
    }

    public String getActualValueText() {
        return actualValueText.get();
    }

    public SimpleStringProperty actualValueTextProperty() {
        return actualValueText;
    }

    public void setActualValueText(String actualValueText) {
        this.actualValueText.set(actualValueText);
    }

    public Image getRefreshSerialPortButtonImage() {
        return refreshSerialPortButtonImage.get();
    }

    public ObjectProperty<Image> refreshSerialPortButtonImageProperty() {
        return refreshSerialPortButtonImage;
    }

    public void setRefreshSerialPortButtonImage(Image refreshSerialPortButtonImage) {
        this.refreshSerialPortButtonImage.set(refreshSerialPortButtonImage);
    }

    public ObservableList<String> getSerialPortNameList() {
        return serialPortNameList;
    }

}
