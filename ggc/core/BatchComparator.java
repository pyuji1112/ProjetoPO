package ggc.core;

import java.util.Comparator;

public class BatchComparator implements Comparator<Batch> {
    @Override
    public int compare(Batch b1, Batch b2) {
        int compareProductId = b1.getProductsId().compareTo(b2.getProductsId());

        if (compareProductId != 0)
            return compareProductId;

        int compareSuplierId = b1.getSuplier().compareTo(b2.getSuplier());

        if (compareSuplierId != 0)
            return compareSuplierId;

        Integer b1PriceStock = b1.getUnitPrice() + b1.getAvailableUnits();
        Integer b2PriceStock = b2.getUnitPrice() + b2.getAvailableUnits();

        return b1PriceStock.compareTo(b2PriceStock);
    }
}