package ggc.core;

public class Date {

  private static int _date;

  public static int getDate() {
    return _date;
  }

  public void advanceDay() {
    this._date++;
  }

  public void skipDays(int days) {
    this._date += days;
  }

}
