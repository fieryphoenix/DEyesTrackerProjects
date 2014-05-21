/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.teacher.handler;

import by.zuyeu.deyestracker.core.eda.event.QualityEvent;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class QualityHandler implements DEyesTrackerHandler<QualityEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(QualityHandler.class);
    private final Label label;

    public QualityHandler(Label l) {
        this.label = l;
    }

    @Override
    public void handle(QualityEvent event) {
        LOG.info("handle() - start: event = {}", event);
        switch (event.getQualityType()) {
            case BAD:
                showBadQulityStatus();
                break;
            case GOOD:
            case NORMAL:
                showGoodQulityStatus();
                break;
            default:
                throw new UnsupportedOperationException("handle type error");
        }
        LOG.info("handle() - end;");
    }

    private void showBadQulityStatus() {
        Platform.runLater(()
                -> {
                    label.setText("ERROR!");
                    label.setStyle("-fx-text-fill: rgb(255,0,0);");
                });
    }

    private void showGoodQulityStatus() {

        Platform.runLater(()
                -> {
                    label.setText("OK!");
                    label.setStyle("-fx-text-fill: rgb(32,191,0);");
                });
    }

}
