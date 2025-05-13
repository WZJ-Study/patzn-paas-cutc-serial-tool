package com.patzn.paas.cutc.ui.view;


import com.patzn.paas.cutc.constants.Constants;
import com.patzn.paas.cutc.fxml.FxmlViews;
import com.patzn.paas.cutc.fxml.loader.SpringFxmlLoader;
import com.patzn.paas.cutc.ui.model.*;
import com.patzn.paas.cutc.ui.utils.*;
import com.patzn.paas.cutc.utils.spring.SpringHelper;
import com.patzn.paas.cutc.ui.helper.StageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Component
public class MainWindowView implements Initializable {



    @Resource
    private StageManager stageManager;

    @Resource
    private SpringFxmlLoader springFxmlLoader;




    @Resource
    private MainWindowModel mainWindowModel;


    @FXML
    private ImageView mainWindowLogoImage;


    @FXML
    private ImageView closeWindowButtonImage;
    @FXML
    private ImageView maximizeWindowButtonImage;
    @FXML
    private ImageView minimizeWindowButtonImage;
    @FXML
    private ImageView openSettingsWindowButtonImage;
    @FXML
    private ImageView reloadMainWindowButtonImage;

    @FXML
    private ImageView toggleCollectStatusMenuButtonImage;
    @FXML
    private Label collectCountDownText;


    private double offsetX;
    private double offsetY;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 绑定FXML组件与model属性 - 标题栏logo
        mainWindowLogoImage.imageProperty().bindBidirectional(mainWindowModel.mainWindowLogoImageProperty());

        // 绑定FXML组件与model属性 - 标题栏右侧窗口按钮
        closeWindowButtonImage.imageProperty().bindBidirectional(mainWindowModel.closeWindowButtonImageProperty());
        maximizeWindowButtonImage.imageProperty().bindBidirectional(mainWindowModel.maximizeWindowButtonImageProperty());
        minimizeWindowButtonImage.imageProperty().bindBidirectional(mainWindowModel.minimizeWindowButtonImageProperty());
        openSettingsWindowButtonImage.imageProperty().bindBidirectional(mainWindowModel.openSettingsWindowButtonImageProperty());
        reloadMainWindowButtonImage.imageProperty().bindBidirectional(mainWindowModel.reloadMainWindowButtonImageProperty());

        // 绑定FXML组件与model属性 - 菜单栏按钮
        toggleCollectStatusMenuButtonImage.imageProperty().bindBidirectional(mainWindowModel.toggleCollectStatusMenuButtonImageProperty());
        collectCountDownText.textProperty().bindBidirectional(mainWindowModel.collectCountDownTextProperty());


        // 处理model属性 - 标题栏logo
        mainWindowModel.setMainWindowLogoImage(ImageLoader.load(Constants.LOGO_IMAGE_PATH));

        // 处理model属性 - 标题栏右侧窗口按钮
        mainWindowModel.setCloseWindowButtonImage(ImageLoader.load(Constants.CLOSE_IMAGE_PATH));
        mainWindowModel.setMaximizeWindowButtonImage(ImageLoader.load(Constants.MAXIMIZE_IMAGE_PATH));
        mainWindowModel.setMinimizeWindowButtonImage(ImageLoader.load(Constants.MINIMIZE_IMAGE_PATH));
        mainWindowModel.setOpenSettingsWindowButtonImage(ImageLoader.load(Constants.SETTINGS_IMAGE_PATH));
        mainWindowModel.setReloadMainWindowButtonImage(ImageLoader.load(Constants.RELOAD_IMAGE_PATH));

        // 处理model属性 - 菜单栏按钮
        mainWindowModel.setToggleCollectStatusMenuButtonImage(ImageLoader.load(Constants.RUN_BLUE_IMAGE_PATH));
        mainWindowModel.setCollectCountDownText("已停止");
        mainWindowModel.setCollectRunningFlag(false);
    }




    @FXML
    protected void onMainWindowMousePressed(MouseEvent event) {
        this.offsetX = event.getSceneX();
        this.offsetY = event.getSceneY();
    }

    @FXML
    protected void onMainWindowMouseDragged(MouseEvent event) {
        Stage stage = stageManager.getMainWindowStage();
        if (null != stage) {
            Platform.runLater(() -> {
                stage.setX(event.getScreenX() - offsetX);
                stage.setY(event.getScreenY() - offsetY);
            });
        }
    }

    @FXML
    protected void onCloseWindowButtonClick() {
        log.info("==== onCloseWindowButtonClick ==== 点击【关闭窗口】按钮，退出！");
        SpringHelper.close();
        Platform.exit();
    }

    @FXML
    protected void onMaximizeWindowButtonClick() {
        log.info("==== onMaximizeWindowButtonClick ==== 点击【最大化窗口/恢复窗口大小】按钮！");
        Stage stage = stageManager.getMainWindowStage();
        if (null != stage) {
            if (WindowSizeHolder.isMaximized()) {
                // 关闭全屏
                log.info("==== onMaximizeWindowButtonClick ==== 恢复窗口大小！");
                Platform.runLater(() -> {
                    WindowSizeHolder.restore(stage);
                });
                mainWindowModel.setMaximizeWindowButtonImage(ImageLoader.load(Constants.MAXIMIZE_IMAGE_PATH));
            } else {
                // 全屏
                log.info("==== onMaximizeWindowButtonClick ==== 最大化窗口！");
                WindowSizeHolder.backup(stage);
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                log.info("==== WindowSizeHolder ==== 最大化的窗口位置大小：visualBounds={}", visualBounds);
                Platform.runLater(() -> {
                    stage.setX(visualBounds.getMinX());
                    stage.setY(visualBounds.getMinY());
                    stage.setWidth(visualBounds.getWidth());
                    stage.setHeight(visualBounds.getHeight());
                });
                mainWindowModel.setMaximizeWindowButtonImage(ImageLoader.load(Constants.RESIZE_IMAGE_PATH));
            }
        }
    }

    @FXML
    protected void onMinimizeWindowButtonClick() {
        log.info("==== onMinimizeWindowButtonClick ==== 点击【最小化窗口】按钮！");
        Stage stage = stageManager.getMainWindowStage();
        if (null != stage && !stage.isIconified()) {
            Platform.runLater(() -> stage.setIconified(true));
        }
    }

    @FXML
    protected void onOpenSettingsWindowButtonClick() {
        log.info("==== onOpenSettingsWindowButtonClick ==== 点击【打开[设置]窗口】按钮！");
        Stage settingsWindowStage = stageManager.getSettingsWindowStage();
        if (null == settingsWindowStage) {
            // 首次打开设置窗口，初始化
            settingsWindowStage = new Stage();
            springFxmlLoader.loadTo(FxmlViews.SETTINGS_WINDOW, settingsWindowStage);
            stageManager.setSettingsWindowStage(settingsWindowStage);
        } else {
            // 打开设置窗口
            Platform.runLater(settingsWindowStage::show);
        }
    }

    @FXML
    protected void onReloadButtonClick() {
        log.info("==== onReloadButtonClick ==== 点击【重新加载】按钮！");
        if (mainWindowModel.isCollectRunningFlag()) {
            log.error("采集正在运行中，不可重新加载！");
            return;
        }
        // todo

    }






    @FXML
    protected void onToggleCollectStatusMenuButtonClick() {
        if (mainWindowModel.isCollectRunningFlag()) {
            // 采集中，结束采集
            log.info("==== onToggleCollectStatusMenuButtonClick ==== 点击【结束采集】按钮！");

            // todo

            // 按钮变为【开始采集】按钮
            mainWindowModel.setToggleCollectStatusMenuButtonImage(ImageLoader.load(Constants.RUN_BLUE_IMAGE_PATH));
            mainWindowModel.setCollectRunningFlag(false);
        } else {
            // 开始采集
            log.info("==== onToggleCollectStatusMenuButtonClick ==== 点击【开始采集】按钮！");

            // todo

            // 按钮变为【结束采集】按钮
            mainWindowModel.setToggleCollectStatusMenuButtonImage(ImageLoader.load(Constants.STOP_RED_IMAGE_PATH));
            mainWindowModel.setCollectRunningFlag(true);
        }
    }



}
