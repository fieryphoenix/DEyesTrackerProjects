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
        DAOFactory factory = null;
        switch (type) {
            case JAXB:
                factory = FACTORIES.getOrDefault(type, new JaxbDAOFactory());
                break;
            case MEMORY:
                factory = FACTORIES.getOrDefault(type, new MemoryDAOFactory());
                break;
            default:
                throw new UnsupportedOperationException("no such factory");
        }
        return factory;
    }
}
