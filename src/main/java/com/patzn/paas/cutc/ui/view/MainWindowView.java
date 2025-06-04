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


    // --------------------------------[ 寄存器#1 ]--------------------------------

    @FXML
    public TextField slaveIdInput;

    @FXML
    public CheckBox listeningAddressEnabledInput;

    @FXML
    private TextField listeningAddressInput;

    @FXML
    public ChoiceBox<String> functionTypeInput;

    @FXML
    public ChoiceBox<String> dataTypeInput;

    // --------------------------------[ 寄存器#2 ]--------------------------------


    @FXML
    public TextField slaveIdInput2;

    @FXML
    public CheckBox listeningAddressEnabledInput2;

    @FXML
    private TextField listeningAddressInput2;

    @FXML
    public ChoiceBox<String> functionTypeInput2;

    @FXML
    public ChoiceBox<String> dataTypeInput2;

    // --------------------------------[ 寄存器#3 ]--------------------------------

    @FXML
    public TextField slaveIdInput3;

    @FXML
    public CheckBox listeningAddressEnabledInput3;

    @FXML
    private TextField listeningAddressInput3;

    @FXML
    public ChoiceBox<String> functionTypeInput3;

    @FXML
    public ChoiceBox<String> dataTypeInput3;


    // --------------------------------[ 寄存器#4 ]--------------------------------

    @FXML
    public TextField slaveIdInput4;

    @FXML
    public CheckBox listeningAddressEnabledInput4;

    @FXML
    private TextField listeningAddressInput4;

    @FXML
    public ChoiceBox<String> functionTypeInput4;

    @FXML
    public ChoiceBox<String> dataTypeInput4;

    // --------------------------------[ 寄存器#5 ]--------------------------------

    @FXML
    public TextField slaveIdInput5;

    @FXML
    public CheckBox listeningAddressEnabledInput5;

    @FXML
    private TextField listeningAddressInput5;

    @FXML
    public ChoiceBox<String> functionTypeInput5;

    @FXML
    public ChoiceBox<String> dataTypeInput5;


    // --------------------------------[ 寄存器#6 ]--------------------------------

    @FXML
    public TextField slaveIdInput6;

    @FXML
    public CheckBox listeningAddressEnabledInput6;

    @FXML
    private TextField listeningAddressInput6;

    @FXML
    public ChoiceBox<String> functionTypeInput6;

    @FXML
    public ChoiceBox<String> dataTypeInput6;


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
            log.info("==== 寄存器#1 - 选择Function ==== {}", newValue);
            if (null != newValue) {
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE, newValue);
                settingsWindowModel.setFunctionType(newValue);
            }
        });

        dataTypeInput.setItems(FXCollections.observableList(DataTypeEnum.getDisplayNameList()));
        dataTypeInput.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#1 - 选择DataType ==== {}", newValue);
            if (null != newValue) {
                DataTypeEnum newValueEnum = DataTypeEnum.ofDisplayName(newValue);
                if (null != newValueEnum) {
                    configManager.setProperty(ConfigKeys.KEY_DATA_TYPE, newValueEnum.getName());
                    settingsWindowModel.setDataType(newValueEnum.getValue());
                }
            }
        });


        functionTypeInput2.setItems(FXCollections.observableList(Constants.MODBUS_FUNCTION_LIST));
        functionTypeInput2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#2 - 选择Function ==== {}", newValue);
            if (null != newValue) {
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_2, newValue);
                settingsWindowModel.setFunctionType2(newValue);
            }
        });

        dataTypeInput2.setItems(FXCollections.observableList(DataTypeEnum.getDisplayNameList()));
        dataTypeInput2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#2 - 选择DataType ==== {}", newValue);
            if (null != newValue) {
                DataTypeEnum newValueEnum = DataTypeEnum.ofDisplayName(newValue);
                if (null != newValueEnum) {
                    configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_2, newValueEnum.getName());
                    settingsWindowModel.setDataType2(newValueEnum.getValue());
                }
            }
        });


        functionTypeInput3.setItems(FXCollections.observableList(Constants.MODBUS_FUNCTION_LIST));
        functionTypeInput3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#3 - 选择Function ==== {}", newValue);
            if (null != newValue) {
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_3, newValue);
                settingsWindowModel.setFunctionType3(newValue);
            }
        });

        dataTypeInput3.setItems(FXCollections.observableList(DataTypeEnum.getDisplayNameList()));
        dataTypeInput3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#3 - 选择DataType ==== {}", newValue);
            if (null != newValue) {
                DataTypeEnum newValueEnum = DataTypeEnum.ofDisplayName(newValue);
                if (null != newValueEnum) {
                    configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_3, newValueEnum.getName());
                    settingsWindowModel.setDataType3(newValueEnum.getValue());
                }
            }
        });


        functionTypeInput4.setItems(FXCollections.observableList(Constants.MODBUS_FUNCTION_LIST));
        functionTypeInput4.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#4 - 选择Function ==== {}", newValue);
            if (null != newValue) {
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_4, newValue);
                settingsWindowModel.setFunctionType4(newValue);
            }
        });

        dataTypeInput4.setItems(FXCollections.observableList(DataTypeEnum.getDisplayNameList()));
        dataTypeInput4.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#4 - 选择DataType ==== {}", newValue);
            if (null != newValue) {
                DataTypeEnum newValueEnum = DataTypeEnum.ofDisplayName(newValue);
                if (null != newValueEnum) {
                    configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_4, newValueEnum.getName());
                    settingsWindowModel.setDataType4(newValueEnum.getValue());
                }
            }
        });


        functionTypeInput5.setItems(FXCollections.observableList(Constants.MODBUS_FUNCTION_LIST));
        functionTypeInput5.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#5 - 选择Function ==== {}", newValue);
            if (null != newValue) {
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_5, newValue);
                settingsWindowModel.setFunctionType5(newValue);
            }
        });

        dataTypeInput5.setItems(FXCollections.observableList(DataTypeEnum.getDisplayNameList()));
        dataTypeInput5.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#5 - 选择DataType ==== {}", newValue);
            if (null != newValue) {
                DataTypeEnum newValueEnum = DataTypeEnum.ofDisplayName(newValue);
                if (null != newValueEnum) {
                    configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_5, newValueEnum.getName());
                    settingsWindowModel.setDataType5(newValueEnum.getValue());
                }
            }
        });


        functionTypeInput6.setItems(FXCollections.observableList(Constants.MODBUS_FUNCTION_LIST));
        functionTypeInput6.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#6 - 选择Function ==== {}", newValue);
            if (null != newValue) {
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_6, newValue);
                settingsWindowModel.setFunctionType6(newValue);
            }
        });

        dataTypeInput6.setItems(FXCollections.observableList(DataTypeEnum.getDisplayNameList()));
        dataTypeInput6.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            log.info("==== 寄存器#6 - 选择DataType ==== {}", newValue);
            if (null != newValue) {
                DataTypeEnum newValueEnum = DataTypeEnum.ofDisplayName(newValue);
                if (null != newValueEnum) {
                    configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_6, newValueEnum.getName());
                    settingsWindowModel.setDataType6(newValueEnum.getValue());
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



        // 设置#3.寄存器#1 - 是否启用监听
        String listeningAddressEnabledFlag = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG);
        if (StringUtils.isBlank(listeningAddressEnabledFlag)) {
            listeningAddressEnabledFlag = Constants.TRUE;
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG, listeningAddressEnabledFlag);
            savePropertiesFlag = true;
        }
        listeningAddressEnabledInput.setSelected(Objects.equals(listeningAddressEnabledFlag, Constants.TRUE));

        // 设置#3.寄存器#1 - 监听的SlaveId
        String slaveIdStr = configManager.getProperty(ConfigKeys.KEY_SLAVE_ID);
        if (StringUtils.isBlank(slaveIdStr)) {
            slaveIdStr = String.valueOf(Constants.DEFAULT_SLAVE_ID);
            configManager.setProperty(ConfigKeys.KEY_SLAVE_ID, slaveIdStr);
            savePropertiesFlag = true;
        }
        slaveIdInput.setText(slaveIdStr);
        
        // 设置#3.寄存器#1 - 监听的寄存器Address
        String listeningAddress = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS);
        if (StringUtils.isBlank(listeningAddress)) {
            listeningAddress = String.valueOf(Constants.DEFAULT_LISTENING_ADDRESS);
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS, listeningAddress);
            savePropertiesFlag = true;
        }
        listeningAddressInput.setText(listeningAddress);

        // 设置#3.寄存器#1 - 监听的寄存器FunctionType
        String functionType = configManager.getProperty(ConfigKeys.KEY_FUNCTION_TYPE);
        if (StringUtils.isBlank(functionType)) {
            functionType = Constants.MODBUS_FUNCTION_03;
            configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE, Constants.MODBUS_FUNCTION_03);
            savePropertiesFlag = true;
        }
        // 设置默认选中
        functionTypeInput.getSelectionModel().select(Constants.MODBUS_FUNCTION_LIST.indexOf(functionType));

        // 设置#3.寄存器#1 - 监听的寄存器DataType
        String dataType = configManager.getProperty(ConfigKeys.KEY_DATA_TYPE);
        if (StringUtils.isBlank(dataType)) {
            dataType = DataTypeEnum.TWO_BYTE_INT_SIGNED.getName();
            configManager.setProperty(ConfigKeys.KEY_DATA_TYPE, DataTypeEnum.TWO_BYTE_INT_SIGNED.getName());
            savePropertiesFlag = true;
        }
        // 设置默认选中
        dataTypeInput.getSelectionModel().select(DataTypeEnum.indexOfName(dataType));



        // 设置#4.寄存器#2 - 是否启用监听
        String listeningAddressEnabledFlag2 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_2);
        if (StringUtils.isBlank(listeningAddressEnabledFlag2)) {
            listeningAddressEnabledFlag2 = Constants.TRUE;
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_2, listeningAddressEnabledFlag2);
            savePropertiesFlag = true;
        }
        listeningAddressEnabledInput2.setSelected(Objects.equals(listeningAddressEnabledFlag2, Constants.TRUE));

        // 设置#4.寄存器#2 - 监听的SlaveId
        String slaveIdStr2 = configManager.getProperty(ConfigKeys.KEY_SLAVE_ID_2);
        if (StringUtils.isBlank(slaveIdStr2)) {
            slaveIdStr2 = String.valueOf(Constants.DEFAULT_SLAVE_ID);
            configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_2, slaveIdStr2);
            savePropertiesFlag = true;
        }
        slaveIdInput2.setText(slaveIdStr2);
        
        // 设置#4.寄存器#2 - 监听的寄存器Address
        String listeningAddress2 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_2);
        if (StringUtils.isBlank(listeningAddress2)) {
            listeningAddress2 = String.valueOf(Constants.DEFAULT_LISTENING_ADDRESS);
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_2, listeningAddress2);
            savePropertiesFlag = true;
        }
        listeningAddressInput2.setText(listeningAddress2);

        // 设置#4.寄存器#2 - 监听的寄存器FunctionType
        String functionType2 = configManager.getProperty(ConfigKeys.KEY_FUNCTION_TYPE_2);
        if (StringUtils.isBlank(functionType2)) {
            functionType2 = Constants.MODBUS_FUNCTION_03;
            configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_2, Constants.MODBUS_FUNCTION_03);
            savePropertiesFlag = true;
        }
        // 设置默认选中
        functionTypeInput2.getSelectionModel().select(Constants.MODBUS_FUNCTION_LIST.indexOf(functionType2));

        // 设置#4.寄存器#2 - 监听的寄存器DataType
        String dataType2 = configManager.getProperty(ConfigKeys.KEY_DATA_TYPE_2);
        if (StringUtils.isBlank(dataType2)) {
            dataType2 = DataTypeEnum.TWO_BYTE_INT_SIGNED.getName();
            configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_2, DataTypeEnum.TWO_BYTE_INT_SIGNED.getName());
            savePropertiesFlag = true;
        }
        // 设置默认选中
        dataTypeInput2.getSelectionModel().select(DataTypeEnum.indexOfName(dataType2));


        // 设置#5.寄存器#3 - 是否启用监听
        String listeningAddressEnabledFlag3 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_3);
        if (StringUtils.isBlank(listeningAddressEnabledFlag3)) {
            listeningAddressEnabledFlag3 = Constants.TRUE;
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_3, listeningAddressEnabledFlag3);
            savePropertiesFlag = true;
        }
        listeningAddressEnabledInput3.setSelected(Objects.equals(listeningAddressEnabledFlag3, Constants.TRUE));

        // 设置#5.寄存器#3 - 监听的SlaveId
        String slaveIdStr3 = configManager.getProperty(ConfigKeys.KEY_SLAVE_ID_3);
        if (StringUtils.isBlank(slaveIdStr3)) {
            slaveIdStr3 = String.valueOf(Constants.DEFAULT_SLAVE_ID);
            configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_3, slaveIdStr3);
            savePropertiesFlag = true;
        }
        slaveIdInput3.setText(slaveIdStr3);

        // 设置#5.寄存器#3 - 监听的寄存器Address
        String listeningAddress3 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_3);
        if (StringUtils.isBlank(listeningAddress3)) {
            listeningAddress3 = String.valueOf(Constants.DEFAULT_LISTENING_ADDRESS);
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_3, listeningAddress3);
            savePropertiesFlag = true;
        }
        listeningAddressInput3.setText(listeningAddress3);

        // 设置#5.寄存器#3 - 监听的寄存器FunctionType
        String functionType3 = configManager.getProperty(ConfigKeys.KEY_FUNCTION_TYPE_3);
        if (StringUtils.isBlank(functionType3)) {
            functionType3 = Constants.MODBUS_FUNCTION_03;
            configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_3, Constants.MODBUS_FUNCTION_03);
            savePropertiesFlag = true;
        }
        // 设置默认选中
        functionTypeInput3.getSelectionModel().select(Constants.MODBUS_FUNCTION_LIST.indexOf(functionType3));

        // 设置#5.寄存器#3 - 监听的寄存器DataType
        String dataType3 = configManager.getProperty(ConfigKeys.KEY_DATA_TYPE_3);
        if (StringUtils.isBlank(dataType3)) {
            dataType3 = DataTypeEnum.TWO_BYTE_INT_SIGNED.getName();
            configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_3, DataTypeEnum.TWO_BYTE_INT_SIGNED.getName());
            savePropertiesFlag = true;
        }
        // 设置默认选中
        dataTypeInput3.getSelectionModel().select(DataTypeEnum.indexOfName(dataType3));



        // 设置#6.寄存器#4 - 是否启用监听
        String listeningAddressEnabledFlag4 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_4);
        if (StringUtils.isBlank(listeningAddressEnabledFlag4)) {
            listeningAddressEnabledFlag4 = Constants.TRUE;
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_4, listeningAddressEnabledFlag4);
            savePropertiesFlag = true;
        }
        listeningAddressEnabledInput4.setSelected(Objects.equals(listeningAddressEnabledFlag4, Constants.TRUE));

        // 设置#6.寄存器#4 - 监听的SlaveId
        String slaveIdStr4 = configManager.getProperty(ConfigKeys.KEY_SLAVE_ID_4);
        if (StringUtils.isBlank(slaveIdStr4)) {
            slaveIdStr4 = String.valueOf(Constants.DEFAULT_SLAVE_ID);
            configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_4, slaveIdStr4);
            savePropertiesFlag = true;
        }
        slaveIdInput4.setText(slaveIdStr4);

        // 设置#6.寄存器#4 - 监听的寄存器Address
        String listeningAddress4 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_4);
        if (StringUtils.isBlank(listeningAddress4)) {
            listeningAddress4 = String.valueOf(Constants.DEFAULT_LISTENING_ADDRESS);
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_4, listeningAddress4);
            savePropertiesFlag = true;
        }
        listeningAddressInput4.setText(listeningAddress4);

        // 设置#6.寄存器#4 - 监听的寄存器FunctionType
        String functionType4 = configManager.getProperty(ConfigKeys.KEY_FUNCTION_TYPE_4);
        if (StringUtils.isBlank(functionType4)) {
            functionType4 = Constants.MODBUS_FUNCTION_03;
            configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_4, Constants.MODBUS_FUNCTION_03);
            savePropertiesFlag = true;
        }
        // 设置默认选中
        functionTypeInput4.getSelectionModel().select(Constants.MODBUS_FUNCTION_LIST.indexOf(functionType4));

        // 设置#6.寄存器#4 - 监听的寄存器DataType
        String dataType4 = configManager.getProperty(ConfigKeys.KEY_DATA_TYPE_4);
        if (StringUtils.isBlank(dataType4)) {
            dataType4 = DataTypeEnum.TWO_BYTE_INT_SIGNED.getName();
            configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_4, DataTypeEnum.TWO_BYTE_INT_SIGNED.getName());
            savePropertiesFlag = true;
        }
        // 设置默认选中
        dataTypeInput4.getSelectionModel().select(DataTypeEnum.indexOfName(dataType4));





        // 设置#7.寄存器#5 - 是否启用监听
        String listeningAddressEnabledFlag5 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_5);
        if (StringUtils.isBlank(listeningAddressEnabledFlag5)) {
            listeningAddressEnabledFlag5 = Constants.TRUE;
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_5, listeningAddressEnabledFlag5);
            savePropertiesFlag = true;
        }
        listeningAddressEnabledInput5.setSelected(Objects.equals(listeningAddressEnabledFlag5, Constants.TRUE));

        // 设置#7.寄存器#5 - 监听的SlaveId
        String slaveIdStr5 = configManager.getProperty(ConfigKeys.KEY_SLAVE_ID_5);
        if (StringUtils.isBlank(slaveIdStr5)) {
            slaveIdStr5 = String.valueOf(Constants.DEFAULT_SLAVE_ID);
            configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_5, slaveIdStr5);
            savePropertiesFlag = true;
        }
        slaveIdInput5.setText(slaveIdStr5);

        // 设置#7.寄存器#5 - 监听的寄存器Address
        String listeningAddress5 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_5);
        if (StringUtils.isBlank(listeningAddress5)) {
            listeningAddress5 = String.valueOf(Constants.DEFAULT_LISTENING_ADDRESS);
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_5, listeningAddress5);
            savePropertiesFlag = true;
        }
        listeningAddressInput5.setText(listeningAddress5);

        // 设置#7.寄存器#5 - 监听的寄存器FunctionType
        String functionType5 = configManager.getProperty(ConfigKeys.KEY_FUNCTION_TYPE_5);
        if (StringUtils.isBlank(functionType5)) {
            functionType5 = Constants.MODBUS_FUNCTION_03;
            configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_5, Constants.MODBUS_FUNCTION_03);
            savePropertiesFlag = true;
        }
        // 设置默认选中
        functionTypeInput5.getSelectionModel().select(Constants.MODBUS_FUNCTION_LIST.indexOf(functionType5));

        // 设置#7.寄存器#5 - 监听的寄存器DataType
        String dataType5 = configManager.getProperty(ConfigKeys.KEY_DATA_TYPE_5);
        if (StringUtils.isBlank(dataType5)) {
            dataType5 = DataTypeEnum.TWO_BYTE_INT_SIGNED.getName();
            configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_5, DataTypeEnum.TWO_BYTE_INT_SIGNED.getName());
            savePropertiesFlag = true;
        }
        // 设置默认选中
        dataTypeInput5.getSelectionModel().select(DataTypeEnum.indexOfName(dataType5));




        // 设置#8.寄存器#6 - 是否启用监听
        String listeningAddressEnabledFlag6 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_6);
        if (StringUtils.isBlank(listeningAddressEnabledFlag6)) {
            listeningAddressEnabledFlag6 = Constants.TRUE;
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_6, listeningAddressEnabledFlag6);
            savePropertiesFlag = true;
        }
        listeningAddressEnabledInput6.setSelected(Objects.equals(listeningAddressEnabledFlag6, Constants.TRUE));

        // 设置#8.寄存器#6 - 监听的SlaveId
        String slaveIdStr6 = configManager.getProperty(ConfigKeys.KEY_SLAVE_ID_6);
        if (StringUtils.isBlank(slaveIdStr6)) {
            slaveIdStr6 = String.valueOf(Constants.DEFAULT_SLAVE_ID);
            configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_6, slaveIdStr6);
            savePropertiesFlag = true;
        }
        slaveIdInput6.setText(slaveIdStr6);

        // 设置#8.寄存器#6 - 监听的寄存器Address
        String listeningAddress6 = configManager.getProperty(ConfigKeys.KEY_LISTENING_ADDRESS_6);
        if (StringUtils.isBlank(listeningAddress6)) {
            listeningAddress6 = String.valueOf(Constants.DEFAULT_LISTENING_ADDRESS);
            configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_6, listeningAddress6);
            savePropertiesFlag = true;
        }
        listeningAddressInput6.setText(listeningAddress6);

        // 设置#8.寄存器#6 - 监听的寄存器FunctionType
        String functionType6 = configManager.getProperty(ConfigKeys.KEY_FUNCTION_TYPE_6);
        if (StringUtils.isBlank(functionType6)) {
            functionType6 = Constants.MODBUS_FUNCTION_03;
            configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_6, Constants.MODBUS_FUNCTION_03);
            savePropertiesFlag = true;
        }
        // 设置默认选中
        functionTypeInput6.getSelectionModel().select(Constants.MODBUS_FUNCTION_LIST.indexOf(functionType6));

        // 设置#8.寄存器#6 - 监听的寄存器DataType
        String dataType6 = configManager.getProperty(ConfigKeys.KEY_DATA_TYPE_6);
        if (StringUtils.isBlank(dataType6)) {
            dataType6 = DataTypeEnum.TWO_BYTE_INT_SIGNED.getName();
            configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_6, DataTypeEnum.TWO_BYTE_INT_SIGNED.getName());
            savePropertiesFlag = true;
        }
        // 设置默认选中
        dataTypeInput6.getSelectionModel().select(DataTypeEnum.indexOfName(dataType6));





        // 设置#9.定时采集间隔（秒）
        String intervalSeconds = configManager.getProperty(ConfigKeys.KEY_INTERVAL_SECONDS);
        if (StringUtils.isBlank(intervalSeconds)) {
            intervalSeconds = String.valueOf(Constants.DEFAULT_INTERVAL_SECONDS);
            configManager.setProperty(ConfigKeys.KEY_INTERVAL_SECONDS, intervalSeconds);
            savePropertiesFlag = true;
        }
        intervalSecondsInput.setText(intervalSeconds);

        // 设置#10.Hook回调URL
        String callbackHookUrl = configManager.getProperty(ConfigKeys.KEY_CALLBACK_HOOK_URL);
        if (StringUtils.isBlank(callbackHookUrl)) {
            callbackHookUrl = serverConfig.buildUrl(Constants.DEFAULT_CALLBACK_HOOK_URI);
            configManager.setProperty(ConfigKeys.KEY_CALLBACK_HOOK_URL, callbackHookUrl);
            savePropertiesFlag = true;
        }
        callbackHookUrlInput.setText(callbackHookUrl);

        // 设置#10.Hook回调URL - 是否启用
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
        listeningAddressEnabledInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        functionTypeInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        dataTypeInput.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());

        slaveIdInput2.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressEnabledInput2.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressInput2.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        functionTypeInput2.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        dataTypeInput2.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());

        slaveIdInput3.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressEnabledInput3.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressInput3.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        functionTypeInput3.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        dataTypeInput3.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());

        slaveIdInput4.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressEnabledInput4.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressInput4.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        functionTypeInput4.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        dataTypeInput4.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        
        slaveIdInput5.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressEnabledInput5.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressInput5.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        functionTypeInput5.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        dataTypeInput5.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());

        slaveIdInput6.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressEnabledInput6.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        listeningAddressInput6.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        functionTypeInput6.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        dataTypeInput6.disableProperty().bindBidirectional(mainWindowModel.collectRunningFlagProperty());
        
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


        // 设置#3.寄存器#1 - 是否启用监听
        boolean listeningAddressEnabledFlag = listeningAddressEnabledInput.isSelected();
        settingsWindowModel.setListeningAddressEnabledFlag(listeningAddressEnabledFlag);
        configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG, listeningAddressEnabledFlag ? Constants.TRUE : Constants.FALSE);
        if(listeningAddressEnabledFlag) {
            // 设置#3.寄存器#1 - 监听的SlaveId
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
            
            // 设置#3.寄存器#1 - 监听的寄存器Address
            String listeningAddressStr = listeningAddressInput.getText();
            if (StringUtils.isNotBlank(listeningAddressStr) && NumberUtils.isCreatable(listeningAddressStr)) {
                int listeningAddress = Integer.parseInt(listeningAddressStr);
                if (listeningAddress < 0 || listeningAddress > 65535) {
                    this.mainWindowModel.displayBottomText("寄存器#1 - 请输入正确的Address（0 to 65535）！");
                    throw new RuntimeException("寄存器#1 - 请输入正确的Address（0 to 65535）！");
                }
                log.info("==== applySettings ==== 寄存器#1 - listeningAddress = {}", listeningAddress);
                settingsWindowModel.setListeningAddress(listeningAddress);
                configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS, listeningAddressStr);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#1 - 请输入正确的Address（0 to 65535）！");
                throw new RuntimeException("寄存器#1 - 请输入正确的Address（0 to 65535）！");
            }

            // 设置#3.寄存器#1 - 监听的寄存器FunctionType
            String functionType = functionTypeInput.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(functionType)) {
                log.info("==== applySettings ==== 寄存器#1 - functionType = {}", functionType);
                settingsWindowModel.setFunctionType(functionType);
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE, functionType);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#1 - 请选择Function！");
                throw new RuntimeException("寄存器#1 - 请选择Function！");
            }

            // 设置#3.寄存器#1 - 监听的寄存器DataType
            String dataType = dataTypeInput.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(dataType)) {
                log.info("==== applySettings ==== 寄存器#1 - dataType = {}", dataType);
                DataTypeEnum dataTypeEnum = DataTypeEnum.ofDisplayName(dataType);
                settingsWindowModel.setDataType(dataTypeEnum.getValue());
                configManager.setProperty(ConfigKeys.KEY_DATA_TYPE, dataTypeEnum.getName());
            } else {
                this.mainWindowModel.displayBottomText("寄存器#1 - 请选择Function！");
                throw new RuntimeException("寄存器#1 - 请选择Function！");
            }
        }


        // 设置#4.寄存器#2 - 是否启用监听
        boolean listeningAddressEnabledFlag2 = listeningAddressEnabledInput2.isSelected();
        settingsWindowModel.setListeningAddressEnabledFlag2(listeningAddressEnabledFlag2);
        configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_2, listeningAddressEnabledFlag2 ? Constants.TRUE : Constants.FALSE);
        if(listeningAddressEnabledFlag2) {
            // 设置#4.寄存器#2 - 监听的SlaveId
            String slaveIdStr = slaveIdInput2.getText();
            if (StringUtils.isNotBlank(slaveIdStr) && NumberUtils.isCreatable(slaveIdStr)) {
                int slaveId = Integer.parseInt(slaveIdStr);
                if (slaveId <= 0 || slaveId > 255) {
                    this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                    throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
                }
                log.info("==== applySettings ==== slaveId = {}", slaveId);
                settingsWindowModel.setSlaveId2(slaveId);
                configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_2, slaveIdStr);
            } else {
                this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
            }

            // 设置#4.寄存器#2 - 监听的寄存器Address
            String listeningAddressStr = listeningAddressInput2.getText();
            if (StringUtils.isNotBlank(listeningAddressStr) && NumberUtils.isCreatable(listeningAddressStr)) {
                int listeningAddress = Integer.parseInt(listeningAddressStr);
                if (listeningAddress < 0 || listeningAddress > 65535) {
                    this.mainWindowModel.displayBottomText("寄存器#2 - 请输入正确的Address（0 to 65535）！");
                    throw new RuntimeException("寄存器#2 - 请输入正确的Address（0 to 65535）！");
                }
                log.info("==== applySettings ==== 寄存器#2 - listeningAddress = {}", listeningAddress);
                settingsWindowModel.setListeningAddress2(listeningAddress);
                configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_2, listeningAddressStr);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#2 - 请输入正确的Address（0 to 65535）！");
                throw new RuntimeException("寄存器#2 - 请输入正确的Address（0 to 65535）！");
            }

            // 设置#4.寄存器#2 - 监听的寄存器FunctionType
            String functionType = functionTypeInput2.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(functionType)) {
                log.info("==== applySettings ==== 寄存器#2 - functionType = {}", functionType);
                settingsWindowModel.setFunctionType2(functionType);
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_2, functionType);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#2 - 请选择Function！");
                throw new RuntimeException("寄存器#2 - 请选择Function！");
            }

            // 设置#4.寄存器#2 - 监听的寄存器DataType
            String dataType = dataTypeInput2.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(dataType)) {
                log.info("==== applySettings ==== 寄存器#2 - dataType = {}", dataType);
                DataTypeEnum dataTypeEnum = DataTypeEnum.ofDisplayName(dataType);
                settingsWindowModel.setDataType2(dataTypeEnum.getValue());
                configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_2, dataTypeEnum.getName());
            } else {
                this.mainWindowModel.displayBottomText("寄存器#2 - 请选择Function！");
                throw new RuntimeException("寄存器#2 - 请选择Function！");
            }
        }


        // 设置#5.寄存器#3 - 是否启用监听
        boolean listeningAddressEnabledFlag3 = listeningAddressEnabledInput3.isSelected();
        settingsWindowModel.setListeningAddressEnabledFlag3(listeningAddressEnabledFlag3);
        configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_3, listeningAddressEnabledFlag3 ? Constants.TRUE : Constants.FALSE);
        if(listeningAddressEnabledFlag3) {
            // 设置#5.寄存器#3 - 监听的SlaveId
            String slaveIdStr = slaveIdInput3.getText();
            if (StringUtils.isNotBlank(slaveIdStr) && NumberUtils.isCreatable(slaveIdStr)) {
                int slaveId = Integer.parseInt(slaveIdStr);
                if (slaveId <= 0 || slaveId > 255) {
                    this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                    throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
                }
                log.info("==== applySettings ==== slaveId = {}", slaveId);
                settingsWindowModel.setSlaveId3(slaveId);
                configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_3, slaveIdStr);
            } else {
                this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
            }
            
            // 设置#5.寄存器#3 - 监听的寄存器Address
            String listeningAddressStr = listeningAddressInput3.getText();
            if (StringUtils.isNotBlank(listeningAddressStr) && NumberUtils.isCreatable(listeningAddressStr)) {
                int listeningAddress = Integer.parseInt(listeningAddressStr);
                if (listeningAddress < 0 || listeningAddress > 65535) {
                    this.mainWindowModel.displayBottomText("寄存器#3 - 请输入正确的Address（0 to 65535）！");
                    throw new RuntimeException("寄存器#3 - 请输入正确的Address（0 to 65535）！");
                }
                log.info("==== applySettings ==== 寄存器#3 - listeningAddress = {}", listeningAddress);
                settingsWindowModel.setListeningAddress3(listeningAddress);
                configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_3, listeningAddressStr);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#3 - 请输入正确的Address（0 to 65535）！");
                throw new RuntimeException("寄存器#3 - 请输入正确的Address（0 to 65535）！");
            }

            // 设置#5.寄存器#3 - 监听的寄存器FunctionType
            String functionType = functionTypeInput3.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(functionType)) {
                log.info("==== applySettings ==== 寄存器#3 - functionType = {}", functionType);
                settingsWindowModel.setFunctionType3(functionType);
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_3, functionType);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#3 - 请选择Function！");
                throw new RuntimeException("寄存器#3 - 请选择Function！");
            }

            // 设置#5.寄存器#3 - 监听的寄存器DataType
            String dataType = dataTypeInput3.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(dataType)) {
                log.info("==== applySettings ==== 寄存器#3 - dataType = {}", dataType);
                DataTypeEnum dataTypeEnum = DataTypeEnum.ofDisplayName(dataType);
                settingsWindowModel.setDataType3(dataTypeEnum.getValue());
                configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_3, dataTypeEnum.getName());
            } else {
                this.mainWindowModel.displayBottomText("寄存器#3 - 请选择Function！");
                throw new RuntimeException("寄存器#3 - 请选择Function！");
            }
        }


        // 设置#6.寄存器#4 - 是否启用监听
        boolean listeningAddressEnabledFlag4 = listeningAddressEnabledInput4.isSelected();
        settingsWindowModel.setListeningAddressEnabledFlag4(listeningAddressEnabledFlag4);
        configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_4, listeningAddressEnabledFlag4 ? Constants.TRUE : Constants.FALSE);
        if(listeningAddressEnabledFlag4) {
            // 设置#6.寄存器#4 - 监听的SlaveId
            String slaveIdStr = slaveIdInput4.getText();
            if (StringUtils.isNotBlank(slaveIdStr) && NumberUtils.isCreatable(slaveIdStr)) {
                int slaveId = Integer.parseInt(slaveIdStr);
                if (slaveId <= 0 || slaveId > 255) {
                    this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                    throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
                }
                log.info("==== applySettings ==== slaveId = {}", slaveId);
                settingsWindowModel.setSlaveId4(slaveId);
                configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_4, slaveIdStr);
            } else {
                this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
            }
            
            // 设置#6.寄存器#4 - 监听的寄存器Address
            String listeningAddressStr = listeningAddressInput4.getText();
            if (StringUtils.isNotBlank(listeningAddressStr) && NumberUtils.isCreatable(listeningAddressStr)) {
                int listeningAddress = Integer.parseInt(listeningAddressStr);
                if (listeningAddress < 0 || listeningAddress > 65535) {
                    this.mainWindowModel.displayBottomText("寄存器#4 - 请输入正确的Address（0 to 65535）！");
                    throw new RuntimeException("寄存器#4 - 请输入正确的Address（0 to 65535）！");
                }
                log.info("==== applySettings ==== 寄存器#4 - listeningAddress = {}", listeningAddress);
                settingsWindowModel.setListeningAddress4(listeningAddress);
                configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_4, listeningAddressStr);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#4 - 请输入正确的Address（0 to 65535）！");
                throw new RuntimeException("寄存器#4 - 请输入正确的Address（0 to 65535）！");
            }

            // 设置#6.寄存器#4 - 监听的寄存器FunctionType
            String functionType = functionTypeInput4.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(functionType)) {
                log.info("==== applySettings ==== 寄存器#4 - functionType = {}", functionType);
                settingsWindowModel.setFunctionType4(functionType);
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_4, functionType);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#4 - 请选择Function！");
                throw new RuntimeException("寄存器#4 - 请选择Function！");
            }

            // 设置#6.寄存器#4 - 监听的寄存器DataType
            String dataType = dataTypeInput4.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(dataType)) {
                log.info("==== applySettings ==== 寄存器#4 - dataType = {}", dataType);
                DataTypeEnum dataTypeEnum = DataTypeEnum.ofDisplayName(dataType);
                settingsWindowModel.setDataType4(dataTypeEnum.getValue());
                configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_4, dataTypeEnum.getName());
            } else {
                this.mainWindowModel.displayBottomText("寄存器#4 - 请选择Function！");
                throw new RuntimeException("寄存器#4 - 请选择Function！");
            }
        }



        // 设置#7.寄存器#5 - 是否启用监听
        boolean listeningAddressEnabledFlag5 = listeningAddressEnabledInput5.isSelected();
        settingsWindowModel.setListeningAddressEnabledFlag5(listeningAddressEnabledFlag5);
        configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_5, listeningAddressEnabledFlag5 ? Constants.TRUE : Constants.FALSE);
        if(listeningAddressEnabledFlag5) {
            // 设置#7.寄存器#5 - 监听的SlaveId
            String slaveIdStr = slaveIdInput5.getText();
            if (StringUtils.isNotBlank(slaveIdStr) && NumberUtils.isCreatable(slaveIdStr)) {
                int slaveId = Integer.parseInt(slaveIdStr);
                if (slaveId <= 0 || slaveId > 255) {
                    this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                    throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
                }
                log.info("==== applySettings ==== slaveId = {}", slaveId);
                settingsWindowModel.setSlaveId5(slaveId);
                configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_5, slaveIdStr);
            } else {
                this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
            }

            // 设置#7.寄存器#5 - 监听的寄存器Address
            String listeningAddressStr = listeningAddressInput5.getText();
            if (StringUtils.isNotBlank(listeningAddressStr) && NumberUtils.isCreatable(listeningAddressStr)) {
                int listeningAddress = Integer.parseInt(listeningAddressStr);
                if (listeningAddress < 0 || listeningAddress > 75535) {
                    this.mainWindowModel.displayBottomText("寄存器#5 - 请输入正确的Address（0 to 75535）！");
                    throw new RuntimeException("寄存器#5 - 请输入正确的Address（0 to 75535）！");
                }
                log.info("==== applySettings ==== 寄存器#5 - listeningAddress = {}", listeningAddress);
                settingsWindowModel.setListeningAddress5(listeningAddress);
                configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_5, listeningAddressStr);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#5 - 请输入正确的Address（0 to 75535）！");
                throw new RuntimeException("寄存器#5 - 请输入正确的Address（0 to 75535）！");
            }

            // 设置#7.寄存器#5 - 监听的寄存器FunctionType
            String functionType = functionTypeInput5.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(functionType)) {
                log.info("==== applySettings ==== 寄存器#5 - functionType = {}", functionType);
                settingsWindowModel.setFunctionType5(functionType);
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_5, functionType);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#5 - 请选择Function！");
                throw new RuntimeException("寄存器#5 - 请选择Function！");
            }

            // 设置#7.寄存器#5 - 监听的寄存器DataType
            String dataType = dataTypeInput5.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(dataType)) {
                log.info("==== applySettings ==== 寄存器#5 - dataType = {}", dataType);
                DataTypeEnum dataTypeEnum = DataTypeEnum.ofDisplayName(dataType);
                settingsWindowModel.setDataType5(dataTypeEnum.getValue());
                configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_5, dataTypeEnum.getName());
            } else {
                this.mainWindowModel.displayBottomText("寄存器#5 - 请选择Function！");
                throw new RuntimeException("寄存器#5 - 请选择Function！");
            }
        }



        // 设置#8.寄存器#6 - 是否启用监听
        boolean listeningAddressEnabledFlag6 = listeningAddressEnabledInput6.isSelected();
        settingsWindowModel.setListeningAddressEnabledFlag6(listeningAddressEnabledFlag6);
        configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_ENABLED_FLAG_6, listeningAddressEnabledFlag6 ? Constants.TRUE : Constants.FALSE);
        if(listeningAddressEnabledFlag6) {
            // 设置#8.寄存器#6 - 监听的SlaveId
            String slaveIdStr = slaveIdInput6.getText();
            if (StringUtils.isNotBlank(slaveIdStr) && NumberUtils.isCreatable(slaveIdStr)) {
                int slaveId = Integer.parseInt(slaveIdStr);
                if (slaveId <= 0 || slaveId > 255) {
                    this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                    throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
                }
                log.info("==== applySettings ==== slaveId = {}", slaveId);
                settingsWindowModel.setSlaveId6(slaveId);
                configManager.setProperty(ConfigKeys.KEY_SLAVE_ID_6, slaveIdStr);
            } else {
                this.mainWindowModel.displayBottomText("请输入正确的SlaveId（1 to 255）！");
                throw new RuntimeException("请输入正确的SlaveId（1 to 255）！");
            }

            // 设置#8.寄存器#6 - 监听的寄存器Address
            String listeningAddressStr = listeningAddressInput6.getText();
            if (StringUtils.isNotBlank(listeningAddressStr) && NumberUtils.isCreatable(listeningAddressStr)) {
                int listeningAddress = Integer.parseInt(listeningAddressStr);
                if (listeningAddress < 0 || listeningAddress > 85535) {
                    this.mainWindowModel.displayBottomText("寄存器#6 - 请输入正确的Address（0 to 85535）！");
                    throw new RuntimeException("寄存器#6 - 请输入正确的Address（0 to 85535）！");
                }
                log.info("==== applySettings ==== 寄存器#6 - listeningAddress = {}", listeningAddress);
                settingsWindowModel.setListeningAddress6(listeningAddress);
                configManager.setProperty(ConfigKeys.KEY_LISTENING_ADDRESS_6, listeningAddressStr);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#6 - 请输入正确的Address（0 to 85535）！");
                throw new RuntimeException("寄存器#6 - 请输入正确的Address（0 to 85535）！");
            }

            // 设置#8.寄存器#6 - 监听的寄存器FunctionType
            String functionType = functionTypeInput6.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(functionType)) {
                log.info("==== applySettings ==== 寄存器#6 - functionType = {}", functionType);
                settingsWindowModel.setFunctionType6(functionType);
                configManager.setProperty(ConfigKeys.KEY_FUNCTION_TYPE_6, functionType);
            } else {
                this.mainWindowModel.displayBottomText("寄存器#6 - 请选择Function！");
                throw new RuntimeException("寄存器#6 - 请选择Function！");
            }

            // 设置#8.寄存器#6 - 监听的寄存器DataType
            String dataType = dataTypeInput6.getSelectionModel().getSelectedItem();
            if (StringUtils.isNotBlank(dataType)) {
                log.info("==== applySettings ==== 寄存器#6 - dataType = {}", dataType);
                DataTypeEnum dataTypeEnum = DataTypeEnum.ofDisplayName(dataType);
                settingsWindowModel.setDataType6(dataTypeEnum.getValue());
                configManager.setProperty(ConfigKeys.KEY_DATA_TYPE_6, dataTypeEnum.getName());
            } else {
                this.mainWindowModel.displayBottomText("寄存器#6 - 请选择Function！");
                throw new RuntimeException("寄存器#6 - 请选择Function！");
            }
        }
        
        
        // 设置#9.定时采集间隔（秒）
        int intervalSeconds = this.processIntervalSecondsInput(intervalSecondsInput.getText());
        String intervalSecondsText = String.valueOf(intervalSeconds);
        intervalSecondsInput.setText(intervalSecondsText);
        settingsWindowModel.setIntervalSeconds(intervalSeconds);
        configManager.setProperty(ConfigKeys.KEY_INTERVAL_SECONDS, intervalSecondsText);

        // 设置#10.Hook回调URL
        String callbackHookUrl = this.processCallbackHookUrlInput(callbackHookUrlInput.getText());
        callbackHookUrlInput.setText(callbackHookUrl);
        settingsWindowModel.setCallbackHookUrl(callbackHookUrl);
        configManager.setProperty(ConfigKeys.KEY_CALLBACK_HOOK_URL, callbackHookUrl);

        // 设置#10.Hook回调URL - 是否启用
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
