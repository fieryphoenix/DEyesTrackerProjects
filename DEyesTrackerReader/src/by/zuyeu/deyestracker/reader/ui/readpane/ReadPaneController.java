/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.ui.readpane;

import by.zuyeu.deyestracker.reader.ui.AppController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle = rb;
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

}
