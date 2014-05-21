/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.teacher.ui;

import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.core.eda.event.CoreEvent;
import by.zuyeu.deyestracker.core.eda.event.QualityEvent;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;
import by.zuyeu.deyestracker.core.eda.router.IRouter;
import by.zuyeu.deyestracker.core.eda.router.RouterFactory;
import by.zuyeu.deyestracker.teacher.handler.QualityHandler;
import by.zuyeu.deyestracker.teacher.model.AppEvent;
import by.zuyeu.deyestracker.teacher.model.ResultPacket;
import by.zuyeu.deyestracker.teacher.scenario.TeachingScenario;
import by.zuyeu.deyestracker.teacher.scenario.WelcomeScenario;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class TeachPanelController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(TeachPanelController.class);

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
    @FXML
    private Label lQuality;

    private Property<String> scenarioText;
    private ResultPacket teachPacket;
    private final IRouter router = RouterFactory.getRouter(RouterFactory.RouterType.EVENT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scenarioText = new SimpleStringProperty();
        lText.textProperty().bind(scenarioText);
        router.registerHandler(CoreEvent.class, (DEyesTrackerHandler<CoreEvent>) (CoreEvent event) -> {
            LOG.debug("handle() - event = {}", event);
            if (event.getAction() != CoreEvent.EventType.START_DETECTION || event.getAction() != CoreEvent.EventType.HOLD_DETECTION) {
                router.sendEvent(new AppEvent(AppEvent.Action.ERROR));
            }
        });
        router.registerHandler(QualityEvent.class, new QualityHandler(lQuality));
        final ScenarioController t = new ScenarioController();
        t.setDaemon(true);
        t.start();
    }

    public void setTeachPacket(ResultPacket teachPacket) {
        this.teachPacket = teachPacket;
    }

    class ScenarioController extends Thread {

        private final ExecutorService executorService = Executors.newSingleThreadExecutor();
        private final IRouter router = RouterFactory.getRouter(RouterFactory.RouterType.EVENT);

        @Override
        public void run() {
            try {
                router.sendEvent(new AppEvent(AppEvent.Action.START));
                runWelcomeScenario();
                final StudyResult tResult = runTeachingScenario();
                Platform.runLater(()
                        -> scenarioText.setValue("Готово! Спасибо!")
                );
                LOG.debug("RESULT HERE = " + tResult);
                final AppEvent endEvent = new AppEvent(AppEvent.Action.END);
                endEvent.setPacket(tResult);
                Thread.sleep(1000);
                Platform.runLater(()
                        -> ((Stage) lText.getScene().getWindow()).close());
                router.sendEvent(endEvent);

            } catch (InterruptedException | ExecutionException e) {
                Platform.runLater(()
                        -> ((Stage) lText.getScene().getWindow()).close());
                router.sendEvent(new AppEvent(AppEvent.Action.ERROR));
                LOG.error("Teach run", e);
            }
        }

        private void runWelcomeScenario() throws ExecutionException, InterruptedException {
            final WelcomeScenario welcomeScenario = new WelcomeScenario(scenarioText);
            executorService.execute(welcomeScenario);
            welcomeScenario.get();
        }

        private StudyResult runTeachingScenario() throws InterruptedException, ExecutionException {
            LOG.trace("runTeachingScenario - start;");
            final TeachingScenario scenario = new TeachingScenario(cTL.visibleProperty(), cTR.visibleProperty(), cBL.visibleProperty(), cBR.visibleProperty());
            executorService.execute(scenario);
            final StudyResult result = scenario.get();
            teachPacket.setStudyResult(result);
            teachPacket.setIsEmpty(false);
            LOG.trace("runTeachingScenario - end: result = " + result);
            return result;
        }

    }
}
