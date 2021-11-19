package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;

import java.util.List;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Acquisition;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("PartnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("PartnerId");
    Partner p = _receiver.searchPartnerById(partnerId);

    if (!_receiver.hasPartner(partnerId))
      throw new UnknownPartnerKeyException(partnerId);

    List<Acquisition> acquisitions = p.getAcquisitionsList();
    for (Acquisition a : acquisitions)
      _display.addLine(_receiver.showTransaction(a.getTransactionId()));

    _display.display();
  }

}
