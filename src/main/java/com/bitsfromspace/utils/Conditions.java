package com.bitsfromspace.utils;

import java.util.function.Supplier;

/**
 * @author chris
 * @since 05/10/2016.
 */
public interface Conditions {

    static void notNull(Object o, String paramName){
        notNull(o, ()-> paramName + " is required.");
    }

    static void notNull(Object o, Supplier<String> errorMessage){
        if (o == null){
            throw new IllegalArgumentException(errorMessage.get());
        }
    }
}
