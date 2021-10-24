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
    List<Batch> allBatches = _receiver.getAllBatchesOrdered();

    for (Batch b: allBatches) {
      if (!productsId.contains(b.getProductsId().toLowerCase())){
        int currentStock = _receiver.getCurrentStock(b.getProductsId());
        double maxPrice = _receiver.getMaxPrice(b.getProductsId());

        _display.add(b.getProductsId() + "|");
        _display.add("" + Math.round(maxPrice) + "|");
        _display.add("" + currentStock + "|");

        productsId.add(b.getProductsId().toLowerCase());
      }
    }

    _display.display();
    //FIXME implement command
  }

}
