package com.patzn.paas.cutc.constants;

import com.patzn.paas.cutc.utils.date.DateFormat;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Constants {

    public static final String EMPTY_STR = "";

    public static final String TRUE = "true";
    public static final String FALSE = "false";

    // 默认时间间隔：10s
    public static final int DEFAULT_INTERVAL_SECONDS = 5;
    public static final int MIN_INTERVAL_SECONDS = 1;
    public static final int MAX_INTERVAL_SECONDS = 300;

    public static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern(DateFormat.YYYY_MM_DD_HH_MM_SS);

    public static final String DEFAULT_OUTPUT_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "Screenshots";
    public static final String IMAGE_FORMAT = "png";
    public static final String IMAGE_FORMAT_WITH_DOT = ".png";

    public static final String DEFAULT_CALLBACK_HOOK_URI = "/serial-tool/callback";

    public static final String MAIN_WINDOW_TITLE = "CUTC串口工具 v0.0.1";
    public static final String SETTINGS_WINDOW_TITLE = "设置";


    /*
     * Logo图片
     */
    public static final String LOGO_IMAGE_PATH = "/icons/logo/Logo.png";

    /*
     * 窗口控制按钮图片
     */
    public static final String CLOSE_IMAGE_PATH = "/icons/window/Close.png";
    public static final String MAXIMIZE_IMAGE_PATH = "/icons/window/Maximize.png";
    public static final String MINIMIZE_IMAGE_PATH = "/icons/window/Minimize.png";
    public static final String RESIZE_IMAGE_PATH = "/icons/window/Resize.png";

    /*
     * 菜单功能按钮图片
     */
    public static final String RELOAD_IMAGE_PATH = "/icons/menu/Reload.png";
    public static final String SETTINGS_IMAGE_PATH = "/icons/menu/Settings.png";
    public static final String DRAG_IMAGE_PATH = "/icons/menu/Drag.png";
    public static final String HISTORY_DATA_IMAGE_PATH = "/icons/menu/HistoryData.png";


    /*
     * 菜单功能按钮图片 - 开始
     */
    public static final String RUN_BLUE_IMAGE_PATH = "/icons/menu/run/Run_Blue.png";
    public static final String RUN_GREY_IMAGE_PATH = "/icons/menu/run/Run_Grey.png";
    public static final String RUN_RED_IMAGE_PATH = "/icons/menu/run/Run_Red.png";
    public static final String RUN_WHITE_IMAGE_PATH = "/icons/menu/run/Run_White.png";

    /*
     * 菜单功能按钮图片 - 暂停
     */
    public static final String PAUSE_BLUE_IMAGE_PATH = "/icons/menu/pause/Pause_Blue.png";
    public static final String PAUSE_GREY_IMAGE_PATH = "/icons/menu/pause/Pause_Grey.png";
    public static final String PAUSE_RED_IMAGE_PATH = "/icons/menu/pause/Pause_Red.png";
    public static final String PAUSE_WHITE_IMAGE_PATH = "/icons/menu/pause/Pause_White.png";

    /*
     * 菜单功能按钮图片 - 结束
     */
    public static final String STOP_BLUE_IMAGE_PATH = "/icons/menu/stop/Stop_Blue.png";
    public static final String STOP_GREY_IMAGE_PATH = "/icons/menu/stop/Stop_Grey.png";
    public static final String STOP_RED_IMAGE_PATH = "/icons/menu/stop/Stop_Red.png";
    public static final String STOP_WHITE_IMAGE_PATH = "/icons/menu/stop/Stop_White.png";



    /*
     * 主界面图片 - 右侧数据列表区域
     */
    public static final String SEARCH_IMAGE_PATH = "/icons/main/Search.png";
    public static final String DELETE_IMAGE_PATH = "/icons/main/Delete.png";

    /*
     * 设置窗口图片 - 应用设置按钮
     */
    public static final String APPLY_IMAGE_PATH = "/icons/settings/Apply.png";

    public static final List<String> PORT_NAME_LIST = new LinkedList<>();
    public static final List<Integer> BUAD_RATE_LIST = new LinkedList<>();
    public static final List<String> MODBUS_FUNCTION_LIST = new LinkedList<>();
    public static final String DEFAULT_PORT_NAME = "COM1";
    public static final int DEFAULT_BAUD_RATE = 9600;
    public static final int DEFAULT_SLAVE_ID = 1;
    public static final int DEFAULT_LISTENING_ADDRESS = 1;

    public static final String MODBUS_FUNCTION_01 = "01 Coil Status (0x)";
    public static final String MODBUS_FUNCTION_02 = "02 Input Status (1x)";
    public static final String MODBUS_FUNCTION_03 = "03 Holding Register (4x)";
    public static final String MODBUS_FUNCTION_04 = "04 Input Registers (3x)";

    static {
        for (int i=1; i<=254; i++) {
            PORT_NAME_LIST.add("COM" + i);
        }

        // 波特率候选列表
        BUAD_RATE_LIST.add(110);
        BUAD_RATE_LIST.add(300);
        BUAD_RATE_LIST.add(600);
        BUAD_RATE_LIST.add(1200);
        BUAD_RATE_LIST.add(2400);
        BUAD_RATE_LIST.add(4800);
        BUAD_RATE_LIST.add(9600);
        BUAD_RATE_LIST.add(14400);
        BUAD_RATE_LIST.add(19200);
        BUAD_RATE_LIST.add(38400);
        BUAD_RATE_LIST.add(56000);
        BUAD_RATE_LIST.add(57600);
        BUAD_RATE_LIST.add(115200);
        BUAD_RATE_LIST.add(128000);
        BUAD_RATE_LIST.add(230400);
        BUAD_RATE_LIST.add(256000);
        BUAD_RATE_LIST.add(460800);
        BUAD_RATE_LIST.add(500000);
        BUAD_RATE_LIST.add(512000);
        BUAD_RATE_LIST.add(600000);
        BUAD_RATE_LIST.add(750000);
        BUAD_RATE_LIST.add(921600);
        BUAD_RATE_LIST.add(1000000);
        BUAD_RATE_LIST.add(1500000);
        BUAD_RATE_LIST.add(2000000);

        // 功能列表
        MODBUS_FUNCTION_LIST.add(MODBUS_FUNCTION_01);
        MODBUS_FUNCTION_LIST.add(MODBUS_FUNCTION_02);
        MODBUS_FUNCTION_LIST.add(MODBUS_FUNCTION_03);
        MODBUS_FUNCTION_LIST.add(MODBUS_FUNCTION_04);
    }
}
