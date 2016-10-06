package com.bitsfromspace.utils;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.bitsfromspace.utils.LogUtils.error;

/**
 * @author chris
 * @since 28/09/2016.
 */
public interface ExceptionUtils {

    static <T> T unchecked(Callable<T> callable){
        try {
            return callable.call();
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    static void unchecked(Action action){
        try {
            action.invoke();
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    static void tryCatch(Runnable runnable, Consumer<Throwable> catchHandler) {
        try {
            runnable.run();
        } catch (Throwable t) {
            catchHandler.accept(t);
        }
    }

    static <T> T tryCatch(Callable<T> runnable, Function<Throwable, T> catchHandler) {
        try {
            return runnable.call();
        } catch (Throwable t) {
            return catchHandler.apply(t);
        }
    }

    static <T> T swallow(Callable<T> callable, Function<Exception, T> onError){
        try {
            return callable.call();
        } catch (Exception ex){
            return onError.apply(ex);
        }
    }
    static void tryLog(Class<?> referenceClass, Action action, Function<Exception, String> logMessageProvider){
        try {
            action.invoke();
        } catch (Exception ex){
            error(referenceClass, logMessageProvider.apply(ex), ex);
        }
    }

    interface Action{
        void invoke() throws Exception;
    }
}
