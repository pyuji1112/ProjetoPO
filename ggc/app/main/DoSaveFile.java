package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

import ggc.core.WarehouseManager;
import ggc.core.exception.MissingFileAssociationException;

import java.io.IOException;
import java.io.FileNotFoundException;

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
    if (_receiver.isNewFile()) f.addStringField("Nome do ficheiro", Message.newSaveAs());
    else f.addStringField("Nome do ficheiro", Message.saveAs());
    f.parse();
    String filename = f.stringField("Nome do ficheiro");
    try {
      _receiver.saveAs(filename);
    } catch (IOException | MissingFileAssociationException e) {
      System.out.println(e);
    }
  }

}
