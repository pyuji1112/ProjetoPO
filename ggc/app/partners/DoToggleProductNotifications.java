package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Product;
import ggc.core.Partner;
import ggc.core.Batch;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnknownPartnerKeyException;

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
    String productId = stringField("ProductId");
    String partnerId = stringField("PartnerId");
    Product product = _receiver.searchProductById(productId);
    Partner partner = _receiver.searchPartnerById(partnerId);
    if (!_receiver.hasProduct(productId)) throw new UnknownProductKeyException(productId);
    if (!_receiver.hasPartner(partnerId)) throw new UnknownPartnerKeyException(partnerId);
    for (Batch b : _receiver.getAllBatchesOf(product)) {
      if (b.hasPartner(partner)) b.removeObserver(partner);
      else b.addObserver(partner);
    }
  }

}
