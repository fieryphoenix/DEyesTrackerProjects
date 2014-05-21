/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.ui;

import by.zuyeu.deyestracker.core.eda.router.IRouter;
import by.zuyeu.deyestracker.core.eda.router.RouterFactory;
import by.zuyeu.deyestracker.reader.handler.RegisterAndTeachingHandler;
import by.zuyeu.deyestracker.reader.model.User;
import by.zuyeu.deyestracker.reader.repository.DAOFactory;
import by.zuyeu.deyestracker.teacher.model.AppEvent;
import by.zuyeu.deyestracker.teacher.ui.DEyesTrackerTeacher;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class DEyesTrackerReader extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(DEyesTrackerReader.class);

    private static final String TEST_PAGE = "signin/ReadPane.fxml";

    private static final String START_PAGE = "signin/SignInForm.fxml";
    private static final String READER_PAGE = "readpane/ReadPane.fxml";
    private static final String BUNDLE = "by.zuyeu.deyestracker.reader.ui.bundle.messages";
    private static final String COMMON_CSS = "/by/zuyeu/deyestracker/reader/ui/css/Common.css";

    private Stage stage;
    private DAOFactory factory;
    private final Locale locale = Locale.ENGLISH;
    private final IRouter router = RouterFactory.getRouter(RouterFactory.RouterType.EVENT);
    private RegisterAndTeachingHandler registerAndTeachingHandler;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        factory = DAOFactory.getFactory(DAOFactory.FactoryType.MEMORY);

        final StackPane root = new StackPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        changeView(TEST_PAGE, locale);
        stage.show();
    }

    public void changeView(String fxml, Locale locale) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setResources(ResourceBundle.getBundle(BUNDLE, locale));
            Pane pane = (AnchorPane) fxmlLoader.load(this.getClass().getResource(fxml).openStream());
            final AppController controller = fxmlLoader.<AppController>getController();
            controller.setFactory(factory);
            controller.setApplication(this);
            // replace the content
            StackPane content = (StackPane) stage.getScene().getRoot();
            content.getChildren().clear();
            content.getChildren().add(pane);
            stage.getScene().getStylesheets().add(getClass().getResource(COMMON_CSS).toExternalForm());
        } catch (IOException ex) {
            LOG.error("changeView", ex);
        }
    }

    public void openLogin() {
        LOG.info("openLogin() - start;");
        changeView(START_PAGE, locale);
        stage.setFullScreen(false);
        stage.setResizable(false);
        LOG.info("openLogin() - end;");
    }

    public void openReaderPane() {
        LOG.info("openReaderPane() - start;");
        // delete handler
        router.deleteHandler(AppEvent.class, registerAndTeachingHandler);
        changeView(READER_PAGE, locale);
        stage.setFullScreen(true);
        stage.setResizable(false);
        LOG.info("openReaderPane() - end;");
    }

    public void openTeaching(User student) {
        final DEyesTrackerTeacher dEyesTrackerTeacher = new DEyesTrackerTeacher();
        registerAndTeachingHandler = new RegisterAndTeachingHandler(this, student, factory);
        router.registerHandler(AppEvent.class, registerAndTeachingHandler);
        Platform.runLater(() -> {
            try {
                dEyesTrackerTeacher.start(new Stage());
            } catch (Exception ex) {
                LOG.error("openTeaching");
                DialogsFrame.showOKDialog(stage, ex.getMessage());
            }
        });
    }

    public Stage getStage() {
        return stage;
    }

    public DAOFactory getFactory() {
        return factory;
    }

    public Locale getLocale() {
        return locale;
    }

    public IRouter getRouter() {
        return router;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
