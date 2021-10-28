package ggc.core;

import java.io.Serializable;
import ggc.core.Partner;

public class Batch implements Serializable {
    private Partner _suplier;
    private int _availableUnits;
    private double _unitPrice;
    private Product _product;

    public Batch(Partner suplier, int availableUnits, double unitPrice, Product product) {
        _suplier = suplier;
        _availableUnits = availableUnits;
        _unitPrice = unitPrice;
        _product = product;
    }

    public Partner getSuplier() {
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
        return getProduct().getProductId().toUpperCase() + "|" + getSuplier().getId().toUpperCase() + "|"
                + Math.round(getUnitPrice()) + "|" + getAvailableUnits();
      }
}
