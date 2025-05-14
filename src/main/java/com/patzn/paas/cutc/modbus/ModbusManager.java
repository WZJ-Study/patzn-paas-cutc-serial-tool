package com.patzn.paas.cutc.modbus;

import com.patzn.paas.cutc.component.TaskExecutor;
import com.patzn.paas.cutc.config.ServerConfig;
import com.patzn.paas.cutc.constants.Constants;
import com.patzn.paas.cutc.modbus.connection.ModbusConnection;
import com.patzn.paas.cutc.modbus.task.CallbackHookTask;
import com.patzn.paas.cutc.modbus.task.CollectTask;
import com.patzn.paas.cutc.server.entity.CollectedValue;
import com.patzn.paas.cutc.ui.model.MainWindowModel;
import com.patzn.paas.cutc.ui.model.SettingsWindowModel;
import javafx.application.Platform;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class ModbusManager {

    /**
     * 设置弹窗区域模型
     */
    private final SettingsWindowModel settingsWindowModel;

    /**
     * 主窗口模型
     */
    private final MainWindowModel mainWindowModel;

    /**
     * 服务器配置
     */
    private final ServerConfig serverConfig;

    /**
     * 定时任务线程Future
     */
    private ScheduledFuture<?> scheduledFuture;

    /**
     * 定时任务线程Future
     */
    private ScheduledFuture<?> countDownFuture;

    /**
     * 定时采集时间间隔
     */
    private volatile int intervalSeconds;

    /**
     * 倒计时显示的秒数
     */
    private final AtomicInteger countDownSeconds;

    /**
     * Modbus连接
     */
    private ModbusConnection connection;

    /**
     * 运行标志
     */
    private volatile boolean running;


    public ModbusManager(MainWindowModel mainWindowModel, SettingsWindowModel settingsWindowModel, ServerConfig serverConfig) {
        this.mainWindowModel = mainWindowModel;
        this.settingsWindowModel = settingsWindowModel;
        this.serverConfig = serverConfig;
        // 默认时间间隔：5s
        this.intervalSeconds = Constants.DEFAULT_INTERVAL_SECONDS;
        this.countDownSeconds = new AtomicInteger(0);
        // 设置运行标志=已停止
        this.running = false;
    }

    public ModbusConnection getConnection() {
        // 建立连接
        boolean connected = tryConnect(true);
        if (!connected) {
            // 连接建立失败，跳过！
            return null;
        }
        return connection;
    }

    public boolean tryConnect(boolean forceReconnect) {
        if (StringUtils.isBlank(settingsWindowModel.getPortName())) {
            this.displayBottomText("未指定端口号！");
            throw new RuntimeException("未指定端口号！");
        }
        try {
            if (null != connection) {
                // 已存在的连接，重置端口号和波特率
                connection.reset(settingsWindowModel.getPortName(), settingsWindowModel.getBaudRate());
                if (!connection.isInitialized()) {
                    // 若断开，则重新连接
                    connection.connect();
                } else if (forceReconnect) {
                    // 强制断开连接，然后重新连接
                    connection.tryDisconnect();
                    connection.connect();
                }
            } else {
                // 连接不存在，创建新连接
                connection = new ModbusConnection(settingsWindowModel.getPortName(), settingsWindowModel.getBaudRate());
                connection.connect();
            }
            return true;
        } catch (Exception e) {
            log.error("==== tryConnect ==== 连接失败！", e);
            this.displayBottomText("连接失败！" + e.getMessage());
        }
        return false;
    }

    public void disconnect() {
        if (null != connection) {
            try {
                connection.disconnect();
            } catch (Exception e) {
                log.error("==== disconnect ==== 断开连接失败！", e);
                this.displayBottomText("断开连接失败！" + e.getMessage());
            }
        }
    }

    public void start() {
        if (this.running) {
            return;
        }

        // 建立连接
        boolean connected = tryConnect(true);
        if (!connected) {
            // 连接建立失败，跳过！
            return;
        }

        // 开始定时采集
        CollectTask collectTask = new CollectTask(this, settingsWindowModel);
        this.scheduledFuture = TaskExecutor.scheduleWithFixedDelay(collectTask, 0, intervalSeconds, TimeUnit.SECONDS);

        // 开始倒计时
        this.countDownSeconds.set(this.intervalSeconds);
        this.countDownFuture = TaskExecutor.scheduleAtFixedRate(() -> {
            int cdSec = this.countDownSeconds.getAndDecrement();
            this.countDownSeconds.compareAndSet(0, this.intervalSeconds);
            String cdSecText = String.format("%02d:%02d", cdSec / 60, cdSec % 60);
            // 确保UI更新在JavaFX线程中执行
            Platform.runLater(() -> {
                this.mainWindowModel.setCollectCountDownText(cdSecText);
            });
        }, 0, 1, TimeUnit.SECONDS);

        // 设置运行标志=运行中
        this.running = true;
    }


    /**
     * 结束运行
     */
    public synchronized void stop() {
        // 停止截屏定时任务
        if (null != this.scheduledFuture) {
            if (!this.scheduledFuture.isCancelled() || !this.scheduledFuture.isDone()) {
                this.scheduledFuture.cancel(true);
            }
        }
        // 结束倒计时
        if (null != this.countDownFuture) {
            if (!this.countDownFuture.isCancelled() || !this.countDownFuture.isDone()) {
                this.countDownFuture.cancel(true);
            }
        }
        // 设置运行标志=已停止
        this.running = false;

        // 断开连接
        disconnect();

        // 确保UI更新在JavaFX线程中执行
        Platform.runLater(() -> {
            mainWindowModel.setCollectCountDownText("已停止");
        });
    }

    public synchronized void setIntervalSeconds(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public void displayBottomText(String text) {
        mainWindowModel.displayBottomText(text);
    }

    public CallbackHookTask createCallbackHookTask(CollectedValue result) {
        if (null == result) {
            return null;
        }
        return new CallbackHookTask(result, this.settingsWindowModel, this.serverConfig);
    }

}
