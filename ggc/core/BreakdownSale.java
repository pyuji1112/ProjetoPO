package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BreakdownSale extends Sale implements Serializable {
    private List<Batch> _allBatches;
    private double _baseValue;
    private Component _resultingProduct;

    public BreakdownSale(Product product, Partner partner, int quantity, int paymentDate, List<Batch> batches) {
        super(product, partner, quantity, paymentDate);
        _allBatches = new ArrayList<>(batches);
    }

    public List<Batch> getAllBatches() {
        return _allBatches;
    }

    public double getBaseValue() {
        return _baseValue;
    }

    public void setBaseValue(double value) {
        _baseValue = value;
    }

    public Component getResultingProcuct() {
        return _resultingProduct;
    }

    public void setResultingProduct(Component resultingProduct) {
        _resultingProduct = resultingProduct;
    }

    double getComponentsLowestPrice(Component component) {
        List<Batch> allBatches = getAllBatches();
        double lowestPrice = 0;

        for (Batch b : allBatches) {
            if (b.getProduct().getProductId().equals(component.getProductId())
                    && b.getProduct().getPrice() < lowestPrice)
                lowestPrice = b.getProduct().getPrice();
        }

        return lowestPrice;
    }

    double getComponentsHighestPrice(Component component, Partner partner) {
        List<Acquisition> allAcquisitions = partner.getAcquisitions();
        List<Sale> allSales = partner.getSales();
        double highestPrice = 0;

        for (Acquisition a : allAcquisitions) {
            if (component.getProductId().equals(a.getProduct().getProductId()) && a.getValue() > highestPrice)
                highestPrice = a.getValue();
        }

        for (Sale s : allSales) {
            if (component.getProductId().equals(s.getProduct().getProductId()) && s.getValue() > highestPrice)
                highestPrice = s.getValue();
        }

        return highestPrice;
    }

    Batch addBatch(Partner partner, int availableUnits, double unitPrice, Product product) {
        return new Batch(partner, availableUnits, unitPrice, product);
    }

    public boolean hasComponent(Component component) {
        List<Batch> allBatches = getAllBatches();
        for (Batch b : allBatches) {
            if (b.getProduct().getProductId().toLowerCase().equals(component.getProductId().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    double doAcquisitionComponents(Batch newBatch, Partner partner, Component component, double componentPrice) {
        List<Batch> allBatches = getAllBatches();
        double componentsTotalPrice = 0;

        allBatches.add(newBatch);
        componentsTotalPrice += componentPrice;
        return componentsTotalPrice;
    }

    double addComponentsToBatch(List<Component> components, Partner partner) {
        double componentsTotalPrice = 0;
        double price = 0;

        for (Component c : components) {
            if (hasComponent(c))
                price = getComponentsLowestPrice(c);
            else
                price = getComponentsHighestPrice(c, partner);

            Batch newBatch = addBatch(partner, c.getQuantity(), price, c);
            Component component = new Component(c.getProductId(), price, c.getQuantity());
            setResultingProduct(component);
            componentsTotalPrice += doAcquisitionComponents(newBatch, partner, c, price);
        }
        return componentsTotalPrice;
    }

    void doBreakdownSale(Product product, int amount, Partner partner) {
        List<Component> components = new ArrayList<>();
        List<Batch> allBatches = getAllBatches();
        Iterator<Batch> iter = allBatches.iterator();
        int count = 0;
        double componentsTotalPrice = 0;
        double breakdownTransactionRealPrice = product.getPrice() - componentsTotalPrice;

        while (count <= amount) {
            Batch b = iter.next();

            if (b.getProduct().getProductId().equals(product.getProductId())) {
                if (b.getAvailableUnits() > amount) {
                    components = b.getProduct().getRecipe().getComponents();
                    componentsTotalPrice += addComponentsToBatch(components, partner);
                    b.uptadeStock(-amount);
                    break;
                }

                else {
                    count += b.getAvailableUnits();
                    iter.remove();
                }
            }
        }

        if (breakdownTransactionRealPrice > 0)
            setValue(breakdownTransactionRealPrice);
        else
            setValue(0);

        setBaseValue(breakdownTransactionRealPrice);
    }

    @Override
    public String showTransaction() {
        return getPartner().getId() + getProduct().getProductId() + getQuantity() + getValue() + getBaseValue()
                + getPaymentDate() + getResultingProcuct().getProductId() + getResultingProcuct().getQuantity()
                + getResultingProcuct().getPrice();
    }
}
