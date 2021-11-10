package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

import java.util.ArrayList;
import java.util.List;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.Batch;
import ggc.core.Component;
import ggc.core.DerivedProduct;
import ggc.core.Partner;
import ggc.core.SimpleProduct;
import ggc.core.Product;
import ggc.core.Recipe;
import ggc.core.WarehouseManager;

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("Parceiro", Message.requestPartnerKey());
    addStringField("Produto", Message.requestProductKey());
    addRealField("Preço", Message.requestPrice());
    addIntegerField("Quantidade", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    String partnerId = stringField("Parceiro");
    Partner partner = _receiver.searchPartnerById(partnerId);
    String productId = stringField("Produto");
    double price = realField("Preço");
    int amount = integerField("Quantidade");

    if (!_receiver.hasPartner(partnerId))
      throw new UnknownPartnerKeyException(partnerId);

    if (_receiver.hasProduct(productId)) {
      Product product = _receiver.searchProductById(productId);
      _receiver.doRegisterAcquisitionTransaction(partner, product, price, amount);
    }

    Form f = new Form("Receita");
    f.addStringField("Receita", Message.requestAddRecipe());
    f.parse();
    String awnser = f.stringField("Receita");

    if (awnser.equals("Sim")) {
      addIntegerField("Número de componentes", Message.requestNumberOfComponents());
      addRealField("Agravamento", Message.requestAlpha());

      int componentsNumber = integerField("Número de componentes");
      int count = 0;
      List<Component> components = new ArrayList<>();

      while (count <= componentsNumber) {
        addStringField("Identificador do componente", Message.requestProductKey());
        addIntegerField("Quantidade do componente", Message.requestAmount());
        Component newComponent = _receiver.makeNewComponent(stringField("Identificador do Componente", integerField("Quantidade do componente")));
        components.add(newComponent);
      }
      Recipe newRecipe = _receiver.makeNewRecipe(components);
      DerivedProduct newProduct = _receiver.makeNeWDerivedProduct(productId, price, newRecipe, realField("Agravamento"));
      _receiver.doRegisterAcquisitionTransaction(partner, newProduct, price, amount);
    }

    else if (awnser.equals("Não")) {
      SimpleProduct newProduct = new SimpleProduct(productId, price);
      _receiver.doRegisterAcquisitionTransaction(partner, newProduct, price, amount);
    }

  }

}
