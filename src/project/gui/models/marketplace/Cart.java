package project.gui.models.marketplace;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import project.gui.models.delivery.DeliveryStatus;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Cart {

	private final ObjectProperty<Buyer> owner = new SimpleObjectProperty<>(null);
	private ObservableList<CartedProduct> cartedProducts = FXCollections.observableArrayList(
			item -> new Observable[] {
					item.selledProductProperty(),
					item.quantityProperty()}
	);


	private final DoubleBinding price = Bindings.createDoubleBinding(
			() -> cartedProducts.stream().mapToDouble( item -> item.getQuantity() * item.getSelledProduct().getPrice()).sum()
	);

	public Cart(Buyer owner, ArrayList<CartedProduct> cartedProducts) {
		this.owner.set(owner);
		if (cartedProducts != null) this.cartedProducts.addAll(cartedProducts);
	}

	public Buyer getOwner() {
		return owner.get();
	}
	public ObjectProperty<Buyer> ownerProperty() {
		return owner;
	}
	public void setOwner(Buyer owner) {
		this.owner.set(owner);
	}

	public ObservableList<CartedProduct> getCartedProducts() {
		return cartedProducts;
	}
	public void setCartedProducts(ObservableList<CartedProduct> cartedProducts) {
		this.cartedProducts = cartedProducts;
	}

	public double getPrice() {
		return price.get();
	}
	public DoubleBinding priceProperty() {
		return price;
	}

	/* ========= move code elsewhere ========= */

	public boolean canBuy() {
		return cartedProducts.stream().map( item -> item.getSelledProduct().canSell(item.getQuantity())).reduce( true, (x,y) -> x && y );
	}

	/** validate cart and create an order
	 *
	 */
//	public Order validateCart() {
//		if (!canBuy()) return null;
//
//		for (CartedProduct cartedProduct : cartedProducts) {
//			try {
//				cartedProduct.getSelledProduct().sell(cartedProduct.getQuantity());
//			} catch (SelledProduct.SelledProductNotEnoughStock ignored) {}
//		}
//
//		Order order = new Order(this);
//		order.register();
//
//		return order;
//	}
//	/*
//	/** add a product to the cart
//	 * @param selledProduct
//	 * @param quantity
//	 * @return
//	 * @throws IllegalArgumentException if quantity is negative
//	 */
//	public double addProduct(SelledProduct selledProduct, int quantity) throws IllegalArgumentException{
//		if(quantity < 0) throw new IllegalArgumentException("quantity must be positive");
//		cartedProducts.
//
//		CartedProduct.put(selledProduct, quantity + Optional.ofNullable(productQuantity.get(selledProduct)).orElse(0));
//		return price;
//	}
//
//	/** add a list of product to the cart
//	 * @param list
//	 * @return
//	 * @throws IllegalArgumentException if quantity is negative
//	 */
//	public double addProduct(HashMap<SelledProduct, Integer> list) throws IllegalArgumentException{
//		boolean allPositive = list.values().stream().map(x -> x >= 0).reduce(true, (x,y) -> x && y);
//		if (!allPositive) throw new IllegalArgumentException("All quantity must be  positive");
//
//		list.forEach((product, quantity) -> addProduct(product, quantity));
//		return price;
//	}
//
//	/** Modify a product quantity
//	 * @param selledProduct
//	 * @param quantity
//	 * @return
//	 * @throws IllegalArgumentException if quantity is negative
//	 */
//	public double editProduct(SelledProduct selledProduct, int quantity) throws IllegalArgumentException {
//		if(quantity < 0) throw new IllegalArgumentException("quantity must be positive or nul");
//		int quantityDelta = quantity - Optional.ofNullable(productQuantity.get(selledProduct)).orElse(0);
//
//		if(quantity != 0) {
//			productQuantity.put(selledProduct, quantity);
//		} else {
//			productQuantity.remove(selledProduct);
//		}
//		price += quantityDelta * selledProduct.getPrice();
//		return price;
//	}
//
//	/** Modify a list of product quantity
//	 * @param list
//	 * @return
//	 */
//	public double editProduct(HashMap<SelledProduct, Integer> list) {
//		//TODO: check null ? if(Objects.isNull(list)) throw new IllegalArgumentException("list must be initialized");
//		boolean allPositive = list.values().stream().map(x -> x >= 0).reduce(true, (x,y) -> x && y);
//		if (!allPositive) throw new IllegalArgumentException("All quantity must be positive or null");
//
//		list.forEach((product, quantity) -> editProduct(product, quantity));
//		return price;
//	}
//
//	/** Remove a product
//	 * @param selledProduct
//	 * @return
//	 */
//	public double removeProduct(SelledProduct selledProduct) {
//		editProduct(selledProduct, 0);
//		return price.get();
//	}
}
