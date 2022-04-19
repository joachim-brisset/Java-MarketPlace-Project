package project.marketplace;

import java.util.HashMap;

public class Admin extends Seller{

    private static HashMap<Product, SelledProduct> products = new HashMap<>();

    public Admin() {
        super("Marketplace");
    }

    protected HashMap<Product, SelledProduct> getProducts() {
        return products;
    }
}
