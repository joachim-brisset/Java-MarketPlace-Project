package project.gui.controllers.buyer.mainmenu;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.buyer.productexplorer.AddProductSceneController;
import project.gui.models.marketplace.*;


/**
 * @implNote must specify a cart through setCart()
 */
public class CartComponentController {
	
	/**
	 * view label containing cart price
	 */
	@FXML private Label lblPrice;
	/**
	 * view label containing cart's article's quantity
	 */
	@FXML private Label articleQuantityLbl;

	/**
	 *  view node container for CartedProduct list
	 */
	@FXML private ListView<CartedProduct> cartedProductContainer;

	private ObjectProperty<Cart> cart = new SimpleObjectProperty<>();
	
	/**
	 * constructor
	 */
	public CartComponentController() {}
	
	/** initialize view and controller
	 * this function is call after FXML loading and variable injection
	 */
	@FXML private void initialize() {
		cart.addListener( (obj) -> {
			cartedProductContainer.setItems(cart.get() == null ? null : cart.get().getCartedProducts());
			lblPrice.setText("0");
			if (cart.get() != null) lblPrice.textProperty().bind(cart.get().priceProperty().asString());

			lblPrice.textProperty().bind(Bindings.createDoubleBinding( () -> cart.get().getCartedProducts().stream().mapToDouble( item -> item.getQuantity() * item.getSelledProduct().getPrice()).sum(), cart.get().getCartedProducts()).asString());

		});
		cartedProductContainer.setCellFactory( x -> new CartedProductComponentController());
	}
	
	/**
	 * handle function for "ajouter un produit"'s button
	 * open a new window listing all products to add desired products to cart
	 */
	@FXML private void handleAddNewProduct(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/productexplorer/ProductExplorer_Scene.fxml"));
			AnchorPane root = loader.load();
			AddProductSceneController controller = loader.getController();
			controller.setCart(cart.get());

			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Stage newStage = new Stage();

			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(currentStage);
			newStage.setScene(new Scene(root));
			newStage.setResizable(false);
			newStage.showAndWait();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}

	/**
	 * handle function for "Effacer"'s button
	 * remove all product in cart.
	 */
	@FXML public void handleRemoveAll() {
		if (cart.get() != null) cart.get().getCartedProducts().clear();
	}

	/**
	 * handle function for "valider"'s button
	 * create an order from cart and clear cart.
	 */
	@FXML public void handleValidate() {
		if (cart.get() == null || cart.get().getCartedProducts().isEmpty()) return;
		Order order = new Order(cart.get());

		cart.get().getOwner().getOrders().add(order);
		Order.register(order); // simplify this code

		cart.get().getCartedProducts().clear();
	}

	public ObjectProperty<Cart> cartProperty() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart.set(cart);
	}



	/**
	 * Controller for each carted product in the list
	 */
	private class CartedProductComponentController extends ListCell<CartedProduct> {
		
		@FXML private Label lblProductDesignation;
		@FXML private Label lblProductReference;
		@FXML private Label lblProductDescription;
		@FXML private Label lblSellerName;
		@FXML private Label lblQuantity;
		@FXML private Label lblPrice;
		@FXML private Label lblShippingTime;
		
		private CartedProduct product;
		
		public CartedProductComponentController() {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/mainmenu/CartedProduct_Component.fxml"));
				loader.setController(this);
				loader.setRoot(this);
				loader.load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		protected void updateItem(CartedProduct cartedProduct, boolean empty) {
			super.updateItem(cartedProduct, empty);
			this.product = cartedProduct;
			
			if (empty || cartedProduct == null) {
				setText(null);
				setContentDisplay(ContentDisplay.TEXT_ONLY);
			} else {
				lblProductDesignation.setText(cartedProduct.getSelledProduct().getProduct().getDesignation());
				lblProductDescription.setText(cartedProduct.getSelledProduct().getProduct().getDescription());
				
				lblSellerName.setText(cartedProduct.getSelledProduct().getSeller().getName());
				
				lblQuantity.setText("" + cartedProduct.getQuantity());
				lblPrice.setText("" +cartedProduct.getSelledProduct().getPrice());
				lblShippingTime.setText("" + cartedProduct.getSelledProduct().getShippingTime().toDays());
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			}
		}

		/**
		 * handle function for "Supprimer"'s button
		 * remove this product from the cart
		 */
		@FXML private void handleRemove() {
			cart.get().getCartedProducts().remove(product);
		}

		/**
		 * handle function for "edit"'s button
		 * open a new window to edit product carted (Seller, quantity...)
		 */
		@FXML private void handleEdit() {
			//TODO: PRIORITY LOW - finish
			Main.uninplemented(null);
		}
	}
}
