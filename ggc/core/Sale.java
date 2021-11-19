package ggc.core;

import java.io.Serializable;

import ggc.core.Date;

public class Sale extends Transaction implements Serializable {

  public Sale(Product product, Partner partner, int quantity, Date limitDate) {
    super(product, partner, quantity, limitDate);
  }

  public Period calculatePeriod(int n, Date currentDate) {
    if (this.getLimitDate().getDate() - currentDate.getDate() >= n)
      return Period.P1;
    else if (this.getLimitDate().getDate() - currentDate.getDate() < n)
      return Period.P2;
    else if (0 < this.getLimitDate().getDate() - currentDate.getDate()
        && currentDate.getDate() - this.getLimitDate().getDate() <= n)
      return Period.P3;
    else if (this.getLimitDate().getDate() - currentDate.getDate() > n)
      return Period.P4;
    return null;
  }

  public int extraDays(Date currentDate) {
    return currentDate.getDate() - getLimitDate().getDate();
  }

  public boolean twoDaysBeforeDeadline(Date currentDate) {
    return currentDate.getDate() - this.getLimitDate().getDate() == 2;
  }

  public boolean oneDayAfterDeadline(Date currentDate) {
    return extraDays(currentDate) == 1;
  }

  @Override
  public String showTransaction(Date currentDate) {
    String transaction = "VENDA|" + getTransactionId() + "|" + this.getPartner().getId() + "|"
        + this.getProduct().getProductId() + "|" + this.getQuantity() + "|" + (int) this.getValue() + "|"
        + (int) this.getPartner().valueToPay(this, getValue(), currentDate) + "|" + this.getLimitDate().getDate();
    if (this.isPaid())
      transaction += "|" + getPaymentDate().getDate();

    return transaction;
  }

}
