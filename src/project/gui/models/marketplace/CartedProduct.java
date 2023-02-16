package project.gui.models.marketplace;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CartedProduct {
	
	private final ObjectProperty<SelledProduct> selledProduct = new SimpleObjectProperty<>(null);
	private final IntegerProperty quantity = new SimpleIntegerProperty(0);
	
	public CartedProduct(SelledProduct selledProduct, int quantity) {
		this.selledProduct.set(selledProduct);
		this.quantity.set(quantity);
	}

	public SelledProduct getSelledProduct() {
		return selledProduct.get();
	}
	public ObjectProperty<SelledProduct> selledProductProperty() {
		return selledProduct;
	}
	public void setSelledProduct(SelledProduct selledProduct) {
		this.selledProduct.set(selledProduct);
	}

	public int getQuantity() {
		return quantity.get();
	}
	public IntegerProperty quantityProperty() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}



	private static final String SEPARATOR = "%";

	public static ArrayList<CartedProduct> unstringSerialize(String serialString) throws ParseException {
		if (!serialString.startsWith("#{") || !serialString.endsWith("}#") || serialString.length() < 5) throw new ParseException("Invalide delimiter", 1);
		final String[] fields = serialString.substring(2, serialString.length()-3).split(SEPARATOR);

		final ArrayList<CartedProduct> cartedProducts = new ArrayList<>();

		for (String field : fields) {
			String[] data = field.split("x");
			cartedProducts.add(new CartedProduct(SelledProduct.selledProductById.get(Integer.parseInt(data[0])), Integer.parseInt(data[1])));
		}

		return cartedProducts;
	}
	public static String stringSerialize(List<CartedProduct> cartedProducts) {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("#{");

		for (CartedProduct cartedproduct : cartedProducts) {
			buffer.append(cartedproduct.getSelledProduct().getId()).append("x").append(cartedproduct.getQuantity());
		}

		buffer.append("}#");
		return buffer.toString();
	}
}
