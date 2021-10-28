package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.util.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import ggc.core.exception.BadEntryException;
import ggc.core.Partner;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private List<Batch> _batchesList = new ArrayList<>();

  private static List<Partner> _partnerList;

  public Warehouse() {
    Warehouse._partnerList = new ArrayList<Partner>();
  }

  public static List<Partner> getPartnerList() {
    return Warehouse._partnerList;
  }

  public static void registerPartner(Partner partner) {
    Warehouse._partnerList.add(partner);
  }

  public static Partner searchPartnerById(String id) {
    for (Partner p : Warehouse._partnerList) {
      if (p.getId().equals(id.toLowerCase())) return p;
    }
    return null;
  }

  public List<Batch> AllBatchesOrdered() {
    Collections.sort(_batchesList, new BatchComparator());
    return _batchesList;
  }

  public int CurrentStock(String productId) {
    int currentStock = 0;

    for (Batch b : _batchesList) {
      String currentProduct = b.getProduct().getProductId().toLowerCase();

      if (currentProduct.equals(productId.toLowerCase())) {
        currentStock += b.getAvailableUnits();
      }
    }

    return currentStock;
  }

  public double MaxPrice(String productId) {
    double maxPrice = 0;

    for (Batch b : _batchesList) {
      String currentProduct = b.getProduct().getProductId().toLowerCase();

      if (currentProduct.equals(productId) && maxPrice < b.getUnitPrice()) {
        maxPrice = b.getUnitPrice();
      }
    }

    return maxPrice;
  }

  public boolean Partner(String partner) {
    for (Batch b : _batchesList) {
      if (b.getSuplier().toLowerCase().equals(partner.toLowerCase())) {
        return true;
      }
    }

    return false;
  }

  public boolean Product(String product) {
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
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    // FIXME implement method
  }

}
