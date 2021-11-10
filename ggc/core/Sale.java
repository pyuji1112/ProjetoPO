package ggc.core;

import java.io.Serializable;

import ggc.core.Date;

public class Sale extends Transaction implements Serializable {
  
  public Sale(Product product, Partner partner, int quantity, int paymentDate) {
    super(product, partner, quantity, paymentDate);
  }

  private int _limitDate;

  public int getLimitDate() {
    return this._limitDate;
  }

  public Period calculatePeriod(int n, int date) {
    if (this.getLimitDate() - date >= n) return Period.P1;
    else if (this.getLimitDate() - date < n) return Period.P2;
    else if (0 < date - this.getLimitDate() && date - this.getLimitDate() <= n) return Period.P3;
    else if (date - this.getLimitDate() > n) return Period.P4;
    return null;
  }

  public void pay(Partner partner) {
    switch(partner.getStatus()) {
      case NORMAL:
        break;
      case SELECTION:
        break;
      case ELITE:
        break;
    }
  }

  public boolean isPaid() {
    return false;
  }

  @Override
  public String showTransaction(int transactionId) {
    // TODO Auto-generated method stub
    return null;
  }
}
