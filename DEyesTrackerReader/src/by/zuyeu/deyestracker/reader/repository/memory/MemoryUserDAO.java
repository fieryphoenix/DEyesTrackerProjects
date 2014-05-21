/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository.memory;

import by.zuyeu.deyestracker.reader.model.User;
import by.zuyeu.deyestracker.reader.repository.UserDAO;

/**
 *
 * @author Fieryphoenix
 */
public class MemoryUserDAO implements UserDAO {

    @Override
    public boolean checkUser(String login, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
