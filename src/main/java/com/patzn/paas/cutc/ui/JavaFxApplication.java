package com.patzn.paas.cutc.ui;


import com.patzn.paas.cutc.fxml.event.StageReadyEvent;
import com.patzn.paas.cutc.utils.spring.SpringHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class JavaFxApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        SpringHelper.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop(){
        SpringHelper.close();
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }

}
