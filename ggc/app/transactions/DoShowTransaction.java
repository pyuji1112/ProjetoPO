package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    addIntegerField("Id da transação", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    int transactionId = integerField("Id da tranação");
    _display.popup(_receiver.showTransaction(transactionId));
  }

}
