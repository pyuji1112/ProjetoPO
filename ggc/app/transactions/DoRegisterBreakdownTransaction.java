package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.WarehouseManager;
import ggc.core.Product;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("Parceiro", Message.requestPartnerKey());
    addStringField("Produto", Message.requestProductKey());
    addIntegerField("Quantidade", Message.requestAmount());
 }

  @Override
  public final void execute() throws CommandException {
    String partner = stringField("Parceiro");
    String productId = stringField("Produto");
    int amount = integerField("Quantidade");
    int currentStock = _receiver.getCurrentStock(productId);
    Product product = _receiver.searchProductById(productId);

    if (!_receiver.hasPartner(partner))
      throw new UnknownPartnerKeyException(partner);

    if (!_receiver.hasProduct(productId))
      throw new UnknownProductKeyException(productId);

    if (amount > currentStock)
      throw new UnavailableProductException(productId, amount, currentStock);

    _receiver.doRegisterBreakdownTransaction(productId, amount, partner);
    _receiver.changeAvailableBalance(product.getPrice() * amount);
    _receiver.changeAccountingBalance(product.getPrice() * amount);
  }

}
