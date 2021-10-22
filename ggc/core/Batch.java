package ggc.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Batch implements Comparator<Batch> {
    private String _suplier;
    private int _availableUnits;
    private int _unitPrice;
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

    public int getUnitPrice() {
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

    @Override
    public int compare(Batch b1, Batch b2) {
        int cmp0 = b1.getProductsId().compareTo(b2.getProductsId());

        if (cmp0 != 0)
            return cmp0;

        int cmp1 = b1.getSuplier().compareTo(b2.getSuplier());

        if (cmp1 != 0)
            return cmp1;

        Integer b1PriceStock = b1.getUnitPrice() + b1.getAvailableUnits();
        Integer b2PriceStock = b2.getUnitPrice() + b2.getAvailableUnits();

        return b1PriceStock.compareTo(b2PriceStock);
    }

}
