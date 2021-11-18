package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable {

  private Product _product;
  private Partner _partner;
  private double _value;
  private int _quantity;
  private Date _limitDate;
  private int _transactionId;
  private boolean _paid;

  public Transaction(Product product, Partner partner, int quantity, Date limitDate) {
    _limitDate = limitDate;
    _partner = partner;
    _product = product;
    _quantity = quantity;
    _paid = false;
  }

  public Partner getPartner() {
    return _partner;
  }

  public double getValue() {
    return _value * _quantity;
  }

  public int getTransactionId() {
    return _transactionId;
  }

  public void setId(int transactionId) {
    this._transactionId = transactionId;
  }

  public Product getProduct() {
    return _product;
  }

  public int getQuantity() {
    return _quantity;
  }

  public Date getLimitDate() {
    return _limitDate;
  }

  public void setValue(double value) {
    _value = value;
  }

  public boolean isPaid() {
    return this._paid;
  }

  public void pay(Date paymentDate) {
    this._paid = true;
    this._limitDate = paymentDate;
  }

  public abstract String showTransaction(Date date);

}
