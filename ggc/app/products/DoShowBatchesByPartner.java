package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.core.Batch;
import ggc.core.WarehouseManager;

import java.util.List;
//FIXME import classes

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> implements Message{

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    //FIXME maybe add command fields
    addStringField("Parceiro", Message.requestPartnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    String partner = stringField("Parceiro");
    List<Batch> allBatches = _receiver.getAllBatchesOrdered();

    for (Batch b : allBatches) {
      if (b.getSuplier().equals(partner)) {
        _display.add(b.getProductsId() + "|");
        _display.add(b.getSuplier() + "|");
        _display.add(b.getUnitPrice() + "|");
        _display.addLine(b.getAvailableUnits());
      }
    }

    _display.display();
    //FIXME implement command
  }

}
