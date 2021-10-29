package ggc.core;

import java.io.Serializable;

public class Date implements Serializable {

  private int _date;

  public int getDate() {
    return _date;
  }

  public void advanceDay() {
    this._date++;
  }

  public void skipDays(int days) {
    this._date += days;
  }

}
