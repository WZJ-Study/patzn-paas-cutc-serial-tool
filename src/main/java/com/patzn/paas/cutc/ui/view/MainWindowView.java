package com.patzn.paas.cutc.ui.view;


import com.patzn.paas.cutc.config.ConfigManager;
import com.patzn.paas.cutc.config.ServerConfig;
import com.patzn.paas.cutc.constants.ConfigKeys;
import com.patzn.paas.cutc.constants.Constants;
import com.patzn.paas.cutc.modbus.ModbusManager;
import com.patzn.paas.cutc.modbus.enums.DataTypeEnum;
import com.patzn.paas.cutc.ui.model.*;
import com.patzn.paas.cutc.ui.utils.*;
import com.patzn.paas.cutc.utils.spring.SpringHelper;
import com.patzn.paas.cutc.ui.helper.StageManager;
import com.serotonin.modbus4j.code.DataType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jssc.SerialPortList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Component
public class MainWindowView implements Initializable {

    @Resource
    private ServerConfig serverConfig;

    @Resource
    private ConfigManager configManager;

    @Resource
    private StageManager stageManager;

    @Resource
    private ModbusManager modbusManager;

    @PostConstruct
    public void init() {
        this.modbusManager = new ModbusManager(mainWindowModel, settingsWindowModel, serverConfig);
        this.settingsWindowModel.intervalSecondsProperty().addListener((observableValue, oldValue, newValue) -> {
            log.info("==== 修改定时采集间隔 ==== 设置新的采集间隔：{}秒", newValue);
            this.modbusManager.setIntervalSeconds(newValue.intValue());
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // 清理代码，例如关闭数据库连接、停止线程等
            this.modbusManager.stop();
        }));
    }


    @Resource
    private MainWindowModel mainWindowModel;

    @Resource
    private SettingsWindowModel settingsWindowModel;


    @FXML
    private ImageView mainWindowLogoImage;

    @FXML
    public Label mainWindowTitleText;

    @FXML
    private ImageView closeWindowButtonImage;
    @FXML
    private ImageView maximizeWindowButtonImage;
    @FXML
    private ImageView minimizeWindowButtonImage;


    @FXML
    private ImageView toggleCollectStatusMenuButtonImage;
    @FXML
    private Label collectCountDownText;

    @FXML
    private ImageView refreshSerialPortButtonImage;

    @FXML
    private Label actualValueText;

    // ================================[ 设置部分 ]================================

    @FXML
    private ChoiceBox<String> portNameInput;

    @FXML
    private ChoiceBox<Integer> baudRateInput;

    // --------------------------------[ 监听地址设置 ]--------------------------------

    @FXML
    public TextField slaveIdInput;

    @FXML
    private TextField listeningAddressInput;

    @FXML
    public ChoiceBox<String> functionTypeInput;

    @FXML
    public ChoiceBox<String> dataTypeInput;

    // --------------------------------[ 采集间隔（秒） ]--------------------------------

    @FXML
    private TextField intervalSecondsInput;

    // --------------------------------[ 回调URL ]--------------------------------

    @FXML
    private TextField callbackHookUrlInput;
    @FXML
    private CheckBox callbackHookEnabledInput;

    // ================================[ 窗口拖拽坐标 ]================================

    private double offsetX;
    private double offsetY;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 绑定FXML组件与model属性 - 标题栏logo
        mainWindowLogoImage.imageProperty().bindBidirectional(mainWindowModel.mainWindowLogoImageProperty());
        mainWindowTitleText.textProperty().bindBidirectional(mainWindowModel.mainWindowTitleTextProperty());

        // 绑定FXML组件与model属性 - 标题栏右侧窗口按钮
        closeWindowButtonImage.imageProperty().bindBidirectional(mainWindowModel.closeWindowButtonImageProperty());
        maximizeWindowButtonImage.imageProperty().bindBidirectional(mainWindowModel.maximizeWindowButtonImageProperty());
        minimizeWindowButtonImage.imageProperty().bindBidirectional(mainWindowModel.minimizeWindowButtonImageProperty());

        // 绑定FXML组件与model属性 - 菜单栏按钮
        toggleCollectStatusMenuButtonImage.imageProperty().bindBidirectional(mainWindowModel.toggleCollectStatusMenuButtonImageProperty());
        collectCountDownText.textProperty().bindBidirectional(mainWindowModel.collectCountDownTextProperty());

        // 绑定FXML组件与model属性 - 中间配置部分
        portNameInput.setItems(mainWindowModel.getSerialPortNameList());
        portNameInput.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 选择portName ==== {}", newValue);
            if (StringUtils.isNotBlank(newValue)) {
                configManager.setProperty(ConfigKeys.KEY_PORT_NAME, newValue);
                settingsWindowModel.setPortName(newValue);
            }
        });

        refreshSerialPortButtonImage.imageProperty().bindBidirectional(mainWindowModel.refreshSerialPortButtonImageProperty());

        baudRateInput.setItems(FXCollections.observableList(Constants.BUAD_RATE_LIST));
        baudRateInput.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 选择baudRate ==== {}", newValue);
            if (null != newValue) {
                configManager.setProperty(ConfigKeys.KEY_BUAD_RATE, String.valueOf(newValue));
                settingsWindowModel.setBaudRate(newValue);
            }
        });

        functionTypeInput.setItems(FXCollections.observableList(Constants.MODBUS_FUNCTION_LIST));
        functionTypeInput.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 选择Function ==== {}", newValue);
            if (null != newValue) {
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE, newValue);
                settingsWindowModel.setFunctionType(newValue);
            }
        });

        dataTypeInput.setItems(FXCollections.observableList(DataTypeEnum.getDisplayNameList()));
        dataTypeInput.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 选择DataType ==== {}", newValue);
            if (null != newValue) {
                DataTypeEnum newValueEnum = DataTypeEnum.ofDisplayName(newValue);
                if (null != newValueEnum) {
                    configManager.setProperty(ConfigKeys.KEY_DATA_TYPE, newValueEnum.getName());
                    settingsWindowModel.setDataType(newValueEnum.getValue());
                }
            }
        });

        // 绑定FXML组件与model属性 - 底部显示文本
        actualValueText.textProperty().bindBidirectional(mainWindowModel.actualValueTextProperty());

        // 处理model属性 - 标题栏logo
        mainWindowModel.setMainWindowLogoImage(ImageLoader.load(Constants.LOGO_IMAGE_PATH));
        mainWindowModel.setMainWindowTitleText(Constants.MAIN_WINDOW_TITLE);

        // 处理model属性 - 标题栏右侧窗口按钮
        mainWindowModel.setCloseWindowButtonImage(ImageLoader.load(Constants.CLOSE_IMAGE_PATH));
        mainWindowModel.setMaximizeWindowButtonImage(ImageLoader.load(Constants.MAXIMIZE_IMAGE_PATH));
        mainWindowModel.setMinimizeWindowButtonImage(ImageLoader.load(Constants.MINIMIZE_IMAGE_PATH));

        // 处理model属性 - 菜单栏按钮
        mainWindowModel.setToggleCollectStatusMenuButtonImage(ImageLoader.load(Constants.RUN_BLUE_IMAGE_PATH));
        mainWindowModel.setCollectCountDownText("已停止");
        mainWindowModel.setCollectRunningFlag(false);

        // 处理model属性 - 中间配置部分
        mainWindowModel.getSerialPortNameList().clear();
        mainWindowModel.getSerialPortNameList().addAll(Arrays.asList(SerialPortList.getPortNames()));


        mainWindowModel.setRefreshSerialPortButtonImage(ImageLoader.load(Constants.RELOAD_IMAGE_PATH));

        // 处理model属性 - 底部显示文本
        mainWindowModel.setActualValueText("未连接");

        // ================================[ 设置部分 ]================================

        boolean savePropertiesFlag = false;

        // 设置#1.串口名称
        String portName = configManager.getProperty(ConfigKeys.KEY_PORT_NAME);
        if (StringUtils.isBlank(portName)) {
            configManager.setProperty(ConfigKeys.KEY_PORT_NAME, Constants.DEFAULT_PORT_NAME);
            savePropertiesFlag = true;
        }
        // 设置默认选中
        portNameInput.getSelectionModel().select(mainWindowModel.getSerialPortNameList().indexOf(portName));

        // 设置#2.波特率（bps）
        String buadRateStr = configManager.getProperty(ConfigKeys.KEY_BUAD_RATE);
        if (StringUtils.isBlank(buadRateStr)) {
            buadRateStr = String.valueOf(Constants.DEFAULT_BAUD_RATE);
            configManager.setProperty(ConfigKeys.KEY_BUAD_RATE, buadRateStr);
            savePropertiesFlag = true;
        }
        // 设置默认选中
        int buadRate = Integer.parseInt(buadRateStr);
        baudRateInput.getSelectionModel().select(Constants.BUAD_RATE_LIST.indexOf(buadRate));

        // 设置#3.监听的SlaveId
        String slaveIdStr = configManager.getProperty(ConfigKeys.KEY_SLAVE_ID);
        if (StringUtils.isBlank(slaveIdStr)) {
            slaveIdStr = String.valueOf(Constants.DEFAULT_SLAVE_ID);
            configManager.setProperty(ConfigKeys.KEY_SLAVE_ID, slaveIdStr);
            savePropertiesFlag = true;
        }
        slaveIdInput.setText(slaveIdStr);

        // 设置#4.监听的寄存器Address
        String listeningAddress = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS);
        if (StringUtils.isBlank(listeningAddress)) {
            listeningAddress = String.valueOf(Constants.DEFAULT_LISTENING_ADDRESS);
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS, listeningAddress);
            savePropertiesFlag = true;
        }
        listeningAddressInput.setText(listeningAddress);

        // 设置#5.监听的寄存器FunctionType
        String functionType = configManager.getProperty(ConfigKeys.KEY_FUNCTION_TYPE);
        if (StringUtils.isBlank(functionType)) {
            functionType = Constants.MODBUS_FUNCTION_03;
            configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE, Constants.MODBUS_FUNCTION_03);
            savePropertiesFlag = true;
        }
        // 设置默认选中
        functionTypeInput.getSelectionModel().select(Constants.MODBUS_FUNCTION_LIST.indexOf(functionType));

        // 设置#6.监听的寄存器DataType
        String dataType = configManager.getProperty(ConfigKeys.KEY_DATA_TYPE);
        if (StringUtils.isBlank(dataType)) {
            dataType = DataTypeEnum.TWO_BYTE_INT_SIGNED.getName();
            configManager.setProperty(ConfigKeys.KEY_DATA_TYPE, DataTypeEnum.TWO_BYTE_INT_SIGNED.getName());
            savePropertiesFlag = true;
        }
        // 设置默认选中
        dataTypeInput.getSelectionModel().select(DataTypeEnum.indexOfName(dataType));

        // 设置#7.定时采集间隔（秒）
        String intervalSeconds = configManager.getProperty(ConfigKeys.KEY_INTERVAL_SECONDS);
        if (StringUtils.isBlank(intervalSeconds)) {
            intervalSeconds = String.valueOf(Constants.DEFAULT_INTERVAL_SECONDS);
            configManager.setProperty(ConfigKeys.KEY_INTERVAL_SECONDS, intervalSeconds);
            savePropertiesFlag = true;
        }
        intervalSecondsInput.setText(intervalSeconds);

        // 设置#8.Hook回调URL
        String callbackHookUrl = configManager.getProperty(ConfigKeys.KEY_CALLBACK_HOOK_URL);
        if (StringUtils.isBlank(callbackHookUrl)) {
            callbackHookUrl = serverConfig.buildUrl(Constants.DEFAULT_CALLBACK_HOOK_URI);
            configManager.setProperty(ConfigKeys.KEY_CALLBACK_HOOK_URL, callbackHookUrl);
            savePropertiesFlag = true;
        }
        callbackHookUrlInput.setText(callbackHookUrl);

        // 设置#8.Hook回调URL - 是否启用
        String callbackHookEnabledFlag = configManager.getProperty(ConfigKeys.KEY_CALLBACK_HOOK_ENABLED_FLAG);
        if (StringUtils.isBlank(callbackHookEnabledFlag)) {
            callbackHookEnabledFlag = Constants.TRUE;
            configManager.setProperty(ConfigKeys.KEY_CALLBACK_HOOK_ENABLED_FLAG, callbackHookEnabledFlag);
            savePropertiesFlag = true;
        }
        callbackHookEnabledInput.setSelected(Objects.equals(callbackHookEnabledFlag, Constants.TRUE));

        // 保存配置文件
        if (savePropertiesFlag) {
            configManager.saveProperties();
        }

        // 当采集状态为运行中时，禁用输入框
        portNameInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        baudRateInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        slaveIdInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        functionTypeInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        dataTypeInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        intervalSecondsInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        callbackHookUrlInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        callbackHookEnabledInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
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
    protected void onToggleCollectStatusMenuButtonClick() {
        if (mainWindowModel.isCollectRunningFlag()) {
            // 采集中，结束采集
            log.info("==== onToggleCollectStatusMenuButtonClick ==== 点击【结束采集】按钮！");
            this.modbusManager.stop();

            // 按钮变为【开始采集】按钮
            mainWindowModel.setToggleCollectStatusMenuButtonImage(ImageLoader.load(Constants.RUN_BLUE_IMAGE_PATH));
            mainWindowModel.setCollectRunningFlag(false);
        } else {
            // 开始采集
            log.info("==== onToggleCollectStatusMenuButtonClick ==== 点击【开始采集】按钮！");

            // 应用配置
            this.applySettings();

            // 开始采集
            this.modbusManager.start();

            // 按钮变为【结束采集】按钮
            mainWindowModel.setToggleCollectStatusMenuButtonImage(ImageLoader.load(Constants.STOP_RED_IMAGE_PATH));
            mainWindowModel.setCollectRunningFlag(true);
        }
    }


    @FXML
    protected void onRefreshSerialPortButtonClick() {
        log.info("==== onRefreshSerialPortButtonClick ==== 点击【刷新串口端口】按钮！");
        mainWindowModel.getSerialPortNameList().clear();
        mainWindowModel.getSerialPortNameList().addAll(Arrays.asList(SerialPortList.getPortNames()));

        log.info("==== onRefreshSerialPortButtonClick ==== 刷新串口端口列表：{}", mainWindowModel.getSerialPortNameList());
        // 设置默认选中
        String portName = configManager.getProperty(ConfigKeys.KEY_PORT_NAME);
        if (StringUtils.isBlank(portName)) {
            configManager.setProperty(ConfigKeys.KEY_PORT_NAME, Constants.DEFAULT_PORT_NAME);
            configManager.saveProperties();
        }
        // 设置默认选中
        portNameInput.getSelectionModel().select(mainWindowModel.getSerialPortNameList().indexOf(portName));
        log.info("==== onRefreshSerialPortButtonClick ==== 默认选中的串口：{}", portName);
    }

    protected void applySettings() {
        // 设置#1.串口名称
        String portName = portNameInput.getSelectionModel().getSelectedItem();
        if (StringUtils.isNotBlank(portName)) {
            log.info("==== applySettings ==== portName = {}", portName);
            settingsWindowModel.setPortName(portName);
            configManager.setProperty(ConfigKeys.KEY_PORT_NAME, portName);
        } else {
            this.mainWindowModel.displayBottomText("请选择通信的串口端口！");
            throw new RuntimeException("请选择通信的串口端口！");
        }

        // 设置#2.波特率（bps）
        Integer buadRate = baudRateInput.getSelectionModel().getSelectedItem();
        if (null != buadRate) {
            log.info("==== applySettings ==== buadRate = {}", buadRate);
            settingsWindowModel.setBaudRate(buadRate);
            String str = String.valueOf(buadRate);
            configManager.setProperty(ConfigKeys.KEY_BUAD_RATE, str);
        } else {
            this.mainWindowModel.displayBottomText("请选择通信的波特率！");
            throw new RuntimeException("请选择通信的波特率！");
        }

        // 设置#3.监听的SlaveId
        String slaveIdStr = slaveIdInput.getText();
        if (StringUtils.isNotBlank(slaveIdStr) && NumberUtils.isCreatable(slaveIdStr)) {
            int slaveId = Integer.parseInt(slaveIdStr);
            if (slaveId <= 0 || slaveId > 255) {
                this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
            }
            log.info("==== applySettings ==== slaveId = {}", slaveId);
            settingsWindowModel.setSlaveId(slaveId);
            configManager.setProperty(ConfigKeys.KEY_SLAVE_ID, slaveIdStr);
        } else {
            this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
            throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
        }

        // 设置#4.监听的寄存器Address
        String listeningAddressStr = listeningAddressInput.getText();
        if (StringUtils.isNotBlank(listeningAddressStr) && NumberUtils.isCreatable(listeningAddressStr)) {
            int listeningAddress = Integer.parseInt(listeningAddressStr);
            if (listeningAddress < 0 || listeningAddress > 65535) {
                this.mainWindowModel.displayBottomText("请输入正确的Address（0 to 65535）！");
                throw new RuntimeException("请输入正确的Address（0 to 65535）！");
            }
            log.info("==== applySettings ==== listeningAddress = {}", listeningAddress);
            settingsWindowModel.setListeningAddress(listeningAddress);
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS, listeningAddressStr);
        } else {
            this.mainWindowModel.displayBottomText("请输入正确的Address（0 to 65535）！");
            throw new RuntimeException("请输入正确的Address（0 to 65535）！");
        }

        // 设置#5.监听的寄存器FunctionType
        String functionType = functionTypeInput.getSelectionModel().getSelectedItem();
        if (StringUtils.isNotBlank(functionType)) {
            log.info("==== applySettings ==== functionType = {}", functionType);
            settingsWindowModel.setFunctionType(functionType);
            configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE, functionType);
        } else {
            this.mainWindowModel.displayBottomText("请选择Function！");
            throw new RuntimeException("请选择Function！");
        }

        // 设置#6.监听的寄存器DataType
        String dataType = dataTypeInput.getSelectionModel().getSelectedItem();
        if (StringUtils.isNotBlank(dataType)) {
            log.info("==== applySettings ==== dataType = {}", dataType);
            DataTypeEnum dataTypeEnum = DataTypeEnum.ofDisplayName(dataType);
            settingsWindowModel.setDataType(dataTypeEnum.getValue());
            configManager.setProperty(ConfigKeys.KEY_DATA_TYPE, dataTypeEnum.getName());
        } else {
            this.mainWindowModel.displayBottomText("请选择Function！");
            throw new RuntimeException("请选择Function！");
        }

        // 设置#7.定时采集间隔（秒）
        int intervalSeconds = this.processIntervalSecondsInput(intervalSecondsInput.getText());
        String intervalSecondsText = String.valueOf(intervalSeconds);
        intervalSecondsInput.setText(intervalSecondsText);
        settingsWindowModel.setIntervalSeconds(intervalSeconds);
        configManager.setProperty(ConfigKeys.KEY_INTERVAL_SECONDS, intervalSecondsText);

        // 设置#8.Hook回调URL
        String callbackHookUrl = this.processCallbackHookUrlInput(callbackHookUrlInput.getText());
        callbackHookUrlInput.setText(callbackHookUrl);
        settingsWindowModel.setCallbackHookUrl(callbackHookUrl);
        configManager.setProperty(ConfigKeys.KEY_CALLBACK_HOOK_URL, callbackHookUrl);

        // 设置#8.Hook回调URL - 是否启用
        boolean callbackHookEnabledFlag = callbackHookEnabledInput.isSelected();
        settingsWindowModel.setCallbackHookEnabledFlag(callbackHookEnabledFlag);
        configManager.setProperty(ConfigKeys.KEY_CALLBACK_HOOK_ENABLED_FLAG, callbackHookEnabledFlag ? Constants.TRUE : Constants.FALSE);

        // 保存配置文件
        configManager.saveProperties();
    }


    /**
     * 限制只能输入数字，并限制输入数字的范围：1~120
     *
     * @param input 输入文本
     * @return 处理后的输入文本
     */
    private int processIntervalSecondsInput(String input) {
        log.info("==== 处理输入值 ==== 原始输入：{}", input);
        if (StringUtils.isBlank(input)) {
            return Constants.DEFAULT_INTERVAL_SECONDS;
        }
        if (!input.matches("\\d*")) {
            input = input.replaceAll("[^\\d]","");
        }
        log.info("==== 处理输入值 ==== 去除非数字文本之后：{}", input);
        if (StringUtils.isBlank(input)) {
            return Constants.DEFAULT_INTERVAL_SECONDS;
        }
        int value = Integer.parseInt(input);
        log.info("==== 处理输入值 ==== 转为数字之后：{}", value);
        if (value < Constants.MIN_INTERVAL_SECONDS) {
            value = Constants.MIN_INTERVAL_SECONDS;
        } else if (value > Constants.MAX_INTERVAL_SECONDS) {
            value = Constants.MAX_INTERVAL_SECONDS;
        }
        log.info("==== 处理输入值 ==== 限制数字范围之后：{}", value);
        return value;
    }

    private String processCallbackHookUrlInput(String input) {
        log.info("==== 处理输入值 ==== 原始输入：{}", input);
        if (StringUtils.isNotBlank(input)) {
            if (input.startsWith("http://") || input.startsWith("https://")) {
                try {
                    // This will throw an exception if the URL is malformed
                    new URL(input);
                    return input;
                } catch (MalformedURLException e) {
                    log.error("URL格式错误！>> " + input, e);
                }
            }
        }
        return serverConfig.buildUrl(Constants.DEFAULT_CALLBACK_HOOK_URI);
    }

}
