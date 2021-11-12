package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable {

  private Product _product;
  private Partner _partner;
  private double _value;
  private int _quantity;
  private Date _paymentDate;
  private static int _transactionId;
  private boolean _paid;

  public Transaction(Product product, Partner partner, int quantity, Date paymentDate) {
    _product = product;
    newTransaction();
    _quantity = quantity;
    _paymentDate = paymentDate;
    _paid = false;
  }

  public Partner getPartner() {
    return _partner;
  }

  public double getValue() {
    return _value;
  }

  public int getTransactionId() {
    return _transactionId;
  }

  public Product getProduct() {
    return _product;
  }

  public int getQuantity() {
    return _quantity;
  }

  public Date getPaymentDate() {
    return _paymentDate;
  }

  void newTransaction() {
    _transactionId++;
  }

  void setValue(double value) {
    _value = value;
  }

  public boolean isPaid() {
    return this._paid;
  }

  public void pay() {
    this._paid = true;
  }


}
