package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.IOException;
import java.io.File;

import ggc.app.exception.FileOpenFailedException;
import ggc.core.WarehouseManager;
import ggc.core.exception.UnavailableFileException;

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    addStringField("Nome do ficheiro", Message.openFile());
  }

  @Override
  public final void execute() throws CommandException {
    String filename = stringField("Nome do ficheiro");
    File file = new File(filename);
    try {
      if (file.exists())
        _receiver.load(filename);
      else {
        _display.popup("Abrir: Operação inválida: " + (new FileOpenFailedException(filename)).getMessage());
      }
    } catch (UnavailableFileException u) {}
      catch (ClassNotFoundException c) {}
      catch (IOException i) {}
  }

}
