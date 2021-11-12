package ggc.core;

import java.io.Serializable;
import java.util.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;

/** Façade for access. */
public class WarehouseManager implements Serializable {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  public boolean isNewFile() {
    return _filename.equals("");
  }

  public Date getDate() {
    return _warehouse.getDate();
  }

  public void skipDays(int days) {
    _warehouse.skipDays(days);
  }

  public List<Batch> getAllBatches() {
    return _warehouse.allBatches();
  }

  public List<Batch> getAllBatchesOrdered() {
    return _warehouse.allBatchesOrdered();
  }

  public List<Batch> getAllBatchesOf(Product p) {
    List<Batch> productBatches = new ArrayList<Batch>();
    for (Batch b : getAllBatches()) {
      if (b.getProduct().getProductId().equals(p.getProductId())) productBatches.add(b);
    }
    return productBatches;
  }

  public int getCurrentStock(String productId) {
    return _warehouse.currentStock(productId);
  }

  public double getMaxPrice(String productId) {
    return _warehouse.maxPrice(productId);
  }

  public boolean hasPartner(String partner) {
    return _warehouse.hasPartner(partner);
  }

  public boolean hasProduct(String product) {
    return _warehouse.hasProduct(product);
  }

  public void registerPartner(Partner p) {
    this._warehouse.registerPartner(p);
  }

  public List<Partner> getPartnerList() {
    return this._warehouse.getPartnerList();
  }

  public Partner searchPartnerById(String id) {
    return this._warehouse.searchPartnerById(id);
  }

  public Transaction searchTransactionById(int id) {
    return _warehouse.searchTransactionById(id);
  }

  public Product searchProductById(String productId) {
    return _warehouse.searchProductById(productId);
  }

  public String showProduct(String productId) {
    return _warehouse.showProduct(productId);
  }

  /* Checks if partner is registered in the warehouse. */
  public boolean partnerExists(Partner partner) {
    for (Partner p : this._warehouse.getPartnerList()) {
      if (p.getId().equals(partner.getId()))
        return true;
    }
    return false;
  }

  public double getAvailableBalance() {
    return _warehouse.availableBalance();
  }

  public double getAccountingBalance() {
    return _warehouse.accountingBalance();
  }

  public void doRegisterSaleTransaction(Sale sale) {
    _warehouse.doSale(sale);
  }

  public void doRegisterBreakdownTransaction(String productId, int amount, String partnerId) {
    _warehouse.doBreakdownSale(productId, amount, partnerId);
  }

  public void doRegisterAcquisitionTransaction(Partner partner, Product product, double price, int amount) {
    _warehouse.doAcquisition(partner, product, price, amount);
  }

  public String showTransaction(int transactionId) {
    return _warehouse.showTransaction(transactionId);
  }

  public Component makeNewComponent(String componentId, int quantity) {
    return _warehouse.makeNewComponent(componentId, quantity);
  }

  public Recipe makeNewRecipe(List<Component> components) {
    return _warehouse.makeNewRecipe(components);
  }

  public DerivedProduct makeNewDerivedProduct(String productId, double price, Recipe recipe, double alpha) {
    return _warehouse.makeNewDerivedProduct(productId, price, recipe, alpha);
  }

  public String getAllNotificationsOf(Partner partner) {
    return _warehouse.getAllNotificationsOf(partner);
  }

  public void changeAvailableBalance(double value) {
    _warehouse.changeAvailableBalance(value);
  }

  public void changeAccountingBalance(double value) {
    _warehouse.changeAccountingBalance(value);
  }

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    try (ObjectOutputStream obOut = new ObjectOutputStream(new FileOutputStream(_filename))) {
      obOut.writeObject(_warehouse);
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   * @throws IOException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException, IOException {
    try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(filename))) {
      _warehouse = (Warehouse) objIn.readObject();
      this._filename = filename;
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
