package project.gui.models.marketplace;

import java.util.*;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.gui.Main;

public class Product {

	private static ObservableList<Product> productList = FXCollections.observableArrayList(
			item -> new Observable[] {
					item.referenceProperty(),
					item.descriptionProperty(),
					item.designationProperty()
			});
	public static ObservableList<Product> getProductList() {return FXCollections.unmodifiableObservableList(productList); }

	private final StringProperty reference = new SimpleStringProperty();
	private final StringProperty designation = new SimpleStringProperty();
	private final StringProperty description = new SimpleStringProperty();
	
	public Product(String reference, String designation, String description) {
		this.reference.set(reference);
		this.designation.set(designation);
		this.description.set(description);
	}

    public Product() {
        this("", "", "");
    }

    public String getReference() {
		return reference.get();
	}
	public StringProperty referenceProperty() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference.set(reference);
	}

	public String getDesignation() {
		return designation.get();
	}
	public StringProperty designationProperty() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation.set(designation);
	}

	public String getDescription() {
		return description.get();
	}
	public StringProperty descriptionProperty() {
		return description;
	}
	public void setDescription(String description) {
		this.description.set(description);
	}

	@Override
	public String toString() {
		return "Product{" +
				"reference=" + reference +
				", designation=" + designation +
				", description=" + description +
				", id=" + id +
				'}';
	}

	/* =================== move code elsewhere =============== */

	// Database column/fields correspondence
	private static final int REFERENCE_FIELD = 0;
	private static final int DESIGNATION_FIELD = 1;
	private static final int DESCRIPTION_FIELD = 2;

	/**
	 * Store all product by reference to keep reference to it
	 */
	private static final HashMap<Integer, Product> productById = new HashMap<>();
	public static Map<Integer,Product> getProducts() { return productById; }

	private static int counterID = 0;
	private static final XSSFSheet sheet = Main.workbook.getSheet("product");

	//just for internal use
	private int id = -1;
	public int getId() { return this.id; }

	/**
	 * load all product from table 'product' of XLSX database defined in project.App
	 * @return the number of product loaded
	 */
	public static int load() {
		Iterator<Row> rowIt = sheet.rowIterator();
		rowIt.next(); //skip header

		int count = 0;
		while(rowIt.hasNext()) {
			Row row = rowIt.next();

			//TODO: reference Cell should never be null, make something to ensure that
			int id = row.getRowNum();
			String reference = row.getCell(REFERENCE_FIELD).getStringCellValue();
			String name = row.getCell(DESIGNATION_FIELD).getStringCellValue();
			String description = row.getCell(DESCRIPTION_FIELD) == null ? "" : row.getCell(DESCRIPTION_FIELD).getStringCellValue();

			Product product = new Product(id, reference, name, description);
			if(counterID <= id) counterID = id +1;

			productById.put(id, product);
			count++;
		}

		return count;
	}

	public static void unload() {
		for (Product product : productList) {
			Row row = Optional.ofNullable(sheet.getRow(product.getId())).orElseGet( () -> sheet.createRow(product.getId()) );
			Optional.ofNullable(row.getCell(REFERENCE_FIELD)).orElseGet( () -> row.createCell(REFERENCE_FIELD) ).setCellValue(product.getReference());
			Optional.ofNullable(row.getCell(DESIGNATION_FIELD)).orElseGet( () -> row.createCell(DESIGNATION_FIELD) ).setCellValue(product.getDesignation());
			Optional.ofNullable(row.getCell(DESCRIPTION_FIELD)).orElseGet( () -> row.createCell(DESCRIPTION_FIELD) ).setCellValue(product.getDescription());
		}
	}

	/** Store all seller that sell this product with sellinfo */
	private final HashMap<Seller, SelledProduct> sellers = new HashMap<Seller, SelledProduct>();

	/**
	 * Register a SelledProduct
	 * @param selledProduct
	 */
	public void register(SelledProduct selledProduct) {
		if(!productList.contains(this)) {
			if (this.id == -1) this.id = counterID++ ;
			productList.add(this);
		}
		sellers.put(selledProduct.getSeller(), selledProduct);
	}

	/**
	 * Remove product from the app
	 */
	public void unregister() {
		for (SelledProduct selledProduct : sellers.values()) {
			selledProduct.unregister();
		};
		sellers.clear();
		productList.remove(this.id);
	}

	private Product(int id, String reference, String designation, String description) {
		this(reference, designation, description);
		this.id = id;
	}
}
