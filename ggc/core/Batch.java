package ggc.core;

import java.util.List;
import java.util.ArrayList;

public class Batch {
    private String _suplier;
    private int _availableUnits;
    private double _unitPrice;
    private List<Product> _product;

    public Batch(String suplier, int availableUnits, int unitPrice) {
        _suplier = suplier;
        _availableUnits = availableUnits;
        _unitPrice = unitPrice;
        _product = new ArrayList<>();
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

    public List<Product> getProduct() {
        return _product;
    }

    public String getProductsId() {
        return _product.get(0).getProductId();
    }

    public boolean addProduct(Product product) {
        return _product.add(product);
    }
}
