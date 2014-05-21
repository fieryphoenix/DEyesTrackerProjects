/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository;

import by.zuyeu.deyestracker.reader.repository.jaxbdao.JaxbDAOFactory;
import by.zuyeu.deyestracker.reader.repository.memory.MemoryDAOFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Fieryphoenix
 */
public abstract class DAOFactory {

    public static enum FactoryType {

        MEMORY, JAXB;
    }

    private static final Map<FactoryType, DAOFactory> FACTORIES = new ConcurrentHashMap<>();

    public abstract UserDAO getUserDAO();

    public abstract TeachResultDAO getTeachResultDAO();

    public static final DAOFactory getFactory(FactoryType type) {
        switch (type) {
            case JAXB:
                FACTORIES.putIfAbsent(type, new JaxbDAOFactory());
                break;
            case MEMORY:
                FACTORIES.putIfAbsent(type, new MemoryDAOFactory());
                break;
            default:
                throw new UnsupportedOperationException("no such factory");
        }
        return FACTORIES.get(type);
    }
}
