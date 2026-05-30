package com.ut.nlSystemAPi.helper;

public class Month {

  private Month() {

  }

  public static String name(int index) {
    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    return months[index];
  }

}