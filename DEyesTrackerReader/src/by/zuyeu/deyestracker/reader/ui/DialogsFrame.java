/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.ui;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class DialogsFrame {

    private static final Logger LOG = LoggerFactory.getLogger(DialogsFrame.class);
    private static final String DIALOG_CSS = "/by/zuyeu/deyestracker/reader/ui/css/modal-dialog.css";

    //TODO infer handler
    public static void showOKDialog(Stage stage, String text) {
        // initialize the confirmation dialog
        final Stage dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(stage);
        dialog.setScene(
                new Scene(
                        HBoxBuilder.create().styleClass("modal-dialog").children(
                                LabelBuilder.create().text(text).build(),
                                ButtonBuilder.create().text("Ok").defaultButton(true).onAction((ActionEvent actionEvent) -> {
                                    LOG.debug("OK");
                                    stage.getScene().getRoot().setEffect(null);
                                    dialog.close();
                                }).build()
                        ).build(), Color.TRANSPARENT
                )
        );
        dialog.getScene().getStylesheets().add(DialogsFrame.class.getResource(DIALOG_CSS).toExternalForm());

        // show the confirmation dialog each time a new page is loaded.
        stage.getScene().getRoot().setEffect(new BoxBlur());
        dialog.show();
    }
}
