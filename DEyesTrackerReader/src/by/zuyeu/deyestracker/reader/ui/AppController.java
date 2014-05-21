/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.ui;

import by.zuyeu.deyestracker.reader.repository.DAOFactory;
import javafx.fxml.Initializable;

/**
 *
 * @author Fieryphoenix
 */
public abstract class AppController implements Initializable {

    protected DAOFactory factory;
    protected DEyesTrackerReader application;

    public void setFactory(DAOFactory factory) {
        this.factory = factory;
    }

    public DAOFactory getFactory() {
        return factory;
    }

    public DEyesTrackerReader getApplication() {
        return application;
    }

    public void setApplication(DEyesTrackerReader application) {
        this.application = application;
    }
}
