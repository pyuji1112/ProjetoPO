package ggc.core;

public abstract class Transaction {

  private double _value;

  public double getValue() {
    return this._value;
  }
  
  public abstract void pay(Partner partner);
  public abstract boolean isPaid();
}
