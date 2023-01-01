package kozmikoda.utilitytoolbox;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloController {

    double offsetX, offsetY;
    private Stage stage;

    @FXML
    private Stage window;
    @FXML
    private Pane toolboxInfoPane, passwordSpaceInfoPane, soundAnalyzerInfoPane;
    @FXML
    private JFXButton passwordSpaceStartButtonLogo;
    @FXML
    private Label passwdIsAliveLabel, soundIsAliveLabel;


    // Drag window functions
    @FXML
    void dragWindow(MouseEvent event) {
        window.setX(event.getScreenX() - offsetX);
        window.setY(event.getScreenY() - offsetY);
    }

    @FXML
    void setWindowOffset(MouseEvent event) {
        offsetX = event.getSceneX();
        offsetY = event.getSceneY();
    }

    // close button
    @FXML
    void closeButton(ActionEvent event) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();

    }

    // minimize button
    @FXML
    void minimizeButton() {
        window.setIconified(true);
    }

    // chooser buttons
    @FXML
    void toolboxChooseButton() {
        toolboxInfoPane.setVisible(true);
        passwordSpaceInfoPane.setVisible(false);
        soundAnalyzerInfoPane.setVisible(false);
    }

    @FXML
    void passwordSpaceChooseButton() {
        toolboxInfoPane.setVisible(false);
        soundAnalyzerInfoPane.setVisible(false);
        passwordSpaceInfoPane.setVisible(true);
    }

    @FXML
    void soundAnalyzerChooseButton () {
        toolboxInfoPane.setVisible(false);
        passwordSpaceInfoPane.setVisible(false);
        soundAnalyzerInfoPane.setVisible(true);

    }

    // Application Start Buttons
    static Process passwordSpaceProcess;
    static boolean passwordSpaceFlag = true;
    @FXML
    void passwordSpaceStartButton() {
        passwordSpaceStartButtonLogo.setDisable(true);

        try {
            // Creating the process for running the bat file
            passwordSpaceProcess = Runtime.getRuntime().exec(new String[]{"C:/PROGRA~2/image/bin/PasswordSpace.bat"}, new String[]{"cmd"});
            //minimize the app
            //window.setIconified(true);

            // Check whether the program is already running or not
            // Flag is responsible for playing timeline just once
            if (passwordSpaceFlag) {
                Timeline passwdProcessHandler = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        passwordSpaceStartButtonLogo.setDisable(passwordSpaceProcess.isAlive());
                                        passwdIsAliveLabel.setVisible(passwordSpaceProcess.isAlive());


                                    }
                                }));

                passwdProcessHandler.setCycleCount(Timeline.INDEFINITE);
                passwdProcessHandler.play();
                passwordSpaceFlag = false;
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void soundAnalyzerStartButton() {

    }
}
