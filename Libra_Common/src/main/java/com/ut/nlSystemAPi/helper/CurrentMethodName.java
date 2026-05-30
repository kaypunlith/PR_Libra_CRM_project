package com.ut.nlSystemAPi.helper;

public class CurrentMethodName {

    public static String getCurrentMethodName() {
        // Get the current stack trace
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // The method name is the second element in the stack trace
        // (index 0 is getStackTrace, index 1 is getCurrentMethodName)
        return stackTrace[2].getMethodName();
    }

}
