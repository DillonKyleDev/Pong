package edu.srjc.finalproject.kyle.dillon.pong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PongApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PongApplication.class.getResource("PongView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 800);
        stage.setTitle("PONG");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}