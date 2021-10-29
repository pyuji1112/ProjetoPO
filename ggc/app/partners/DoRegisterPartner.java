package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.DuplicatePartnerKeyException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.Warehouse;

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    addStringField("id", Message.requestPartnerKey());
    addStringField("name", Message.requestPartnerName());
    addStringField("address", Message.requestPartnerAddress());
  }

  @Override
  public void execute() throws CommandException {
    String id = stringField("id");
    String name = stringField("name");
    String address = stringField("address");
    Partner p = new Partner(id, name, address);
    if (!_receiver.partnerExists(p))
      _receiver.registerPartner(p);
    else _display.popup("Registar parceiro: " + (new DuplicatePartnerKeyException(id)));
  }

}
