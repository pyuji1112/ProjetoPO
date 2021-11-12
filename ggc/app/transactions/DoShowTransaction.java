package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownTransactionKeyException;

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
    int transactionId = integerField("Id da transação");
    if (_receiver.hasTransaction(transactionId))
      _display.popup(_receiver.showTransaction(transactionId));
    else throw new UnknownTransactionKeyException(transactionId);
  }

}
