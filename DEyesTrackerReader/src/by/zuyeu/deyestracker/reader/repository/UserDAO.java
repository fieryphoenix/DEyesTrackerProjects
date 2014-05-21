/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository;

import by.zuyeu.deyestracker.reader.model.User;

/**
 *
 * @author Fieryphoenix
 */
public interface UserDAO {

    boolean checkUser(String login, String password);

    void saveUser(User user);
}
