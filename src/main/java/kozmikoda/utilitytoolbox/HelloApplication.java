package kozmikoda.utilitytoolbox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {

    Stage window;
    Scene mainScene;

    FXMLLoader fxml;

    @Override
    public void start(Stage stage) throws IOException {
        fxml = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        window = fxml.load();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        window.setX(screenBounds.getWidth()/3.5);
        window.setY(screenBounds.getHeight()/4);



        window.initStyle(StageStyle.TRANSPARENT);

        mainScene = window.getScene();
        mainScene.setFill(Color.TRANSPARENT);

        window.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

        window.setScene(mainScene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}