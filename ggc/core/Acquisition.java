package ggc.core;

import java.io.Serializable;

public class Acquisition extends Transaction implements Serializable {
  public void pay(Partner partner) {

  }


  public boolean isPaid() {
    return false;
  }
}
