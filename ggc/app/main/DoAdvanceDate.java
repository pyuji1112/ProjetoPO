package ggc.app.main;

import java.lang.*;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.InvalidDateException;
import ggc.core.WarehouseManager;
import ggc.core.Date;

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    addIntegerField("Date", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
    int days = integerField("Date");
    if (days > 0) _receiver.skipDays(days);
    else _display.popup("Avançar Data: Operação inválida: " + (new InvalidDateException(days)).getMessage());
  }

}
