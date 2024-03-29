package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.List;

import ggc.core.Batch;
import ggc.core.Partner;
import ggc.core.WarehouseManager;

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

    for (Batch b : allBatches) {
      String currentProductId = b.getProduct().getProductId();

      if (!productsId.contains(currentProductId.toLowerCase())) {
        _display.addLine(_receiver.showProduct(currentProductId));
        productsId.add(currentProductId.toLowerCase());
      }
    }
    _display.display();
  }

}
