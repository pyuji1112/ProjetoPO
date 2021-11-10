package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;

import ggc.core.Batch;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    addRealField("Valor limite", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    List<Batch> allBatches = _receiver.getAllBatchesOrdered();
    Double limitValue = realField("Valor limite");

    for (Batch b : allBatches) {
      if (b.getUnitPrice() < limitValue) {
        _display.addLine(b.showBatch());
      }
    }

    _display.display();
  }
}
