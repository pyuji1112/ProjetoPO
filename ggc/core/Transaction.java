package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable {

  private Product _product;
  private Partner _partner;
  private double _value;
  private int _quantity;
  private int _paymentDate;
  private static int _transactionId;

  public Transaction(Product product, Partner partner, int quantity, int paymentDate) {
    _product = product;
    _transactionId = 0;
    _quantity = quantity;
    _paymentDate = paymentDate;
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

  public int getPaymentDate() {
    return _paymentDate;
  }

  void newTransaction() {
    _transactionId++;
  }

  void setValue(double value) {
    _value = value;
  }

  public abstract void pay(Partner partner);

  public abstract boolean isPaid();

  public abstract String showTransaction(int transactionId);
}
