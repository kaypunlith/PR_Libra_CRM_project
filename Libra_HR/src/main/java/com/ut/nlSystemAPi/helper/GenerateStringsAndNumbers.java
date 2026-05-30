package com.ut.nlSystemAPi.helper;

import java.util.Random;

public class GenerateStringsAndNumbers {

  public static void main(String[] args) {
    // Create an array to store 10 strings and numbers
    Object[] randomValues = new Object[10];

    // Random generator
    Random random = new Random();

    for (int i = 0; i < 10; i++) {
      // Generate a random number
      int number = random.nextInt(100); // random number between 0 and 99

      // Generate a random string of length 5
      String randomString = generateRandomString(5);

      // Alternate storing number and string in the array
      if (i % 2 == 0) {
        randomValues[i] = number;
      } else {
        randomValues[i] = randomString;
      }
    }

    // Print the generated values
    for (Object value : randomValues) {
      System.out.println(value);
    }
  }

  // Method to generate a random string of a given length
  public static String generateRandomString(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    Random random = new Random();
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(characters.length());
      sb.append(characters.charAt(index));
    }

    return sb.toString();
  }

}
