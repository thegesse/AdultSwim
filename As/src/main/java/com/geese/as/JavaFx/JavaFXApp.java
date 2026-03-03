package com.geese.as.JavaFx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        System.out.println("JavaFX Toolkit initialized");

        AsciiAnimationService.notifyToolkitStarted();

    }
}