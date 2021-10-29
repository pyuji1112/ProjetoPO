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
    String filename = "";
    if (_receiver.isNewFile()) {
      Form f = new Form("Nome do ficheiro");
      f.addStringField("Nome do ficheiro", Message.newSaveAs());
      f.parse();
      filename = f.stringField("Nome do ficheiro");
    }
    try {
      if (_receiver.isNewFile())
        _receiver.saveAs(filename);
      else
        _receiver.save();
    } catch (IOException | MissingFileAssociationException e) {
      System.out.println(e);
    }
  }

}
