package ggc.core;

import java.util.Comparator;

public class BatchComparator implements Comparator<Batch> {
    @Override
    public int compare(Batch b1, Batch b2) {
        int compareProductId = b1.getProduct().getProductId().toLowerCase().
                compareTo(b2.getProduct().getProductId().toLowerCase());

        if (compareProductId != 0)
            return compareProductId;

        int compareSuplierId = b1.getSuplier().getId().toLowerCase().
                compareTo(b2.getSuplier().getId().toLowerCase());

        if (compareSuplierId != 0)
            return compareSuplierId;

        Double b1PriceStock = (double) b1.getUnitPrice() + b1.getAvailableUnits();
        Double b2PriceStock = (double) b2.getUnitPrice() + b2.getAvailableUnits();

        return b1PriceStock.compareTo(b2PriceStock);
    }
}
