package project.marketplace;

import java.util.HashMap;

public class ExternSeller extends Seller {

    private HashMap<Product, SelledProduct> products = new HashMap<>();

    public ExternSeller(int id, String name) {
        super(id,name);
    }

    @Override
    protected HashMap<Product, SelledProduct> getProducts() {
        return products;
    }
}