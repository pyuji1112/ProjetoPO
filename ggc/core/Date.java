package ggc.core;

public class Date {

  private static int _date;

  public static int getDate() {
    return _date;
  }

  public static void advanceDay() {
    Date._date++;
  }

  public static void skipDays(int days) {
    Date._date += days;
  }

}
