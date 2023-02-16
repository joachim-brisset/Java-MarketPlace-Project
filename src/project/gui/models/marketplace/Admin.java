package project.gui.models.marketplace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Date;

public class Admin extends Seller {

    private static final String NAME = "Marketplace";

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<SelledProduct> selledProducts = FXCollections.observableArrayList();

    public Admin(int id, int idAsSeller, String firstName, String lastName) {
        super(id, idAsSeller, firstName, lastName, NAME);
    }

    /**
     * create a new contract and register it on the marketplace
     * @param seller for whom this contract is
     */
    public Contract createContract(ExternSeller seller, double commissiionFee, Date expirationDate) {
        Contract contract = new Contract(this, seller, commissiionFee, expirationDate);
        contract.register();
        return contract;
    }

    @Override
    protected ObservableList<Product> getProductsList() {
        return products;
    }

    @Override
    protected ObservableList<SelledProduct> getSelledProductsList() {
        return selledProducts;
    }
}
