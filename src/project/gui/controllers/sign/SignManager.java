package project.gui.controllers.sign;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.gui.Main;
import project.gui.controllers.sign.SignInController;
import project.gui.controllers.sign.SignUpController;
import project.gui.models.Account;

import java.io.IOException;

public class SignManager {

    public enum SignType { SIGN_UP, SIGN_IN }

    private SignType type = SignType.SIGN_IN;

    private Pane signInPane;
    private Pane signUpPane;

    private Scene scene;
    private Stage signStage;

    /**
     * object to keep track which sign stage is open. view track with getState()
     * @param account not null. keep a reference to it!
     * @param stage not null. keep a reference to it!
     * @throws IOException
     */
    public SignManager(Account account, Stage stage) {
    	try {
	        Stage newStage = new Stage();
	        newStage.initOwner(stage);
	        newStage.initModality(Modality.WINDOW_MODAL);
	        newStage.setResizable(false);
	
	        signStage = newStage;
	
	        FXMLLoader loader1 = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "common/signIn.fxml"));
	        signInPane = loader1.load();
	        SignInController signInController = loader1.getController();
	        signInController.setManager(this);
	        signInController.setStage(signStage);
	        signInController.setAccount(account);
	
	        FXMLLoader loader2 = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "common/signUp.fxml"));
	        signUpPane = loader2.load();
	        SignUpController signUpController = loader2.getController();
	        signUpController.setManager(this);
	        signUpController.setStage(signStage);
	        signUpController.setAccount(account);
    	} catch (Exception e) { throw new RuntimeException(e); }
    }

    public void toggleSignMethod() {
        type = type == SignType.SIGN_IN ? SignType.SIGN_UP : SignType.SIGN_IN;
        showStage();
    }

    public void showStage() {
    	if (scene == null) {
    		scene = new Scene(signInPane);
    		signStage.setScene(scene);
    	}
    	
        switch (type) {
            case SIGN_IN -> scene.setRoot(signInPane);
            case SIGN_UP -> scene.setRoot(signUpPane);
        }
        
        if (!signStage.isShowing()) signStage.showAndWait();
    }
}
