package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.List;

import ggc.core.Batch;
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

    for (Batch b: allBatches) {

      String currentProduct = b.getProduct().getProductId();

      if (!productsId.contains(currentProduct.toLowerCase())){
        
        int currentStock = _receiver.getCurrentStock(currentProduct);
        double maxPrice = _receiver.getMaxPrice(currentProduct);

        _display.add(currentProduct + "|");
        _display.add("" + Math.round(maxPrice) + "|");
        _display.add("" + currentStock + "|");
        _display.addLine(b.getProduct().showProduct());

        productsId.add(currentProduct.toLowerCase());
      }
    }

    _display.display();
  }

}
