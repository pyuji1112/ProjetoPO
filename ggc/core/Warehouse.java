package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.util.*;
import java.io.Serializable;
import java.io.IOException;
import ggc.core.exception.BadEntryException;
import ggc.core.Partner;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

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

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    // FIXME implement method
  }

}
