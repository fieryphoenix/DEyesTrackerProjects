/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.ui.readpane;

import by.zuyeu.deyestracker.reader.model.User;
import by.zuyeu.deyestracker.reader.ui.AppController;
import by.zuyeu.deyestracker.reader.ui.DialogsFrame;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Fieryphoenix
 */
public class ReadPaneController extends AppController {

    private static final Logger LOG = LoggerFactory.getLogger(ReadPaneController.class);

    private ResourceBundle bundle;
    @FXML
    private TextFlow textFlow;
    @FXML
    private ScrollPane spText;
    private boolean scrollExist;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle = rb;
        scrollExist = false;
    }

    /**
     * Close application
     *
     * @param event
     */
    @FXML
    public void closeButtonAction(ActionEvent event) {
        LOG.info("closeButtonAction() - start;");
        Stage stage = application.getStage();
        stage.close();
        LOG.info("closeButtonAction() - end;");
    }

    /**
     * Go to login page
     *
     * @param event
     */
    @FXML
    public void logoutButtonAction(ActionEvent event) {
        LOG.info("logoutButtonAction() - start;");
        application.openLogin();
        LOG.info("logoutButtonAction() - end;");
    }

    /**
     * Start teaching procedure
     *
     * @param event
     */
    @FXML
    public void teachingButtonAction(ActionEvent event) {
        LOG.info("logoutButtonAction() - start;");
        application.openTeaching((User) application.getSession().get("user"));
        LOG.info("logoutButtonAction() - end;");
    }

    public void openFileButtonAction(ActionEvent event) {
        LOG.info("openFileButtonAction() - start;");
        addScrollTracker();
        FileChooser fileChooser = new FileChooser();
        //TODO extract text to bundle
        fileChooser.setTitle("Open Text File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(application.getStage());
        if (selectedFile != null) {
            try {
                final String text = FileUtils.readFileToString(selectedFile);
                textFlow.getChildren().clear();
                textFlow.getChildren().add(new Text(text));
            } catch (IOException ex) {
                LOG.warn("openFileButtonAction", ex);
                DialogsFrame.showOKDialog(application.getStage(), bundle.getString(ERROR_READ_FILE));
            }
        }
        LOG.info("openFileButtonAction() - end;");
    }
    private static final String ERROR_READ_FILE = "page.reader.errors.fileread";

    private void addScrollTracker() {
        LOG.info("addScrollTracker() - start;");
        if (!scrollExist) {
            application.getStage().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                if (evt.getCode().equals(KeyCode.DOWN)) {
                    Platform.runLater(() -> {
                        LOG.debug("down - vvalue = {}", spText.getVvalue());
                        spText.setVvalue(spText.getVvalue() + spText.getVmax() / 10);
                    }
                    );
                }
                if (evt.getCode().equals(KeyCode.UP)) {
                    Platform.runLater(() -> {
                        LOG.debug("up - vvalue = {}", spText.getVvalue());
                        spText.setVvalue(spText.getVvalue() - spText.getVmax() / 10);
                    }
                    );
                }
            });
            scrollExist = true;
        }
        LOG.info("addScrollTracker() - end;");
    }
}
