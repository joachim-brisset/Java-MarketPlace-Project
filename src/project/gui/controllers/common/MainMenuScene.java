package project.gui.controllers.common;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.buyer.mainmenu.MainMenuSceneController;
import project.gui.controllers.seller.MenuController;
import project.gui.models.Account;
import project.gui.models.marketplace.Buyer;
import project.gui.models.marketplace.ExternSeller;
import project.gui.models.marketplace.Seller;
import project.gui.models.utils.User;

public class MainMenuScene {

    @FXML private AnchorPane mainMenuContainer;

    private final ObjectProperty<Account> account = new SimpleObjectProperty<>();
    private Stage currentStage;

    public MainMenuScene() { }

    @FXML private void initialize() {
        account.addListener( (obj) -> {
            User user = account.get().getUser();
            System.out.println("Current User : " + user);
            try {
            	AnchorPane pane;
                if (user instanceof Buyer) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "buyer/mainmenu/MainMenu_Scene.fxml"));
                    pane = loader.load();
                    MainMenuSceneController controller = loader.getController();
                    controller.setBuyer((Buyer) user);
                    
                    mainMenuContainer.getChildren().add(pane);
                
                } else if (user instanceof ExternSeller) {
                	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "seller/menu.fxml"));
                    pane = loader.load();
                    MenuController controller = loader.getController();
                    Seller seller = (Seller) user;
                    controller.setSeller(seller);
                    
                    mainMenuContainer.getChildren().add(pane);
                }

            } catch (Exception e) { throw new RuntimeException(e); }
        });
    }

    @FXML private void handleDisconnect(ActionEvent actionEvent) {
    	currentStage.close();
    }

    @FXML private void handleAccount(ActionEvent actionEvent) {
    	try {
	    	Stage newStage = new Stage();
	        newStage.initOwner(currentStage);
	        newStage.initModality(Modality.WINDOW_MODAL);
	
	        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "common/EditPersoInfo.fxml"));
	        newStage.setScene(new Scene(loader.load()));
	
	        EditPersoInfoController controller = loader.getController();
	        
	        controller.setAccount(account.get());
	        controller.setCurrentStage(newStage);
	
	        newStage.showAndWait();
    	} catch (Exception e) { throw new RuntimeException(e); }
    }

    
	public void setAccount(Account account) {
		this.account.set(account);;
	}
	
	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}
    
    
}
