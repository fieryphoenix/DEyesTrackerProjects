/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.teacher.scenario;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class WelcomeScenario extends Task<Void> {

    private static final Logger LOG = LoggerFactory.getLogger(WelcomeScenario.class);

    private final Property<String> scenarioText;

    public WelcomeScenario(Property<String> scenarioText) {
        this.scenarioText = scenarioText;
    }

    @Override
    protected Void call() throws Exception {
        LOG.info("run welcome scenario - start;");
        delayedFXOut(6000, "Привет! Это программа обучения системы слежения за взглядом.");
        delayedFXOut(3000, "Сейчас будут появляться индикаторы в углах экрана. Постарайся не двигаться и внимательно следить за ними.");
        delayedFXOut(2000, "Следи за индикаторами!");
        LOG.info("run welcome scenario - end;");
        return null;
    }

    private void delayedFXOut(long millis, final String text) throws InterruptedException {
        LOG.debug("delayedFXOut() - start: millis = {}, text = {}", millis, text);
        Thread.sleep(millis);
        Platform.runLater(()
                -> scenarioText.setValue(text));
        LOG.info("delayedFXOut() - end;");
    }

}
