package project.marketplace;

import java.util.*;

public class Cart {

    private HashMap<SelledProduct, Integer> productQuantity = new HashMap<>();
    private double price = 0;
    private Buyer buyer;

    public Cart(Buyer buyer) {
        this.buyer = buyer;
    }

    public Cart(Buyer buyer, HashMap<SelledProduct, Integer> productQuantity) {
        this.buyer = buyer;
        if (!Objects.isNull(productQuantity)) this.productQuantity = productQuantity;
        computePrice();
    }

    private double computePrice() {
        productQuantity.forEach( (product, quantity) -> price += product.getPrice() * quantity );
        return price;
    }

    public void validateCart() {
        //TODO: cart to order
    }


    public double addProduct(SelledProduct selledProduct, int quantity) {
        if(quantity < 0) throw new IllegalArgumentException("quantity must be positive");
        productQuantity.put(selledProduct, quantity + Optional.ofNullable(productQuantity.get(selledProduct)).orElse(0));
        price += quantity * selledProduct.getPrice();
        return price;
    }
    public double addProduct(HashMap<SelledProduct, Integer> list) {
        boolean allPositive = list.values().stream().map(x -> x >= 0).reduce(true, (x,y) -> x && y);
        if (!allPositive) throw new IllegalArgumentException("All quantity must be  positive");

        list.forEach((product, quantity) -> addProduct(product, quantity));
        return price;
    }
    public double editProduct(SelledProduct selledProduct, int quantity) {
        if(quantity < 0) throw new IllegalArgumentException("quantity must be positive or nul");
        int quantityDelta = quantity - Optional.ofNullable(productQuantity.get(selledProduct)).orElse(0);

        if(quantity != 0) {
            productQuantity.put(selledProduct, quantity);
        } else {
            productQuantity.remove(selledProduct);
        }
        price += quantityDelta * selledProduct.getPrice();
        return price;
    }
    public double editProduct(HashMap<SelledProduct, Integer> list) {
        //TODO: check null ? if(Objects.isNull(list)) throw new IllegalArgumentException("list must be initialized");
        boolean allPositive = list.values().stream().map(x -> x >= 0).reduce(true, (x,y) -> x && y);
        if (!allPositive) throw new IllegalArgumentException("All quantity must be positive or null");

        list.forEach((product, quantity) -> editProduct(product, quantity));
        return price;
    }
    public double removeProduct(SelledProduct selledProduct) {
        editProduct(selledProduct, 0);
        return price;
    }

    public Map<SelledProduct, Integer> getProductList() {
        return Collections.unmodifiableMap(productQuantity);
    }

    public double getPrice() {
        return price;
    }
    public Buyer getBuyer() {
        return buyer;
    }
    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }
}
