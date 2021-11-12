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
  private double _availableBalance;
  private double _accountingBalance;
  private Date _date = new Date();


  public Warehouse() {
    this._batchesList = new ArrayList<Batch>();
    this._partnerList = new ArrayList<Partner>();
    this._transactions = new ArrayList<Transaction>();
    this._parser = new Parser(this);
    _availableBalance = 0;
    _accountingBalance = 0;
  }

  public Date getDate() {
    return _date;
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

  public Transaction searchTransactionById(int id) {
    for (Transaction t : _transactions) {
      if (t.getTransactionId() == id)
        return t;
    }
    return null;
  }

  String showProduct(String productId) {
    Product product = searchProductById(productId);
    return productId + "|" + Math.round(maxPrice(productId)) + "|" + currentStock(productId) + "|" + product.toString();
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

  public String getAllNotificationsOf(Partner partner) {
    String line = "";
    for (Batch b : this._batchesList) {
      if (b.getObservers().contains(partner)) return line += '\n' + b.getNotificationType() + "|" + b.getProduct().getProductId() + "|" + b.getProduct().getPrice();
    }
    return "";
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
    if (b.getAvailableUnits() == 0) b.setNotificationType("NEW");
    else if (isLowerPrice(b.getProduct())) b.setNotificationType("BARGAIN");
    for (Partner p : this._partnerList)
      b.addObserver(p);
  }

  public boolean isLowerPrice(Product p) {
    return Double.compare(p.getPrice(), minPrice(p.getProductId())) >= 0 ? false: true;
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

  public double minPrice(String productId) {
    double minPrice = 0;

    for (Batch b : _batchesList) {
      String currentProduct = b.getProduct().getProductId().toLowerCase();

      if (currentProduct.equals(productId) && minPrice > b.getUnitPrice()) {
        minPrice = b.getUnitPrice();
      }
    }

    return minPrice;
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

  Component doSale(Sale sale) {
    if (isSalePossible(sale.getProduct(), sale.getQuantity())) {
      Partner partner = searchPartnerById(sale.getPartner().getId());
      sale.pay();
      partner.addSale(sale);
      partner.addPoints(sale.getValue() * 10);
      registerTransaction(sale);
    } else {
      if (isAgreggationPossible(sale.getProduct(), sale.getQuantity()) == null) {
        doAgreggation(sale.getProduct(), sale.getQuantity());
      } else {
        return isAgreggationPossible(sale.getProduct(), sale.getQuantity());
      }
    }
    return null;
  }

  void doBreakdownSale(String productId, int amount, String partnerId) {
    Product product = searchProductById(productId);
    Partner partner = searchPartnerById(partnerId);

    BreakdownSale newBreakdownSale = new BreakdownSale(product, partner, amount, getDate(), allBatches());
    newBreakdownSale.doBreakdownSale(product, amount, partner);
    newBreakdownSale.pay();
    partner.addSale(newBreakdownSale);
    partner.addPoints(newBreakdownSale.getValue() * 10);
    registerTransaction(newBreakdownSale);
    uptadeBatches(newBreakdownSale.getAllBatches());
  }

  void doAcquisition(Partner partner, Product product, double price, int amount) {
    Acquisition newAcquisition = new Acquisition(product, partner, amount, getDate());
    newAcquisition.pay();
    partner.addAcquisition(newAcquisition);
    registerTransaction(newAcquisition);
    Batch newBatch = new Batch(partner, amount, price, product);
    addBatch(newBatch);
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

  Component isAgreggationPossible(Product product, int amount) {
    List<Component> components = product.getRecipe().getComponents();
    for (Component c : components) {
      if (currentStock(c.getProductId()) < c.getQuantity() * amount) {
        return c;
      }
    }
    return null;
  }

  public boolean isSalePossible(Product product, int amount) {
    return currentStock(product.getProductId()) >= amount;
  }

  double agregation(Component component, int amount) {
    List<Batch> allBatches = allBatchesOrdered();
    Iterator<Batch> iter = allBatches.iterator();
    int count = 0;
    double totalPrice = 0;

    while (count <= amount) {
      Batch b = iter.next();

      if (b.getProduct().getProductId().equals(component.getProductId())) {
        if (b.getAvailableUnits() > amount) {
          b.uptadeStock(-amount);
          break;
        }

        else {
          count += b.getAvailableUnits();
          iter.remove();
        }
        totalPrice += b.getProduct().getPrice();
      }
    }
    return totalPrice;
  }

  double doSingleAgreggation(Product product) {
    List<Component> components = product.getRecipe().getComponents();
    double totalPrice = 0;

    for (Component c : components)
      totalPrice += agregation(c, c.getQuantity());
    return totalPrice;
  }

  double doAgreggation(Product product, int amount) {
    double totalPrice = 0;
    int count = 0;

    while (count <= amount) {
      totalPrice += doSingleAgreggation(product);
      count++;
    }
    return totalPrice;
  }

  String showTransaction(int transactionId) {
    String transaction = "";

    for (Transaction t : getTransactions()) {
      if (t.getTransactionId() == transactionId)
        transaction += transactionId + t.toString();
        break;
    }
    return transaction;
  }

  public boolean hasTransaction(int transactionId) {
    for (Transaction t : this._transactions) {
      if (t.getTransactionId() == transactionId) return true;
    }
    return false;
  }

  public double availableBalance() {
    return _availableBalance;
  }

  public double accountingBalance() {
    return _accountingBalance;
  }

  public void changeAvailableBalance(double value) {
    _availableBalance += value;
  }

  public void changeAccountingBalance(double value) {
    _accountingBalance += value;
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
