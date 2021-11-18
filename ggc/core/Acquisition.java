package ggc.core;

import java.io.Serializable;

public class Acquisition extends Transaction implements Serializable {

  public Acquisition(Product product, Partner partner, int quantity, Date paymentDate) {
    super(product, partner, quantity, paymentDate);
  }

  @Override
  public String showTransaction(Date date) {
    return "COMPRA|" + getTransactionId() + "|" + getPartner().getId() + "|" + getProduct().getProductId() + "|"
        + getQuantity() + "|" + (int) getValue() + "|" + date.getDate();
  }
}
