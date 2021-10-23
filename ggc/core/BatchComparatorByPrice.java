package ggc.core;

import java.util.Comparator;

public class BatchComparatorByPrice implements Comparator<Batch> {
    public int compare(Batch b1, Batch b2) {
        Integer b1Price = b1.getUnitPrice();
        Integer b2Price = b2.getUnitPrice();

        return b1Price.compareTo(b2Price);
    }
}
