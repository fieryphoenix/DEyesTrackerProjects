/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository.jaxbdao;

import by.zuyeu.deyestracker.reader.model.User;
import by.zuyeu.deyestracker.reader.repository.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class JaxbUserDAO implements UserDAO {

    private static final Logger LOG = LoggerFactory.getLogger(JaxbUserDAO.class);

    @Override
    public boolean checkUser(String login, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
