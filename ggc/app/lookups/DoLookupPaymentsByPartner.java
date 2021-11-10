package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("Parceiro", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partner = stringField("Parceiro");

    if (!_receiver.hasPartner(partner)) {
      throw new UnknownPartnerKeyException(partner);
    }

    
    //FIXME implement command
  }

}
