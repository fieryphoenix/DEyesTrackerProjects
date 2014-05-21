/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
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

    private static final String START_PAGE = "signin/SignInForm.fxml";
    private static final String BUNDLE = "by.zuyeu.deyestracker.reader.ui.bundle.messages";
    private static final String COMMON_CSS = "/by/zuyeu/deyestracker/reader/ui/css/Common.css";

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        final StackPane root = new StackPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        changeView(Locale.ENGLISH);
        stage.show();
    }

    public void changeView(Locale locale) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setResources(ResourceBundle.getBundle(BUNDLE, locale));
            Pane pane = (AnchorPane) fxmlLoader.load(this.getClass().getResource(START_PAGE).openStream());
            // replace the content
            StackPane content = (StackPane) stage.getScene().getRoot();
            content.getChildren().clear();
            content.getChildren().add(pane);
            stage.getScene().getStylesheets().add(COMMON_CSS);
        } catch (IOException ex) {
            LOG.error("changeView", ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
