package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.Sale;
import ggc.core.Date;
import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;

/**
 *
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    addStringField("Partner", Message.requestPartnerKey());
    addIntegerField("LimitDate", Message.requestPaymentDeadline());
    addStringField("Product", Message.requestProductKey());
    addIntegerField("Quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    String partnerId = stringField("Partner");
    String productId = stringField("Product");
    Date limitDate = new Date(integerField("LimitDate"));
    int quantity = integerField("Quantity");


    if (!_receiver.hasPartner(partnerId))
      throw new UnknownPartnerKeyException(partnerId);

    if (!_receiver.hasProduct(productId))
      throw new UnknownProductKeyException(productId);

    Partner partner = _receiver.searchPartnerById(partnerId);
    Product product  = _receiver.searchProductById(productId);
    int currentStock = _receiver.getCurrentStock(productId);

    if (quantity > currentStock)
      throw new UnavailableProductException(productId, quantity, currentStock);

    Sale sale = new Sale(product, partner, quantity, limitDate);
    sale.setValue(product.getPrice() * quantity);
    _receiver.doRegisterSaleTransaction(sale);
    _receiver.changeAccountingBalance(partner.valueToPay(sale));

  }

}
