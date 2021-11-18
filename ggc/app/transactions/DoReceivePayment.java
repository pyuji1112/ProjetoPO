package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Transaction;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    addIntegerField("Id", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    Transaction transaction = _receiver.searchTransactionById(integerField("Id"));
    transaction.pay(_receiver.getDate());
    _receiver.changeAvailableBalance(transaction.getValue());
  }

}
