package ggc.core;

//FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

  // FIXME define other attributes

  // FIXME define constructor(s)

  // FIXME define other methods

  public List<Batch> getBatchByPartner(String partnerId) {
    List<Batch> partnerBatches = new ArrayList<>();
    for (Batch b : _batchesList) {
      if (b.getSuplier().equals(partnerId))
        partnerBatches.add(b);
    }

    return partnerBatches;
  }

  public List<Batch> getBatchByProduct(String productId) {
    List<Batch> productBatches = new ArrayList<>();
    for (Batch b : _batchesList) {
      if (b.getProductsId().equals(productId))
        productBatches.add(b);
    }

    return productBatches;
  }

  public List<Batch> getAllBatches() {
    return Collections.unmodifiableList(_batchesList);
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
