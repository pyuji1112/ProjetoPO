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
    addStringField("Produto", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException {
    String productId = stringField("Produto");
    List<Batch> allBatches = _receiver.getAllBatchesOrdered();

    if (!_receiver.hasPartner(productId)) {
      throw new UnknownProductKeyException(productId);
    }

    for (Batch b : allBatches)
      _display.addLine(_receiver.searchProductById(productId));

    _display.display();
  }

}
