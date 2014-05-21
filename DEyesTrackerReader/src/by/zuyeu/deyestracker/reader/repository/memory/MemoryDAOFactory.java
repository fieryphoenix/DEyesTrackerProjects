/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository.memory;

import by.zuyeu.deyestracker.reader.repository.DAOFactory;
import by.zuyeu.deyestracker.reader.repository.TeachResultDAO;
import by.zuyeu.deyestracker.reader.repository.UserDAO;

/**
 *
 * @author Fieryphoenix
 */
public class MemoryDAOFactory extends DAOFactory {

    private UserDAO userDAO;
    private TeachResultDAO teachResultDAO;

    @Override
    public synchronized UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MemoryUserDAO();
        }
        return userDAO;
    }

    @Override
    public synchronized TeachResultDAO getTeachResultDAO() {
        if (teachResultDAO == null) {
            teachResultDAO = new MemoryTechResultDAO();
        }
        return teachResultDAO;
    }

}
