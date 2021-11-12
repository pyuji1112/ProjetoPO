package ggc.core;

import java.io.Serializable;

import ggc.core.Date;

public class Sale extends Transaction implements Serializable {

  private int _id;
  private Date _limitDate;

  public Sale(Product product, Partner partner, int quantity, Date paymentDate) {
    super(product, partner, quantity, paymentDate);
  }

  public Period calculatePeriod(int n) {
    if (this.getLimitDate().getDate() - this.getPaymentDate().getDate() >= n) return Period.P1;
    else if (this.getLimitDate().getDate() - this.getPaymentDate().getDate() < n) return Period.P2;
    else if (0 < this.getPaymentDate().getDate() - this.getLimitDate().getDate() && this.getPaymentDate().getDate() - this.getLimitDate().getDate() <= n) return Period.P3;
    else if (this.getPaymentDate().getDate() - this.getLimitDate().getDate() > n) return Period.P4;
    return null;
  }

  public int extraDays() {
    return this.getPaymentDate().getDate() - this._limitDate.getDate();
  }

  public boolean twoDaysBeforeDeadline() {
    return this._limitDate.getDate() - this.getPaymentDate().getDate() == 2;
  }

  public boolean oneDayAfterDeadline() {
    return extraDays() == 1;
  }

  @Override
  public String toString() {
    return "VENDA|" + this._id + "|" + this.getPartner().getId() + "|" + this.getProduct().getProductId() + "|"
    + this.getQuantity() + "|" + this.getValue() + "|" + this.getPartner().valueToPay(this) + "|"
    + this.getLimitDate().getDate() + "|" + this.getPaymentDate().getDate();
  }

  public int getId() {
    return this._id;
  }

  public Date getLimitDate() {
    return this._limitDate;
  }
}
