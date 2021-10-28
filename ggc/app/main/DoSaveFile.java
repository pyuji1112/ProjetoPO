package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    Form f = new Form("Nome do ficheiro");
    f.addStringField("Nome do ficheiro", Message.saveAs());

    f.parse();

    String filename = f.stringField("Nome do ficheiro");
    //_receiver.saveAs(filename);
    
    // FIXME implement command and create a local Form
  }

}
