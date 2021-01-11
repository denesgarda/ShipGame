package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Test extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            Integer.parseInt("a");
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unable to connect to server. Online functionalities will not work. If you want to use online functionalities, check your connection and try again.", ButtonType.OK);
            alert.setTitle("Network connection error");
            alert.setHeaderText("Network connection error");
            alert.showAndWait();
        }
        System.out.println("b");
    }
}