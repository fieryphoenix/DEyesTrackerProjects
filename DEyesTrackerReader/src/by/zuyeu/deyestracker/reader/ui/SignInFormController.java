/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Fieryphoenix
 */
public class SignInFormController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(SignInFormController.class);

    private ResourceBundle bundle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle = rb;
    }

    @FXML
    private void handleSignIn(ActionEvent event) {
        LOG.debug("handleSignIn!");
    }

    @FXML
    private void handleStartWithTeaching(ActionEvent event) {
        LOG.debug("Teaching!");
    }
}
