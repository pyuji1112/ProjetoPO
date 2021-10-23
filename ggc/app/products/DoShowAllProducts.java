package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.List;

import ggc.core.Batch;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Show all products.
 */
class DoShowAllProducts extends Command<WarehouseManager> {

  DoShowAllProducts(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    List<String> productsId = new ArrayList<>();
    List<Batch> allBatches = _receiver.getAllBatchesOrderedByPrice();

    for (Batch b: allBatches) {
      if (!productsId.contains(b.getProductsId())){
        _display.add(b.getProductsId() + "|");
        _display.add(b.getUnitPrice() + "|");
        _display.add(b.getAvailableUnits() + "|");
      }
    }

    _display.display();
    //FIXME implement command
  }

}
