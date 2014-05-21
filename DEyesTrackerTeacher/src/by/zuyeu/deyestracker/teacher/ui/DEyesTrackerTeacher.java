/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.teacher.ui;

import by.zuyeu.deyestracker.teacher.model.ResultPacket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 *
 * @author Fieryphoenix
 */
public class DEyesTrackerTeacher extends Application {

    private static final String TEACH_PANEL = "TeachPanel.fxml";
    //packet to send to interraction app
    private final ResultPacket teachPacket = new ResultPacket();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource(TEACH_PANEL).openStream());
        TeachPanelController controller = fxmlLoader.<TeachPanelController>getController();
        controller.setTeachPacket(teachPacket);

        final Scene scene = new Scene(root);
        scene.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ESCAPE) {
                Stage sb = (Stage) scene.getWindow();
                sb.close();
            }
        });

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    public ResultPacket getTeachPacket() {
        return teachPacket;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
