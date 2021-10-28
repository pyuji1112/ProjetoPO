package ggc.core;

import java.io.Serializable;

public class Batch implements Serializable {
    private String _suplier;
    private int _availableUnits;
    private double _unitPrice;
    private Product _product;

    public Batch(String suplier, int availableUnits, int unitPrice, Product product) {
        _suplier = suplier;
        _availableUnits = availableUnits;
        _unitPrice = unitPrice;
        _product = product;
    }

    public String getSuplier() {
        return _suplier;
    }

    public int getAvailableUnits() {
        return _availableUnits;
    }

    public double getUnitPrice() {
        return _unitPrice;
    }

    public Product getProduct() {
        return _product;
    }

    public String showBatch() {
        return getProduct().getProductId() + "|" + getSuplier() + "|"
                + Math.round(getUnitPrice()) + "|" + getAvailableUnits();
      }
}
