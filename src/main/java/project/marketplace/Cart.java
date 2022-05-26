package project.marketplace;

import project.delivery.DeliveryStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

/** Represent a cart
 * TODO: remove foreach ?
 */
public class Cart {

    /** list of all product in the cart */
    private HashMap<SelledProduct, Integer> productQuantity = new HashMap<>();

    /** current price of the cart */
    private double price = 0;
    /** buyer who own the cart */
    private Buyer buyer;


    public Cart(Buyer buyer) {
        this.buyer = buyer;
    }

    public Cart(Buyer buyer, HashMap<SelledProduct, Integer> productQuantity) {
        this.buyer = buyer;
        if (productQuantity != null) this.productQuantity = productQuantity;
        computePrice();
    }

    /** compute current price of the cart
     *
     * @return price of the cart
     * @implNote internal use
     */
    private double computePrice() {
        productQuantity.forEach( (product, quantity) -> price += product.getPrice() * quantity );
        return price;
    }

    /** validate cart and create an order
     * TODO: where go the order ?
     */
    public void validateCart() {
        Order order = new Order(buyer, DeliveryStatus.WAITING, null, productQuantity);
        order.register();

        for (Entry<SelledProduct, Integer> entry : productQuantity.entrySet()) {
            entry.getKey().sell(entry.getValue());
        }

        this.price = 0;
        this.productQuantity = new HashMap<>();
    }

    /** add a product to the cart
     * @param selledProduct
     * @param quantity
     * @return
     * @throws IllegalArgumentException if quantity is negative
     */
    public double addProduct(SelledProduct selledProduct, int quantity) throws IllegalArgumentException{
        if(quantity < 0) throw new IllegalArgumentException("quantity must be positive");
        productQuantity.put(selledProduct, quantity + Optional.ofNullable(productQuantity.get(selledProduct)).orElse(0));
        price += quantity * selledProduct.getPrice();
        return price;
    }

    /** add a list of product to the cart
     * @param list
     * @return
     * @throws IllegalArgumentException if quantity is negative
     */
    public double addProduct(HashMap<SelledProduct, Integer> list) throws IllegalArgumentException{
        boolean allPositive = list.values().stream().map(x -> x >= 0).reduce(true, (x,y) -> x && y);
        if (!allPositive) throw new IllegalArgumentException("All quantity must be  positive");

        list.forEach((product, quantity) -> addProduct(product, quantity));
        return price;
    }

    /** Modify a product quantity
     * @param selledProduct
     * @param quantity
     * @return
     * @throws IllegalArgumentException if quantity is negative
     */
    public double editProduct(SelledProduct selledProduct, int quantity) throws IllegalArgumentException {
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

    /** Modify a list of product quantity
     * @param list
     * @return
     */
    public double editProduct(HashMap<SelledProduct, Integer> list) {
        //TODO: check null ? if(Objects.isNull(list)) throw new IllegalArgumentException("list must be initialized");
        boolean allPositive = list.values().stream().map(x -> x >= 0).reduce(true, (x,y) -> x && y);
        if (!allPositive) throw new IllegalArgumentException("All quantity must be positive or null");

        list.forEach((product, quantity) -> editProduct(product, quantity));
        return price;
    }

    /** Remove a product
     * @param selledProduct
     * @return
     */
    public double removeProduct(SelledProduct selledProduct) {
        editProduct(selledProduct, 0);
        return price;
    }

    /** ordered product unmodifiable getter */
    public Map<SelledProduct, Integer> getProductList() {
        return Collections.unmodifiableMap(productQuantity);
    }

    /** price getter */
    public double getPrice() {
        return price;
    }
    /** buyer getter */
    public Buyer getBuyer() {
        return buyer;
    }
    /** buyer setter */
    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }
}
