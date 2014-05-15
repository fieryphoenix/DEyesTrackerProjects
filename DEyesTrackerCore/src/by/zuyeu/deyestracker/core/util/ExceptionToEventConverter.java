/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.util;

import by.zuyeu.deyestracker.core.event.CoreEvent;
import by.zuyeu.deyestracker.core.exception.DEyesTrackerExceptionCode;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fieryphoenix
 */
public class ExceptionToEventConverter {

    private static final Map<DEyesTrackerExceptionCode, CoreEvent.EventType> EXCEPTION_T0_EVENT_MAP = new HashMap<>();

    static {
        EXCEPTION_T0_EVENT_MAP.put(DEyesTrackerExceptionCode.NO_CAMERA, CoreEvent.EventType.NO_CAMERA);
//TODO continue mapping
    }

    private ExceptionToEventConverter() {
        throw new IllegalAccessError("Not contructable");
    }

    public static CoreEvent.EventType getEventFromException(DEyesTrackerExceptionCode exceptionCode) {
        return EXCEPTION_T0_EVENT_MAP.get(exceptionCode);
    }
}
