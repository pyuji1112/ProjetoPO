package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> implements Message {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    // FIXME maybe add command fields
    addStringField("Parceiro", Message.requestPartnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    String partner = stringField("Parceiro");
    // FIXME implement command
  }

}
