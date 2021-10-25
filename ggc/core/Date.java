package gcc.core

public class Date {

  private int _date;

  public int getDate() {
    return this._date;
  }

  public void advanceDay() {
    this._date++;
  }

  public void skipDays(int days) {
    this._date += days;
  }
  
}
