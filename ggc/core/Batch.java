package ggc.core;

import java.io.Serializable;
import java.util.*;

public class Batch implements Serializable {
    private Partner _suplier;
    private int _availableUnits;
    private double _unitPrice;
    private Product _product;
    private Set<Observer> _observers = new HashSet<Observer>();
    private String _notificationType;
    private List<Media> _media = new ArrayList<Media>();

    /**
     * @param {type} Partner suplier
     * @param {type} int availableUnits
     * @param {type} double unitPrice
     * @param {type} Product product
     */
    public Batch(Partner suplier, int availableUnits, double unitPrice, Product product) {
        _suplier = suplier;
        _availableUnits = availableUnits;
        _unitPrice = unitPrice;
        _product = product;
        _media = Arrays.asList(Media.values());

    }

    public Partner getSuplier() {
        return _suplier;
    }

    public boolean hasPartner(Partner partner) {
      for (Observer p : this._observers) {
        if (((Partner) p).getId().equals(partner.getId())) return true;
      }
      return false;
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

    public void setNotificationType(String type) {
      this._notificationType = type;
    }

    public String getNotificationType() {
      return this._notificationType;
    }


    int uptadeStock(int quantity) {
      return _availableUnits + quantity;
    }

    public boolean addObserver(Observer obs) {
      return _observers.add(obs);
    }

    public boolean removeObserver(Observer obs) {
      return _observers.remove(obs);
    }

    public Set<Observer> getObservers() {
      return this._observers;
    }

    /**
    * public String - Returns a string with all batch attributes.
    *
    * @return {type} String
    */
    public String toString() {
      return getProduct().getProductId() + "|" + getSuplier().getId() + "|" + Math.round(getUnitPrice()) + "|"
      + getAvailableUnits();
    }
}
