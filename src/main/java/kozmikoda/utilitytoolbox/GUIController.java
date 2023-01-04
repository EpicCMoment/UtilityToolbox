package kozmikoda.utilitytoolbox;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GUIController {

    double offsetX, offsetY;
    private Stage stage;

    @FXML
    private Stage window;
    @FXML
    private Pane toolboxInfoPane, passwordSpaceInfoPane, soundAnalyzerInfoPane, soundAnalyzerPane;
    @FXML
    private JFXButton passwordSpaceStartButtonLogo, soundAnalyzerStartButtonLogo, soundAnalyzerBackButton;
    @FXML
    private Label passwdIsAliveLabel, soundIsAliveLabel;

    @FXML
    private ProgressIndicator analyzeBar;
    @FXML
    private Label analyzeLabel;
    @FXML
    private TextField output;
    @FXML
    private JFXButton selectFileButton;
    @FXML
    private Scene mainLayout;
    @FXML
    private Pane titlebar;

    FileChooser fileChooser = new FileChooser();

    Path newDir;


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
            passwordSpaceProcess = Runtime.getRuntime().exec(new String[]{"C:/PROGRA~2/image1/bin/PasswordSpace.bat"}, new String[]{"cmd"});
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
        soundAnalyzerPane.setVisible(true);
    }


    @FXML
    protected void selectFileButton(ActionEvent event) {
        selectFileButton.setDisable(true);
        output.setVisible(false);
        output.setText(" ");
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // ADD INITIAL DIRECTORY TO EXAMPLE SOUND FILE DIRECTORY
        newDir = Paths.get(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(newDir.resolve("test_sounds").toFile());
        try {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Wav Files", "*.wav")
            );

            // save the file that is chose by the user
            File sound = fileChooser.showOpenDialog(stage);
            // Creating the sound analyzer object
            SoundAnalyzerStart analyzerObj = new SoundAnalyzerStart(sound, output, selectFileButton, analyzeBar, analyzeLabel, soundAnalyzerBackButton);

            // Calling the preprocessing function
            analyzerObj.prepareFile();
            // Starting the thread
            analyzerObj.start();

            analyzeBar.setVisible(true);
            analyzeLabel.setVisible(true);

            // Changing the visibilities of progress bar and analyzing label
            //analyzeBar.setVisible(true);
            //analyzeLabel.setVisible(true);

        } catch (NullPointerException e) {
            output.setVisible(true);
            output.setText("You did not select a file!");
            selectFileButton.setDisable(false);
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // output.setText(data);

    }

    @FXML
    void soundAnalyzerBackButtonAction() {
        soundAnalyzerPane.setVisible(false);
        output.setText(" ");
        output.setVisible(false);
    }
}
