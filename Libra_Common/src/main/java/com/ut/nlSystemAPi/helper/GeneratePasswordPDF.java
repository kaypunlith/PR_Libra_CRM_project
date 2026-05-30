package com.ut.nlSystemAPi.helper;


import java.io.IOException;
import java.util.Random;

public class GeneratePasswordPDF {

    public static String generatePasswordPDF() throws IOException {
        String rechaptcha;

//        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//        String SALTCHARS = "1234567890!@#$%^&*()?<abcdefghiAZGC";
        String SALTCHARS = "abcdefghigklmopqrstu";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 20) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        rechaptcha = salt.toString();

        return rechaptcha;
    }
}
