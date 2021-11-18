package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.Sale;
import ggc.core.Acquisition;

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("partnerId");

    if (!_receiver.hasPartner(partnerId))
      throw new UnknownPartnerKeyException(partnerId);

    Partner p = _receiver.searchPartnerById(partnerId);
    for (Sale s : p.getSalesList()) {
      if (s.isPaid()) _display.addLine(_receiver.showTransaction(s.getTransactionId()));
    }
    
    _display.display();

  }

}
