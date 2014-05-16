/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.scenario;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.concurrent.Task;

/**
 *
 * @author Fieryphoenix
 */
public class WelcomeScenario extends Task<Void> {

    private final Property<String> scenarioText;

    public WelcomeScenario(Property<String> scenarioText) {
        this.scenarioText = scenarioText;
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("run welcome scenario - start;");
        Thread.sleep(3000);
        Platform.runLater(() -> {
            scenarioText.setValue("Привет! Это программа обучения системы слежения за взглядом.");
        });
        Thread.sleep(3000);
        Platform.runLater(() -> {
            scenarioText.setValue("Сейчас будут появляться индикаторы в углах экрана. Постарайся не двигаться и внимательно следить за ними.");
        });
        Thread.sleep(2000);
        Platform.runLater(() -> {
            scenarioText.setValue("Следи за индикаторами!");
        });
        System.out.println("run welcome scenario - end;");
        return null;
    }

}
