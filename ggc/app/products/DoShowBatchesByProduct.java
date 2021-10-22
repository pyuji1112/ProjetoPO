package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

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
    List<Batch> allBatches = _receiver.getAllBatches();

    for (Batch b : allBatches) {
      if (b.getProductsId().equals(product)) {
        _display.add(b.getProductsId() + "|");
        _display.add(b.getSuplier() + "|");
        _display.add(b.getUnitPrice() + "|");
        _display.addLine(b.getAvailableUnits());
      }
    }

    _display.display();
    // FIXME implement command
  }

}
