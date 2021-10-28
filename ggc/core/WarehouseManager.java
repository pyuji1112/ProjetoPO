package ggc.core;

import java.io.Serializable;
import java.util.List;
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

  /** Date tracking **/
  private Date _date;

  public boolean isNewFile() {
    return _filename.equals("");
  }

  public List<Batch> getAllBatchesOrdered() {
    return _warehouse.AllBatchesOrdered();
  }

  public int getCurrentStock(String productId) {
    return _warehouse.CurrentStock(productId);
  }

  public double getMaxPrice(String productId) {
    return _warehouse.MaxPrice(productId);
  }

  public boolean hasPartner(String partner) {
    return _warehouse.Partner(partner);
  }

  public boolean hasProduct(String product) {
    return _warehouse.Product(product);
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
      objIn.readObject();
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
