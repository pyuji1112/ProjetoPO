package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;

import ggc.core.Batch;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Show available batches.
 */
class DoShowAvailableBatches extends Command<WarehouseManager> {

  DoShowAvailableBatches(WarehouseManager receiver) {
    super(Label.SHOW_AVAILABLE_BATCHES, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    List<Batch> allBatches = _receiver.getAllBatchesOrdered();

    for (Batch b : allBatches) {
      _display.addLine(b.showBatch());
    }

    _display.display();
  }

}
