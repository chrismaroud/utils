package com.bitsfromspace.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author chris
 * @since 01/10/2016.
 */
public class LogUtils {

    private final static Map<Class<?>, Logger> CLASS_TO_LOGGER= new HashMap<>();


    public static void error(Class<?> referenceClass, String msg, Throwable t){
        final Logger logger;
        synchronized (CLASS_TO_LOGGER) {
            logger = CLASS_TO_LOGGER.computeIfAbsent(referenceClass, aClass -> Logger.getLogger(aClass.getName()));
        }
        logger.log(Level.SEVERE, msg, t);
    }
}
