package project.marketplace;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.App;
import project.User;

import java.util.*;

public abstract class Seller extends User {

    /**
     * store all seller to keep reference to it
     */
    private static HashMap<Integer,Seller> sellersById = new HashMap<>();
    public static Map<Integer, Seller> getSellers() {
        return Collections.unmodifiableMap(sellersById);
    }

    private static int counter = 0;

    /**
     * load all product from table 'seller' of XLSX database defined in project.App
     * @return the number of product loaded
     */
    public static int load() {
        XSSFSheet sheet = App.workook.getSheet("seller");
        Iterator<Row> rowIt = sheet.rowIterator();
        rowIt.next(); //skip header

        int count = 0;
        while(rowIt.hasNext()) {
            Row row = rowIt.next();

            Cell cell;
            cell = row.getCell(0);
            int id = (int) ((Objects.isNull(cell)) ? 0 : cell.getNumericCellValue());

            cell = row.getCell(1);
            boolean admin = cell.getStringCellValue() == "ADMIN";

            cell = row.getCell(2);
            String name = (Objects.isNull(cell)) ? "" : cell.getStringCellValue();

            Seller seller;
            if (admin) { seller = new Admin(); } else { seller = new ExternSeller(name); }
            if(counter <= id) counter = id +1;

            sellersById.put(id, seller);
            count++;
        }

        return count;
    }

    private int id;
    private String name;

    public Seller(String name) {
        this.name = name;
    }

    public void register() {
        if(!sellersById.containsValue(this)) {
            this.id = counter++;
            sellersById.put(this.id, this);
        }
    }


    public void registerNewSelledProduct(Product product, SelledProduct selledProduct) {
        if(getProducts().containsKey(product)) return;

        getProducts().put(product, selledProduct);
        selledProduct.register(product, this);
        product.register(selledProduct);
    }

    public void unregister(SelledProduct selledProduct) {
        getProducts().remove(selledProduct.getProduct());
    }

    public String getName() {
        return name;
    }
    private void setName(String name) {
        //TODO: change name
        this.name = name;
    }

    protected abstract HashMap<Product, SelledProduct> getProducts();

    public Map<Product, SelledProduct> getProductList() {
        return Collections.unmodifiableMap(getProducts());
    }
}
