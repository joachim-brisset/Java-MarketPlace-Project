package project.gui.controllers.seller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.models.marketplace.Product;
import project.gui.models.marketplace.SelledProduct;
import project.gui.models.marketplace.Seller;

import java.io.IOException;
import java.util.stream.Collectors;

public class MenuController {

    private static class SelledProductController extends ListCell<SelledProduct> {

        @FXML private Label lblPrdRef;
        @FXML private Label lblPrdDesignation;
        @FXML private Label lblPrdDesc;
        @FXML private Label lblStock;
        @FXML private Label lblPrice;
        @FXML private Label lblShipPrice;
        @FXML private Label lblShipTime;

        private SelledProduct selledProduct;

        public SelledProductController() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "seller/product.fxml"));
                loader.setController(this);
                loader.setRoot(this);
                loader.load();
            } catch (IOException e) { throw new RuntimeException(e); }
        }

        @Override
        protected void updateItem(SelledProduct selledProduct, boolean empty) {
            super.updateItem(selledProduct, empty);
            this.selledProduct = selledProduct;

            if (empty || selledProduct == null) {
                setText(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            } else {
                lblPrdRef.textProperty().bind(selledProduct.getProduct().referenceProperty());
                lblPrdDesignation.textProperty().bind(selledProduct.getProduct().designationProperty());
                lblPrdDesc.textProperty().bind(selledProduct.getProduct().descriptionProperty());

                lblStock.textProperty().bind(selledProduct.stockProperty().asString());
                lblPrice.textProperty().bind(selledProduct.priceProperty().asString());
                lblShipPrice.textProperty().bind(selledProduct.shippingPriceProperty().asString());
                lblShipTime.textProperty().bind(selledProduct.shippingTimeProperty().asString());

                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
        
        @FXML private void handleEdit(ActionEvent event) {
        	try {
	        	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            Stage newStage = new Stage();
	
	            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "seller/edit.fxml"));
	            BorderPane root = (BorderPane) loader.load();
	            EditController controller = loader.getController();
	
	            controller.setSelledProduct(selledProduct);
	            controller.setCurrentStage(newStage);
	
	            newStage.initModality(Modality.WINDOW_MODAL);
	            newStage.initOwner(currentStage);
	            newStage.setScene(new Scene(root));
	            newStage.setResizable(false);
	            newStage.showAndWait();
	            
        	} catch (Exception e) { throw new RuntimeException(e); }
        }
        
        @FXML private void handleDelete(ActionEvent event) {
        	Main.uninplemented(null);
        }
    }
    
    
	@FXML private Label lblTotalrevenu;
    @FXML private ListView<SelledProduct> productContainer;
    @FXML private ListView<SelledProduct> revenuContainer;

    private ObservableList<SelledProduct> selledProducts;
    private final ObjectProperty<Seller> seller = new SimpleObjectProperty<>();
    
    @FXML private void initialize() {
        seller.addListener( (obj) -> {
            selledProducts = seller.get().getSelledProductList();

            productContainer.setItems(selledProducts);
            revenuContainer.setItems(selledProducts);

            lblTotalrevenu.textProperty().bind( Bindings.createDoubleBinding(
                    () -> selledProducts.stream().mapToDouble( item -> item.getPrice() * item.getSelledCount()).sum(), selledProducts).asString()
            );

            productContainer.setCellFactory( x -> new SelledProductController());
            revenuContainer.setCellFactory( x -> new ListCell<>(){
                @Override
                protected void updateItem(SelledProduct selledProduct, boolean b) {
                    super.updateItem(selledProduct, b);
                    if(b || selledProduct == null) {
                        setText(null);
                    } else {
                        setText("Produit "  + (selledProduct.getProduct() == null ? "{null}" : selledProduct.getProduct().getDesignation()) + " : " + selledProduct.getSelledCount() * selledProduct.getPrice());
                    }
                }
            });
        });

    }

    @FXML private void handleAdd(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage newStage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "seller/add.fxml"));
            BorderPane root = (BorderPane) loader.load();
            AddController controller = loader.getController();

            SelledProduct selledProduct = new SelledProduct();

            System.out.println(Product.getProductList().filtered( item -> !seller.get().getProductList().contains(item)));
            controller.setProduct(FXCollections.observableArrayList(Product.getProductList().filtered( item -> !seller.get().getProductList().contains(item))));
            controller.setSelledProduct(selledProduct);
            controller.setCurrentStage(newStage);

            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(currentStage);
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            newStage.showAndWait();

            if(!controller.isAddClicked()) return;
            try {
                getSeller().registerNewSelledProduct(
                        selledProduct.getProduct(), selledProduct
                );
            } catch (Seller.SellerAlreadySellProduct ignored) {}
            System.out.println(selledProducts);

        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public Seller getSeller() {
        return seller.get();
    }
    public ObjectProperty<Seller> sellerProperty() {
        return seller;
    }
    public void setSeller(Seller seller) {
        this.seller.set(seller);
    }
}
