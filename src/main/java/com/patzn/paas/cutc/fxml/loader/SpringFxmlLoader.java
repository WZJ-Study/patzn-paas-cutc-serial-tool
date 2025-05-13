package com.patzn.paas.cutc.fxml.loader;

import com.patzn.paas.cutc.fxml.FxmlViews;
import javafx.scene.Parent;
import javafx.stage.Stage;

public interface SpringFxmlLoader {

    Parent load(String path);

    void loadTo(FxmlViews view, Stage stage);

}
