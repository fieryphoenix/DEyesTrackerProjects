/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.handler;

import by.zuyeu.deyestracker.core.detection.model.StudyResult;
import by.zuyeu.deyestracker.core.eda.event.handler.DEyesTrackerHandler;
import by.zuyeu.deyestracker.reader.model.StudyResult2;
import by.zuyeu.deyestracker.reader.model.User;
import by.zuyeu.deyestracker.reader.repository.DAOFactory;
import by.zuyeu.deyestracker.reader.ui.DEyesTrackerReader;
import by.zuyeu.deyestracker.reader.ui.DialogsFrame;
import by.zuyeu.deyestracker.reader.util.ObjectTransformer;
import by.zuyeu.deyestracker.teacher.model.AppEvent;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class RegisterAndTeachingHandler implements DEyesTrackerHandler<AppEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterAndTeachingHandler.class);
    private final User student;
    private final DAOFactory factory;
    private final DEyesTrackerReader application;

    public RegisterAndTeachingHandler(DEyesTrackerReader application, User student, DAOFactory factory) {
        this.application = application;
        this.factory = factory;
        this.student = student;
    }

    @Override
    public void handle(AppEvent event) {
        switch (event.getAction()) {
            case START:
                handleStartAction(event.getPacket());
                break;
            case END:
                handleEndAction(event.getPacket());
                break;
            case ERROR:
                handleErrorAction(event.getPacket());
                break;
            default:
                throw new UnsupportedOperationException("No action handler");
        }
    }

    private void handleStartAction(Object packet) {
        LOG.info("handleStartAction() - packet = {}", packet);
    }

    private void handleErrorAction(Object packet) {
        LOG.info("handleErrorAction() - start: packet = {}", packet);
        Platform.runLater(()
                -> DialogsFrame.showOKDialog(application.getStage(), "Error to run teaching process"));
        LOG.info("handleErrorAction() - end;");
    }

    private void handleEndAction(Object packet) {
        LOG.info("handleEndAction() - start: packet = {}", packet);
        if (packet != null) {
            StudyResult studyResult = (StudyResult) packet;
            StudyResult2 studyResultToSave = ObjectTransformer.transformStudyResultToSerializableType(studyResult);
            //save results
            factory.getUserDAO().saveUser(student);
            factory.getTeachResultDAO().saveTechingResult(student, studyResultToSave);

            //close teacher app and open reader pane
            Platform.runLater(()
                    -> application.openReaderPane());
        }
        LOG.info("handleEndAction() - end;");
    }

}
