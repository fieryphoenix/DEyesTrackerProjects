/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.ui.signin;

import by.zuyeu.deyestracker.reader.model.User;
import by.zuyeu.deyestracker.reader.repository.UserDAO;
import by.zuyeu.deyestracker.reader.ui.AppController;
import by.zuyeu.deyestracker.reader.ui.DialogsFrame;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Fieryphoenix
 */
public class SignInFormController extends AppController {

    private static final Logger LOG = LoggerFactory.getLogger(SignInFormController.class);
    private static final String ERROR_NO_USER_KEY = "page.login.errors.no_user";
    private static final String ERROR_EMPTY_FIELD_KEY = "page.login.errors.empty_fields";

    @FXML
    private TextField fLogin;
    @FXML
    private TextField fPassword;

    private ResourceBundle bundle;

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

    @FXML
    public void handleSignIn(ActionEvent event) {
        LOG.debug("handleSignIn() - start: event = {}", event);
        if (isInputValid()) {
            processLogin();
        } else {
            DialogsFrame.showOKDialog(application.getStage(), bundle.getString(ERROR_EMPTY_FIELD_KEY));
        }
        LOG.debug("handleSignIn() - end;");
    }

    private void processLogin() {
        final UserDAO userDAO = factory.getUserDAO();
        if (userDAO.checkUser(fLogin.getText(), fPassword.getText())) {
            application.getSession().put("user", userDAO.findUser(fLogin.getText(), fPassword.getText()));
            application.openReaderPane();
        } else {
            LOG.debug("errors");
            DialogsFrame.showOKDialog(application.getStage(), bundle.getString(ERROR_NO_USER_KEY));
        }
    }

    @FXML
    public void handleStartWithTeaching(ActionEvent event) {
        LOG.debug("handleStartWithTeaching() - start;");
        if (isInputValid()) {
            processTeaching();
        } else {
            DialogsFrame.showOKDialog(application.getStage(), bundle.getString(ERROR_EMPTY_FIELD_KEY));
        }
        LOG.debug("handleStartWithTeaching() - end;");
    }

    private void processTeaching() {
        if (factory.getUserDAO().checkUser(fLogin.getText(), fPassword.getText())) {
            application.openTeaching(factory.getUserDAO().findUser(fLogin.getText(), fPassword.getText()));
        } else {
            application.openTeaching(new User(fLogin.getText(), fPassword.getText()));
        }
    }

    private boolean isInputValid() {
        boolean result = true;
        if (StringUtils.isBlank(fLogin.getText())) {
            result = false;
        }
        if (StringUtils.isBlank(fPassword.getText())) {
            result = false;
        }
        return result;
    }

}
