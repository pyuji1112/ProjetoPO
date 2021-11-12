package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Product;
import ggc.core.Partner;
import ggc.core.Batch;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    addStringField("PartnerId", Message.requestPartnerKey());
    addStringField("ProductId", Message.requestProductKey());


  }

  @Override
  public void execute() throws CommandException {
    Product product = _receiver.searchProductById(stringField("ProductId"));
    Partner partner = _receiver.searchPartnerById(stringField("PartnerId"));
    for (Batch b : _receiver.getAllBatchesOf(product)) {
      if (b.hasPartner(partner)) b.removeObserver(partner);
      else b.addObserver(partner);
    }
  }

}
