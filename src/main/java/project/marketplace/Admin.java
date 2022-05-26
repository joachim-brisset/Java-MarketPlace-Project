package project.marketplace;

import java.util.HashMap;

public class Admin extends Seller{

    private static HashMap<Product, SelledProduct> products = new HashMap<>();

    public Admin(int id) {
        super(id,"Marketplace");
    }

    /**
     * create a new contract and register it on the marketplace
     * @param seller for whom this contract is
     */
    public void createContract(ExternSeller seller) {
        Contract contract = new Contract(this, seller);
        contract.registerContract();
    }

    protected HashMap<Product, SelledProduct> getProducts() {
        return products;
    }
}
