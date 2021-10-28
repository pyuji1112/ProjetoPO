package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.app.exception.UnknownProductKeyException;
import ggc.core.WarehouseManager;
import ggc.core.Batch;

import java.util.ArrayList;
import java.util.List;

//FIXME import classes

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> implements Message {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    // FIXME maybe add command fields
    addStringField("Produto", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException {
    String product = stringField("Produto");
    List<Batch> allBatches = _receiver.getAllBatchesOrdered();

    if (!_receiver.hasPartner(product)) {
      throw new UnknownProductKeyException(product);
    }

    for (Batch b : allBatches) {
      if (b.getProduct().getProductId().equals(product)) {
        _display.addLine(b.showBatch());
      }
    }

    _display.display();
    // FIXME implement command
  }

}
