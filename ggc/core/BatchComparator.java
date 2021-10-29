package ggc.core;

import java.util.Comparator;

public class BatchComparator implements Comparator<Batch> {
    @Override
    public int compare(Batch b1, Batch b2) {
        int compareProductId = b1.getProduct().getProductId().toLowerCase().
                compareTo(b2.getProduct().getProductId().toLowerCase());

        int compareSuplierId = b1.getSuplier().getId().toLowerCase().
        compareTo(b2.getSuplier().getId().toLowerCase());

        Double b1Price = b1.getUnitPrice();
        Double b2Price = b2.getUnitPrice();

        if (compareProductId != 0)
            return compareProductId;

        if (compareSuplierId != 0)
            return compareSuplierId;

        if (Double.compare(b1Price, b2Price) != 0)
          return Double.compare(b1Price, b2Price);

        return b1.getAvailableUnits() - b2.getAvailableUnits();
    }
}
