package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.Sale;
import ggc.core.Date;
import ggc.app.exception.UnavailableProductException;

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
    Partner partner = _receiver.searchPartnerById(stringField("Partner"));
    Date limitDate = new Date(integerField("LimitDate"));
    Product product  = _receiver.searchProductById(stringField("Product"));
    int quantity = integerField("Quantity");
    int currentStock = _receiver.getCurrentStock(product.getProductId());

    if (quantity > currentStock)
      throw new UnavailableProductException(product.getProductId(), quantity, currentStock);


    Sale sale = new Sale(product, partner, quantity, limitDate);
    _receiver.doRegisterSaleTransaction(sale);
    _receiver.changeAccountingBalance(partner.valueToPay(sale));

  }

}
