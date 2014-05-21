/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository.memory;

import by.zuyeu.deyestracker.reader.model.User;
import by.zuyeu.deyestracker.reader.repository.UserDAO;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fieryphoenix
 */
public class MemoryUserDAO implements UserDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MemoryUserDAO.class);

    private static final List<User> users = new LinkedList<>();

    @Override
    public boolean checkUser(String login, String password) {
        LOG.info("checkUser() - start: login = {}, pass = {}", login, password);
        boolean exist = users.stream().anyMatch(u
                -> (u.getLogin().equalsIgnoreCase(login) && u.getPassword().equals(password)));
        LOG.info("checkUser() - end: exist = {}", exist);
        return exist;
    }

    @Override
    public void saveUser(User user) {
        LOG.info("saveUser() - start: user = {}", user);
        boolean added = false;
        if (user != null && !checkUser(user.getLogin(), user.getPassword())) {
            added = users.add(user);
        }
        LOG.info("saveUser() - end: added = {}", added);
    }

}
