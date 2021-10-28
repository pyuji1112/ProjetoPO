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

      String currentProduct = b.getProduct().getProductId().toUpperCase();

      if (!productsId.contains(currentProduct.toLowerCase())){

        int currentStock = _receiver.getCurrentStock(currentProduct);
        double maxPrice = _receiver.getMaxPrice(currentProduct);

        _display.addLine(currentProduct.toUpperCase() + "|" + Math.round(b.getUnitPrice()) + "|" + currentStock);
      }
    }

    _display.display();
  }

}
