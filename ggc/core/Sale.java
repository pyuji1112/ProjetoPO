package ggc.core;

import ggc.core.Date;

public class Sale extends Transaction {
  private int _limitDate;

  public int getLimitDate() {
    return this._limitDate;
  }

  public Period calculatePeriod(int n) {
    if (this.getLimitDate() - Date.getDate() >= n) return Period.P1;
    else if (this.getLimitDate() - Date.getDate() < n) return Period.P2;
    else if (0 < Date.getDate() - this.getLimitDate() && Date.getDate() - this.getLimitDate() <= n) return Period.P3;
    else if (Date.getDate() - this.getLimitDate() > n) return Period.P4;
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
}
