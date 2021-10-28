package ggc.core;

import java.io.Serializable;

import ggc.core.Product;

public class Notification implements Serializable {
  private String _type;
  private Product _product;

  public String getType() {
    return this._type;
  }

  public Product getProduct() {
    return this._product;
  }

}
