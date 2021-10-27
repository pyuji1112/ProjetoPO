package ggc.core;

import java.util.*;
import ggc.core.Notification;
import ggc.core.Batch;
import ggc.core.Transaction;
import ggc.core.Product;

public class Partner {

  private String _name;
  private String _address;
  private String _id;
  private Status _status;
  private double _points;
  private List<Acquisition> _acquisitions;
  private List<Sale> _sales;
  private List<Batch> _batches;
  private List<Notification> _notifications;

  public Partner(String id, String name, String address) {
    this._id = id.toLowerCase();
    this._name = name;
    this._address = address;
    this._status = Status.NORMAL;
    this._acquisitions = new ArrayList<Acquisition>();
    this._batches = new ArrayList<Batch>();
    this._sales = new ArrayList<Sale>();
    this._notifications = new ArrayList<Notification>();
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

  public void addBatch(Batch batch) {
    this._batches.add(batch);
  }

  public void removeBatch(Batch batch) {
    this._batches.remove(batch);
  }

  public void addNotification(Notification notification) {
    this._notifications.add(notification);
  }

  public void removeNotification(Notification notification) {
    this._notifications.remove(notification);
  }

  public void determineStatus() {
    if (this._points > 2000 && this._points < 25000) this._status = Status.SELECTION;
    else if (this._points > 25000) this._status = Status.ELITE;
    else this._status = Status.NORMAL;
  }

  public void pay(Transaction transaction) {
    transaction.pay(this);
  }

  public void toggleNotification(Product product) {

  }

  public double getTotalValue() {
    double value = 0;
    for (Acquisition a : this._acquisitions) {
      value += a.getValue();
    }
    for (Sale s : this._sales) {
      value += s.getValue();
    }
    return value;
  }

  public double getRawValue() {
    double value = 0;
    for (Acquisition a : this._acquisitions) {
      if (a.isPaid()) value += a.getValue();
    }
    for (Sale s : this._sales) {
      if (s.isPaid()) value += s.getValue();
    }
    return value;
  }

  public double getValuePaid() {
    double value = 0;
    for (Acquisition a : this._acquisitions) {
      if (a.isPaid()) value += a.getValue();
    }
    for (Sale s : this._sales) {
      if (s.isPaid()) value += s.getValue();
    }
    return value;
  }

  @Override
  public String toString() {
    string toBeReturned = this._id + "|" + this._name + "|" + this._address + "|" + this._status.name() + "|" + this._points + "|" + this.getTotalValue() + "|" + this.getRawValue() + "|" + this.getValuePaid();
    if (!this._notifications.isEmpty()) {
      for (Notification n : this._notifications) toBeReturned += '\n' + n.getType() + "|" + n.getProduct().getProductId() + "|" + n.getProduct().getPrice();
    }
    return toBeReturned;
  }
