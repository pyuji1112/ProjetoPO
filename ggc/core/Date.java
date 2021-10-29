package ggc.core;

import java.io.Serializable;

public class Date implements Serializable {

  private int _date;

  public int getDate() {
    return _date;
  }

  /* Advances one day */
  public void advanceDay() {
    this._date++;
  }

  /* Advances days based on an input */
  public void skipDays(int days) {
    this._date += days;
  }

}
