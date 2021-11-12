package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.Sale;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
    addStringField("PartnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("PartnerId");
    Partner p = _receiver.searchPartnerById(partnerId);

    for (Sale s : p.getSalesList())
      _display.addLine(s.toString());
      
    _display.display();
  }

}
