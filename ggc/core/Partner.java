package ggc.core;

import java.util.*;
import java.io.Serializable;

import ggc.core.Notification;
import ggc.core.Batch;
import ggc.core.Transaction;
import ggc.core.Product;

public class Partner implements Serializable, Observer {

  private String _name;
  private String _address;
  private String _id;
  private Status _status;
  private double _points;
  private List<Acquisition> _acquisitions;
  private List<Sale> _sales;
  private List<Batch> _batches;

  public Partner(String id, String name, String address) {
    this._id = id;
    this._name = name;
    this._address = address;
    this._status = Status.NORMAL;
    this._acquisitions = new ArrayList<Acquisition>();
    this._batches = new ArrayList<Batch>();
    this._sales = new ArrayList<Sale>();
  }

  public String getName() {
    return this._name;
  }

  public String getAddress() {
    return this._address;
  }

  public String getId() {
    return this._id;
  }

  public Status getStatus() {
    return this._status;
  }

  public void setStatus(Status status) {
    this._status = status;
  }

  public double getPoints() {
    return this._points;
  }

  public void addPoints(double points) {
    this._points += points;
  }

  public List<Sale> getSalesList() {
    List<Sale> copy = new ArrayList<Sale>();
    copy.addAll(this._sales);
    return copy;
  }

  public List<Acquisition> getAcquisitionsList() {
    List<Acquisition> copy = new ArrayList<Acquisition>();
    copy.addAll(this._acquisitions);
    return copy;
  }

  public void addBatch(Batch batch) {
    this._batches.add(batch);
  }

  public void removeBatch(Batch batch) {
    this._batches.remove(batch);
  }


  /* Determines the status of the partner based on their points */
  public void determineStatus() {
    if (this._points > 2000 && this._points < 25000) this._status = Status.SELECTION;
    else if (this._points > 25000) this._status = Status.ELITE;
    else this._status = Status.NORMAL;
  }

  public double valueToPay(Sale sale, Double value, Date currentDate) {
    int n = 0;
    if (sale.getProduct() instanceof SimpleProduct) n = 5;
    else if (sale.getProduct() instanceof DerivedProduct) n = 3;
    else return 0;

    if (this._status.equals(Status.NORMAL)) return calculateValueNormal(sale, value, n, currentDate);
    else if (this._status.equals(Status.SELECTION)) return calculateValueSelection(sale, value, n, currentDate);
    else if (this._status.equals(Status.ELITE)) return calculateValueElite(sale, value, n, currentDate);

    return 0;
  }

  public double calculateValueNormal(Sale sale, double value, int n, Date currentDate) {
    switch(sale.calculatePeriod(n, currentDate)) {
      case P1:
        sale.setValue(value * 0.9);
        return value * 0.9;
      case P2:
        sale.setValue(value);
        return value;
      case P3:
        sale.setValue(value + sale.extraDays(currentDate) * (value * 0.05));
        return value + sale.extraDays(currentDate) * (value * 0.05);
      case P4:
        sale.setValue(value + sale.extraDays(currentDate) * (value * 0.1));
        return value + sale.extraDays(currentDate) * (value * 0.1);
    }
    return 0;
  }

  public double calculateValueSelection(Sale sale, double value, int n, Date currentDate) {
    switch(sale.calculatePeriod(n, currentDate)) {
      case P1:
        sale.setValue(value * 0.9);
        return value * 0.9;
      case P2:
        sale.setValue(sale.twoDaysBeforeDeadline(currentDate) ? value * 0.95 : value);
        return sale.twoDaysBeforeDeadline(currentDate) ? value * 0.95 : value;
      case P3:
        sale.setValue(sale.oneDayAfterDeadline(currentDate) ? value : value + sale.extraDays(currentDate) * (value * 0.02));
        return sale.oneDayAfterDeadline(currentDate) ? value : value + sale.extraDays(currentDate) * (value * 0.02);
      case P4:
        sale.setValue(value + sale.extraDays(currentDate) * (value * 0.05));
        return value + sale.extraDays(currentDate) * (value * 0.05);
    }
    return 0;
  }

  public double calculateValueElite(Sale sale, double value, int n, Date currentDate) {
    switch(sale.calculatePeriod(n, currentDate)) {
      case P1:
        sale.setValue(value * 0.9);
        return value * 0.9;
      case P2:
        sale.setValue(value * 0.9);
        return value * 0.9;
      case P3:
        sale.setValue(value * 0.95);
        return value * 0.95;
      case P4:
        sale.setValue(value);
        return value;
    }
    return 0;
  }

  public double getAcquisitionsValue() {
    double value = 0;
    for (Acquisition a : this._acquisitions) {
      value += a.getValue();
    }
    return value;
  }

  public double getSalesDone() {
    double value = 0;
    for (Sale s : this._sales) {
      value += s.getValue();
    }
    return value;
  }

  public double getSalesPaid() {
    double value = 0;
    for (Sale s : this._sales) {
      if (s.isPaid()) value += s.getValue();
    }
    return value;
  }

  public List<Sale> getSales() {
    return _sales;
  }

  public void addSale(Sale sale) {
    _sales.add(sale);
  }

  public void addAcquisition(Acquisition acquisition) {
    _acquisitions.add(acquisition);
  }

  @Override
  public String toString() {
    String toBeReturned = this._id + "|" + this._name + "|" + this._address + "|" + this._status.name() + "|" + (int) this._points + "|" + (int) this.getAcquisitionsValue() + "|" + (int) this.getSalesDone() + "|" + (int) this.getSalesPaid();
    return toBeReturned;
  }
}
