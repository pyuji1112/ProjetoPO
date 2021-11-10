package ggc.core;

import java.io.Serializable;

public class Acquisition extends Transaction implements Serializable {

  public Acquisition(Product product, Partner partner, int quantity, int paymentDate) {
    super(product, partner, quantity, paymentDate);
  }

  public void pay(Partner partner) {

  }

  public boolean isPaid() {
    return false;
  }

  @Override
  public String showTransaction() {
    return getPartner().getId() + getProduct().getProductId() + getQuantity() + getValue() + getPaymentDate();
  }
}
