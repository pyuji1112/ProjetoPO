package ggc.core;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

import java.util.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import ggc.core.exception.BadEntryException;
import ggc.core.Partner;
import ggc.core.Parser;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private List<Batch> _batchesList;
  private Parser _parser;
  private List<Partner> _partnerList;

  public Warehouse() {
    this._batchesList = new ArrayList<Batch>();
    this._partnerList = new ArrayList<Partner>();
    this._parser = new Parser(this);
  }

  public List<Partner> getPartnerList() {
    List<Partner> copy = new ArrayList<Partner>();
    copy.addAll(this._partnerList);
    return copy;
  }

  public void registerPartner(Partner partner) {
    this._partnerList.add(partner);
  }

  public Partner searchPartnerById(String id) {
    for (Partner p : this._partnerList) {
      if (p.getId().toLowerCase().equals(id.toLowerCase())) return p;
    }
    return null;
  }

  public Product searchProductById(String product) {
    for (Batch b : _batchesList) {
      if (b.getProduct().getProductId().toLowerCase().equals(product.toLowerCase())) return b.getProduct();
    }
    return null;
  }

  public boolean hasPartner(String id) {
    for (Partner p : this._partnerList) {
      if (p.getId().toLowerCase().equals(id.toLowerCase())) return true;
    }
    return false;
  }

  public List<Batch> allBatchesOrdered() {
    Collections.sort(_batchesList, new BatchComparator());
    return _batchesList;
  }

  public void addBatch(Batch b) {
    this._batchesList.add(b);
  }

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


  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException {
       this._parser.parseFile(txtfile);
  }

}
