package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable {

  private double _value;

  public double getValue() {
    return this._value;
  }

  public abstract void pay(Partner partner);
  public abstract boolean isPaid();
}
