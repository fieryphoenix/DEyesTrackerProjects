/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker;

import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.scenario.TeachingScenario;
import by.zuyeu.deyestracker.scenario.WelcomeScenario;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Fieryphoenix
 */
public class TeachPanelController implements Initializable {

    @FXML
    private Circle cTL;
    @FXML
    private Circle cTR;
    @FXML
    private Circle cBL;
    @FXML
    private Circle cBR;
    @FXML
    private Label lText;

    private Property<String> scenarioText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scenarioText = new SimpleStringProperty();
        lText.textProperty().bind(scenarioText);
        new ScenarioController().start();
    }

    class ScenarioController extends Thread {

        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        @Override
        public void run() {
            try {
                runWelcomeScenario();
                final StudyResult tResult = runTeachingScenario();
                Platform.runLater(() -> {
                    scenarioText.setValue("Готово! Спасибо!");
                });
                System.out.println("RESULT HERE = " + tResult);
                Platform.runLater(() -> {
                    ((Stage) lText.getScene().getWindow()).close();
                });

            } catch (InterruptedException | ExecutionException e) {

            }
        }

        private void runWelcomeScenario() throws ExecutionException, InterruptedException {
            final WelcomeScenario welcomeScenario = new WelcomeScenario(scenarioText);
            executorService.execute(welcomeScenario);
            welcomeScenario.get();
        }

        private StudyResult runTeachingScenario() throws InterruptedException, ExecutionException {
            System.out.println("runTeachingScenario - start;");
            final TeachingScenario scenario = new TeachingScenario(cTL.visibleProperty(), cTR.visibleProperty(), cBL.visibleProperty(), cBR.visibleProperty());
            executorService.execute(scenario);
            final StudyResult result = scenario.get();
            System.out.println("runTeachingScenario - end: result = " + result);
            return result;
        }

    }
}
