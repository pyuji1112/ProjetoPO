package ggc.core;

//FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse = new Warehouse();
  private List<Batch> _batchesList = new ArrayList<>();

  /** Date tracking **/
  private Date _date;

  // FIXME define other attributes

  // FIXME define constructor(s)

  // FIXME define other methods

  public List<Batch> getAllBatchesOrdered() {
    Collections.sort(_batchesList, new BatchComparator());
    return _batchesList;
  }

  public int getCurrentStock(String productId) {
    int currentStock = 0;

    for (Batch b : _batchesList) {
      String currentProduct = b.getProduct().getProductId().toLowerCase();

      if (currentProduct.equals(productId.toLowerCase())) {
        currentStock += b.getAvailableUnits();
      }
    }

    return currentStock;
  }

  public double getMaxPrice(String productId) {
    double maxPrice = 0;

    for (Batch b : _batchesList) {
      String currentProduct = b.getProduct().getProductId().toLowerCase();

      if (currentProduct.equals(productId) && maxPrice < b.getUnitPrice()) {
        maxPrice = b.getUnitPrice();
      }
    }

    return maxPrice;
  }

  public boolean getPartner(String partner) {
    for (Batch b : _batchesList) {
      if (b.getSuplier().toLowerCase().equals(partner.toLowerCase())) {
        return true;
      }
    }

    return false;
  }

  public boolean getProduct(String product) {
    for (Batch b : _batchesList) {
      if (b.getProduct().getProductId().toLowerCase().equals(product.toLowerCase())) {
        return true;
      }
    }

    return false;
  }

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    // FIXME implement serialization method
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
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException {
    // FIXME implement serialization method
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
