/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.eda.router;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Fieryphoenix
 */
public class RouterFactory {

    public static enum RouterType {

        EVENT;
    }
    private static final Map<RouterType, IRouter> routers = new ConcurrentHashMap<>();

    private RouterFactory() {

    }

    public static IRouter getRouter(RouterType type) {

        switch (type) {
            case EVENT:
                routers.putIfAbsent(type, new EventRouter());
                break;
            default:
                throw new UnsupportedOperationException("No such event routers");
        }
        return routers.get(type);
    }
}
