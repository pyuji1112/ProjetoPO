package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Batch;
import ggc.core.WarehouseManager;

import java.util.List;
//FIXME import classes

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> implements Message {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("Parceiro", Message.requestPartnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    String partnerId = stringField("Parceiro");
    List<Batch> allBatches = _receiver.getAllBatchesOrdered();

    if (!_receiver.hasPartner(partnerId)) {
      throw new UnknownPartnerKeyException(partnerId);
    }

    for (Batch b : allBatches) {
      if (b.getSuplier().equals(_receiver.searchPartnerById(partnerId))) _display.addLine(b.toString());
    }

    _display.display();
  }

}
