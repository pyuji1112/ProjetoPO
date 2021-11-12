package ggc.core;

import java.io.Serializable;

public class Acquisition extends Transaction implements Serializable {

  public Acquisition(Product product, Partner partner, int quantity, Date paymentDate) {
    super(product, partner, quantity, paymentDate);
  }

  public void pay(Partner partner) {

  }

  public boolean isPaid() {
    return false;
  }

  @Override
  public String toString() {
    return getPartner().getId() + "|" + getProduct().getProductId() + "|" + getQuantity() + "|" + getValue() + "|" + getPaymentDate();
  }
}
