/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.handler;

import by.zuyeu.deyestracker.core.eda.event.MoveEvent;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import org.opencv.core.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class ScrollMoveHandler implements DEyesTrackerHandler<MoveEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(ScrollMoveHandler.class);
    private final ScrollPane sp;

    public ScrollMoveHandler(ScrollPane sp) {
        this.sp = sp;
    }

    @Override
    public void handle(MoveEvent event) {
        boolean makeOneMove = false;
        //TODO update logic
        final Point newPosition = event.getNewPosition();
        final Point oldPosition = event.getOldPosition();//TODO add move ration by this param
        final Point center = event.getViewCenter();
        if (newPosition.x > center.x) {
            LOG.trace("decide to right");
            //makeOneMove = true;
            //moveRight();
        }
        if (!makeOneMove && (newPosition.x < center.x)) {
            LOG.trace("decide to left");
            //makeOneMove = true;
            //moveLeft();
        }
        if (!makeOneMove && (newPosition.y > center.y)) {
            LOG.trace("decide to down");
            makeOneMove = true;
            moveDown();
        }
        if (!makeOneMove && (newPosition.y < center.y)) {
            LOG.trace("decide to up");
            moveUp();
        }
    }

    private void moveDown() {
        Platform.runLater(() -> {
            LOG.debug("down - vvalue = {}", sp.getVvalue());
            sp.setVvalue(sp.getVvalue() + sp.getVmax() / 10);
        }
        );
    }

    private void moveUp() {
        Platform.runLater(() -> {
            LOG.debug("up - vvalue = {}", sp.getVvalue());
            sp.setVvalue(sp.getVvalue() - sp.getVmax() / 10);
        }
        );
    }

    private void moveRight() {
        Platform.runLater(() -> {
            LOG.debug("right - vvalue = {}", sp.getVvalue());
            sp.setHvalue(sp.getHvalue() + sp.getHmax() / 10);
        }
        );
    }

    private void moveLeft() {
        Platform.runLater(() -> {
            LOG.debug("left - vvalue = {}", sp.getVvalue());
            sp.setHvalue(sp.getHvalue() - sp.getHmax() / 10);
        }
        );
    }

}
