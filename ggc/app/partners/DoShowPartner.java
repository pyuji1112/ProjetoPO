package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Partner;
import ggc.core.Warehouse;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("Id", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String id = stringField("Id");
    Partner p = _receiver.searchPartnerById(id);

    if (!_receiver.hasPartner(id))
      throw new UnknownPartnerKeyException(id);

    _display.popup(p.toString());
  }

}
