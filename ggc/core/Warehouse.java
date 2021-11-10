package ggc.core;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner;

import java.util.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private List<Batch> _batchesList;
  private Parser _parser;
  private List<Partner> _partnerList;
  private List<Transaction> _transactions;
  private double _currentBalance;
  private Date _date = new Date();

  public Warehouse() {
    this._batchesList = new ArrayList<Batch>();
    this._partnerList = new ArrayList<Partner>();
    this._transactions = new ArrayList<Transaction>();
    this._parser = new Parser(this);
    _currentBalance = 0;
  }

  public int getDate() {
    return _date.getDate();
  }

  public void skipDays(int days) {
    _date.skipDays(days);
  }

  /* Returns a copy of the list of partners */
  public List<Partner> getPartnerList() {
    List<Partner> copy = new ArrayList<Partner>();
    copy.addAll(this._partnerList);
    return copy;
  }

  public void registerPartner(Partner partner) {
    this._partnerList.add(partner);
  }

  /*
   * Searches a partner with given ID. Comparison is made with all lower cased
   * characters.
   */
  public Partner searchPartnerById(String id) {
    for (Partner p : this._partnerList) {
      if (p.getId().toLowerCase().equals(id.toLowerCase()))
        return p;
    }
    return null;
  }

  /*
   * Searches a product with given ID. Comparison is made with all lower cased
   * characters.
   */
  public Product searchProductById(String product) {
    for (Batch b : _batchesList) {
      if (b.getProduct().getProductId().toLowerCase().equals(product.toLowerCase()))
        return b.getProduct();
    }
    return null;
  }

  String showProduct(String productId) {
    Product product = searchProductById(productId);
    return productId + "|" + Math.round(maxPrice(productId)) + "|" + currentStock(productId) + "|" + product.showProduct();
  }

  /* Checks if warehouse has a partner given their ID. */
  public boolean hasPartner(String id) {
    for (Partner p : this._partnerList) {
      if (p.getId().toLowerCase().equals(id.toLowerCase()))
        return true;
    }
    return false;
  }

  public List<Batch> allBatches() {
    return Collections.unmodifiableList(_batchesList);
  }

  /* Returns a copy of the list of batches, but ordered. */
  public List<Batch> allBatchesOrdered() {
    List<Batch> copy = new ArrayList<Batch>();
    copy.addAll(this._batchesList);
    Collections.sort(copy, new BatchComparator());
    return copy;
  }

  public void addBatch(Batch b) {
    this._batchesList.add(b);
  }

  /* Returns the current stock of a product in the warehouse. */
  public int currentStock(String productId) {
    int currentStock = 0;

    for (Batch b : _batchesList) {
      String currentProduct = b.getProduct().getProductId().toLowerCase();

      if (currentProduct.equals(productId.toLowerCase())) {
        currentStock += b.getAvailableUnits();
      }
    }
    return currentStock;
  }

  /* Returns the highest price that a productin the warehouse is */
  public double maxPrice(String productId) {
    double maxPrice = 0;

    for (Batch b : _batchesList) {
      String currentProduct = b.getProduct().getProductId().toLowerCase();

      if (currentProduct.equals(productId) && maxPrice < b.getUnitPrice()) {
        maxPrice = b.getUnitPrice();
      }
    }

    return maxPrice;
  }

  public boolean batchHasPartner(String partner) {
    for (Batch b : _batchesList) {
      if (b.getSuplier().getId().toLowerCase().equals(partner.toLowerCase())) {
        return true;
      }
    }

    return false;
  }

  public boolean hasProduct(String product) {
    for (Batch b : _batchesList) {
      if (b.getProduct().getProductId().toLowerCase().equals(product.toLowerCase())) {
        return true;
      }
    }
    return false;
  }

  void registerTransaction(Transaction transaction) {
    _transactions.add(transaction);
    transaction.newTransaction();
  }

  void uptadeBatches(List<Batch> batches) {
    _batchesList = new ArrayList<>(batches);
  }

  void doBreakdownSale(String productId, int amount, String partnerId) {
    Product product = searchProductById(productId);
    Partner partner = searchPartnerById(partnerId);

    BreakdownSale newBreakdownSale = new BreakdownSale(product, partner, amount, getDate(), allBatches());
    newBreakdownSale.doBreakdownSale(product, amount, partner);
    newBreakdownSale.pay(partner);
    partner.addSale(newBreakdownSale);
    partner.addPoints(newBreakdownSale.getValue()* 10);
    registerTransaction(newBreakdownSale);
    uptadeBatches(newBreakdownSale.getAllBatches());
  }

  void doAcquisition(Partner partner, Product product, double price, int amount) {
    Acquisition newAcquisition = new Acquisition(product, partner, amount, getDate());
    newAcquisition.pay(partner);
    partner.addAcquisition(newAcquisition);
    registerTransaction(newAcquisition);
    Batch newBatch = new Batch(partner, amount, price, product);
    addBatch(newBatch);
    changeCurrentBalance(price);
  }
  
  Component makeNewComponent(String componentId, int quantity) {
    return new Component(componentId, quantity);
  }

  Recipe makeNewRecipe(List<Component> components) {
    return new Recipe(components);
  }

  DerivedProduct makeNewDerivedProduct(String productId, double price, Recipe recipe, double alpha) {
    return new DerivedProduct(productId, price, recipe, alpha);
  }

  List<Transaction> getTransactions() {
    return Collections.unmodifiableList(_transactions);
  }

  String showTransaction(int transactionId) {
    String transaction = "";
    for (Transaction t : getTransactions()) {
      if (t.getTransactionId() == transactionId)
        transaction += transactionId + t.showTransaction();
        break;
    }
    return transaction;
  }

  public double currentBalance() {
    return _currentBalance;
  }

  public void changeCurrentBalance(double value) {
    _currentBalance += value;
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException {
    this._parser.parseFile(txtfile);
  }

}
