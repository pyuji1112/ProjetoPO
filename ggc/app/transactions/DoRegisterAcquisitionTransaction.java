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

    else if (_receiver.hasProduct(productId)) {
      Product product = _receiver.searchProductById(productId);
      _receiver.doRegisterAcquisitionTransaction(partner, product, price, amount);
    }
    else {
      Form f = new Form("Receita");
      f.addStringField("Receita", Message.requestAddRecipe());
      f.parse();
      String awnser = f.stringField("Receita");

      if (awnser.equals("s")) {
        Form fs = new Form("Sim");
        fs.addIntegerField("Número de componentes", Message.requestNumberOfComponents());
        fs.addRealField("Agravamento", Message.requestAlpha());
        fs.parse();
        int nComponents = fs.integerField("Número de componentes");
        Double alpha = fs.realField("Agravamento");

        int count = 0;
        List<Component> components = new ArrayList<>();

        while (count <= nComponents) {
          Form fc = new Form("Components");
          fc.addStringField("Identificador do componente", Message.requestProductKey());
          fc.addIntegerField("Quantidade do componente", Message.requestAmount());
          fc.parse();
          String idComponent = fc.stringField("Identificador do componente");
          if (!_receiver.hasProduct(idComponent)) throw new UnknownProductKeyException(idComponent);
          int qComponent = fc.integerField("Quantidade do componente");
          Component newComponent = _receiver.makeNewComponent(idComponent, qComponent);
          components.add(newComponent);
          count++;
        }
        Recipe newRecipe = _receiver.makeNewRecipe(components);
        DerivedProduct newProduct = _receiver.makeNewDerivedProduct(productId, price, newRecipe, alpha);
        _receiver.doRegisterAcquisitionTransaction(partner, newProduct, price, amount);
        _receiver.changeAvailableBalance(price);
        _receiver.changeAccountingBalance(price);
      }

      else if (awnser.equals("n")) {
        SimpleProduct newProduct = new SimpleProduct(productId, price);
        _receiver.doRegisterAcquisitionTransaction(partner, newProduct, price, amount);
        _receiver.changeAvailableBalance(price);
        _receiver.changeAccountingBalance(price);
      }
    }

  }

}
