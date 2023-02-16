package project.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import project.gui.controllers.common.MainMenuScene;
import project.gui.controllers.sign.SignManager;
import project.gui.models.Account;
import project.gui.models.marketplace.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main extends Application {

	/** base path to get FXML file */
	public static final String VIEW_FOLDER = "project/gui/views/";
	/** Model object for XLSX file */
    public static XSSFWorkbook workbook;

    private static String xlsxPath;

	/**
	 * Main function
	 * Read program argument, init program and launch GUI.
	 *
	 * @param args String containing program argument. Here we need a unique argument : Path to XLSX file
	 */
    public static void main(String[] args) {

		if(args.length != 1) {	// read argument
			System.out.println("Le programme doit prendre le path (sans espace dedans) du fichier excel en 1er argument");
			System.exit(-1);
		}
		System.out.println("XLSX file : " + args[0]);
		xlsxPath = args[0];

		try {
			FileInputStream fis = new FileInputStream(xlsxPath);
			workbook = new XSSFWorkbook(fis);
			fis.close();
		} catch (IOException e) { throw new RuntimeException(e); } // TODO: create file if it does not exist

		//load all data
		System.out.println("Loaded " + Product.load() + " product");
		System.out.println("Loaded " + Seller.load() + " seller");
		System.out.println("Loaded " + SelledProduct.load() + " selledProduct");
		System.out.println("Loaded " + Contract.load() + " Contract");
		Buyer.load();
		Order.load();

		SelledProduct.linkModels();
		Contract.linkModels();
		Buyer.linkModels();
		Order.linkModels();

    	launch(args); //launch GUI

		Product.unload();
		Seller.unload();
		SelledProduct.unload();
		Contract.unloadAll();
		Buyer.unloadAll();
		Order.unload();

		try {
			FileOutputStream fos = new FileOutputStream(xlsxPath);
			workbook.write(fos);
			fos.close();
		} catch (IOException e) { throw new RuntimeException(e); }
	}



	@Override
	public void start(Stage primaryStage) {
		while (true) {
			Account account = new Account(0, "", "", false, null);

			SignManager manager = new SignManager(account, primaryStage);
			manager.showStage();

			if (!account.isConnected()) break;


			Stage stage = new Stage();

			try {
				stage.initOwner(primaryStage);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setResizable(false);

				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.VIEW_FOLDER + "MainMenu_Scene.fxml"));
				Parent root = loader.load();

				MainMenuScene controller = loader.getController();
				controller.setAccount(account);
				controller.setCurrentStage(stage);

				stage.setScene(new Scene(root));
				stage.showAndWait();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void uninplemented(String message) {
		System.err.println("NOT IMPLEMENTED YET");

		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText("NOT IMPLEMENTED YET");
		alert.setContentText(message == null ? "This feature will be implemented latter." : message);

		alert.showAndWait();
	}
}
