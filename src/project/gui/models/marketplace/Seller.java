package project.gui.models.marketplace;

import javafx.beans.Observable;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.gui.Main;
import project.gui.models.utils.User;

import java.util.*;

public abstract class Seller extends User {

	private static ObservableList<Seller> sellerList = FXCollections.observableArrayList(
			item -> new Observable[]{
					item.nameProperty()
			});
	public static ObservableList<Seller> getSellerList() { return FXCollections.unmodifiableObservableList(sellerList); }

	private final StringProperty name = new SimpleStringProperty();

	public Seller(int id, int idAsSeller, String firstName, String lastName, String name) {
		super(firstName, lastName);
		this.id = id;
		this.idAsSeller = idAsSeller;
		this.name.set(name);
	}

	public int getId() {
		return id;
	}
	public int getIdAsSeller() { return idAsSeller; }

	public String getName() {
		return name.get();
	}
	public StringProperty nameProperty() {
		return name;
	}
	public void setName(String name) {
		this.name.set(name);
	}

	@Override
	public String toString() {
		return "Seller{" +
				"name=" + name +
				", id=" + id +
				"} " + super.toString();
	}

	/* ======== move code elsewhere ========= */

	enum SellerType { ADMIN, EXTERN }

	private static final int FIRSTNAME_FIELD = 0;
	private static final int LASTNAME_FIELD = 1;
	private static final int IS_ADMIN_FIELD = 2;
	private static final int SELLER_ID_FIELD = 3;
	private static final int NAME_FIELD = 4;

	/**
	 * store all seller to keep reference to it
	 */
	private static final HashMap<Integer,Seller> sellersById = new HashMap<>();
	public static Map<Integer, Seller> getSellers() {
		return sellersById;
	}

	private static int counter = 0;
	private static final XSSFSheet sheet = Main.workbook.getSheet("seller");

	/**
	 * load all product from table 'seller' of XLSX database defined in project.App
	 * @return the number of product loaded
	 */
	public static int load() {
		Iterator<Row> rowIt = sheet.rowIterator();
		rowIt.next(); //skip header

		int count = 0;
		while(rowIt.hasNext()) {
			Row row = rowIt.next();

			int id = row.getRowNum();
			String firstName = row.getCell(FIRSTNAME_FIELD).getStringCellValue();
			String lastName = row.getCell(LASTNAME_FIELD).getStringCellValue();
			SellerType admin = SellerType.valueOf(row.getCell(IS_ADMIN_FIELD).getStringCellValue());
			int sellerid = (int) row.getCell(SELLER_ID_FIELD).getNumericCellValue();
			String name = row.getCell(NAME_FIELD) == null ? "" : row.getCell(NAME_FIELD).getStringCellValue();

			//TODO: Strange to load ADMIN and EXTERN from Seller - modify code
			Seller seller = switch (admin) {
				case ADMIN -> new Admin(sellerid, id, firstName, lastName);
				case EXTERN -> ExternSeller.load(sellerid, id, firstName, lastName, name);
			};


			if(counter <= id) counter = id +1;

			sellersById.put(id, seller);
			sellerList.add(seller);
			count++;
		}
		return count;
	}

	public static void unload() {
		for (Seller seller : sellerList) {
			Row row = Optional.ofNullable(sheet.getRow(seller.idAsSeller)).orElseGet( () -> sheet.createRow(seller.idAsSeller) );
			Optional.ofNullable(row.getCell(FIRSTNAME_FIELD)).orElseGet( () -> row.createCell(FIRSTNAME_FIELD) ).setCellValue(seller.getFirstName());
			Optional.ofNullable(row.getCell(LASTNAME_FIELD)).orElseGet( () -> row.createCell(LASTNAME_FIELD) ).setCellValue(seller.getLastName());
			Optional.ofNullable(row.getCell(IS_ADMIN_FIELD)).orElseGet( () -> row.createCell(IS_ADMIN_FIELD) ).setCellValue( (seller instanceof Admin ? SellerType.ADMIN : SellerType.EXTERN).name() );
			//Optional.ofNullable(row.getCell(SELLER_ID_FIELD)).orElseGet( () -> row.createCell(SELLER_ID_FIELD) ).setCellValue(entry.getValue().getId());
			Optional.ofNullable(row.getCell(NAME_FIELD)).orElseGet( () -> row.createCell(NAME_FIELD) ).setCellValue(seller.getName());

			if (seller instanceof ExternSeller externSeller) {
				ExternSeller.unload(externSeller);
			}
		}
	}

	private int id;
	private int idAsSeller;

	/**
	 * register a new seller
	 * Give it an id and reference it in all list
	 */
	public void register() {
		if(!sellersById.containsValue(this)) {
			this.idAsSeller = counter++;
			sellerList.add(this);
		}
	}


	/**
	 * register a new SelledProduct.
	 * @param product
	 * @param selledProduct
	 * @throws SellerAlreadySellProduct if the seller already sells this item
	 */
	public void registerNewSelledProduct(Product product, SelledProduct selledProduct) throws SellerAlreadySellProduct {
		if(getProductsList().contains(product)) throw new SellerAlreadySellProduct();

		getSelledProductsList().add(selledProduct);
		getProductsList().add(product);

		selledProduct.register(product, this);
		product.register(selledProduct);
	}

	/**
	 * unregister a selledProduct
	 * @param selledProduct
	 */
	public void unregister(SelledProduct selledProduct) {
		getProductsList().remove(selledProduct.getProduct());
		getSelledProductsList().remove(selledProduct.getProduct());
	}

	protected abstract ObservableList<Product> getProductsList();
	public 	ObservableList<Product> getProductList() { return FXCollections.unmodifiableObservableList(getProductsList()); }

	protected abstract ObservableList<SelledProduct> getSelledProductsList();
	public 	ObservableList<SelledProduct> getSelledProductList() { return FXCollections.unmodifiableObservableList(getSelledProductsList()); }

	public class SellerAlreadySellProduct extends Throwable {}
}
