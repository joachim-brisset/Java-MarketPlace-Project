package project.marketplace;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.App;

import java.util.*;

public class Product {

    /**
     * Store all product by reference to keep reference to it
     */
    private static HashMap<Integer, Product> productById = new HashMap<>();
    public static Map<Integer,Product> getProducts() {
        return Collections.unmodifiableMap(productById);
    }

    private static int counter = 0;

    /**
     * load all product from table 'product' of XLSX database defined in project.App
     * @return the number of product loaded
     */
    public static int load() {
        XSSFSheet sheet = App.workook.getSheet("product");
        Iterator<Row> rowIt = sheet.rowIterator();
        rowIt.next(); //skip header

        int count = 0;
        while(rowIt.hasNext()) {
            Row row = rowIt.next();

            Cell cell;
            cell = row.getCell(0);
            int id = (int) cell.getNumericCellValue();
            cell = row.getCell(1);
            String reference = (Objects.isNull(cell)) ? "" : cell.getStringCellValue();
            //TODO: reference Cell should never be null, make something to ensure that
            cell = row.getCell(2);
            String name = (Objects.isNull(cell)) ? "" : cell.getStringCellValue();
            cell = row.getCell(3);
            String description = (Objects.isNull(cell)) ? "" : cell.getStringCellValue();

            Product product = new Product(id, reference, name, description);
            if(counter <= id) counter = id +1;

            productById.put(id, product);
            count++;
        }

        return count;
    }

    private int id;

    private String reference;
    private String designation;
    private String description;

    /** Store all seller that sell this product with sellinfo */
    private HashMap<Seller, SelledProduct> sellers = new HashMap<Seller, SelledProduct>();

    private Product(int id, String reference, String designation, String description) {
        this(reference, designation, description);
        this.id = id;
    }

    public Product(String reference, String designation, String description) {
        this.reference = reference;
        this.designation = designation;
        this.description = description;
    }

    /* unused
    /**
     * create a new product and register it to
     * @implSpec no other product object with reference must exist
     * @param reference
     * @param designation
     * @param description
     * @return

    public static Product create(String reference, String designation, String description) {
        //TODO: check if reference does not alredy exist
        Product product = new Product(reference, designation, description);
        productById.put(counter++, product);
        return product;
    }
    */

    public void register(SelledProduct selledProduct) {
        if(!productById.containsValue(this)) {
            this.id = counter++;
            productById.put(this.id, this);
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
        productById.remove(this.id);
    }

    public int getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }
    private void setReference(String reference) {
        //do not call this methode: cannot change an identifier
        this.reference = reference;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Seller, SelledProduct> getSellers() {
        return Collections.unmodifiableMap(sellers);
    }
}
