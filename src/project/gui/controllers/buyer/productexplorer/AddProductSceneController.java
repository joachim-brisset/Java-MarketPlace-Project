package project.gui.controllers.buyer.productexplorer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import project.gui.Main;
import project.gui.controllers.buyer.mainmenu.CartComponentController;
import project.gui.models.marketplace.Cart;
import project.gui.models.marketplace.CartedProduct;
import project.gui.models.marketplace.SelledProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

/** Controller */
public class AddProductSceneController {
	/** node container for SelledProduct list */
	@FXML private ListView<SelledProduct> selledProductContainer;
	
	private ObservableList<SelledProduct> products = SelledProduct.getSelledProductList();
	private ObjectProperty<Cart> cart = new SimpleObjectProperty<>(new Cart(null,null));

	/** constructor */
	public AddProductSceneController() { }

	/** initialize view and controller
	 * this function is call after FXML loading and variable injection
	 */
	@FXML private void initialize() {
		cart.addListener( (obj) -> selledProductContainer.setItems(products) );
		selledProductContainer.setCellFactory( x -> new SelledProductComponentController());
	}


	public ObservableList<SelledProduct> getProducts() {
		return products;
	}
	public void setProducts(ObservableList<SelledProduct> products) {
		this.products = products;
		selledProductContainer.setItems(products);
	}

	public Cart getCart() {
		return cart.get();
	}
	public ObjectProperty<Cart> cartProperty() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart.set(cart);
	}

	/** internal controller for SelledProduct list item */
	public class SelledProductComponentController extends ListCell<SelledProduct> {

		@FXML private Label lblProductDesignation;
		@FXML private Label lblProductReference;
		@FXML private Label lblProductDescription;
		@FXML private Label lblSellerName;
		@FXML private TextField inputQuantity;
		@FXML private Label lblPrice;
		@FXML private Label lblShippingPrice;
		@FXML private Label lblShippingTime;

		private SelledProduct product;

		public SelledProductComponentController() {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/productexplorer/SelledProduct_Component.fxml"));
				loader.setController(this);
				loader.setRoot(this);
				loader.load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void updateItem(SelledProduct selledProduct, boolean empty) {
			this.product = selledProduct;
			super.updateItem(selledProduct, empty);

			if (empty || selledProduct == null) {
				setText(null);
				setContentDisplay(ContentDisplay.TEXT_ONLY);
			} else {
				lblProductDesignation.setText(selledProduct.getProduct().getDesignation());
				lblProductReference.setText(selledProduct.getProduct().getReference());
				lblProductDescription.setText(selledProduct.getProduct().getDescription());

				lblSellerName.setText(selledProduct.getSeller().getName());

				lblPrice.setText("" +selledProduct.getPrice());
				lblShippingPrice.setText("" + selledProduct.getShippingPrice());
				lblShippingTime.setText("" + selledProduct.getShippingTime().toDays());
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			}
		}
		
		@FXML private void handleAdd() {
			try {
				int quantity = Integer.parseInt(inputQuantity.getText());
				cart.get().getCartedProducts().add(new CartedProduct(product, quantity));
			} catch (NumberFormatException e) {
				System.out.println("Veuillez entrez un nombre !");
			};
		}
		
	}
}
