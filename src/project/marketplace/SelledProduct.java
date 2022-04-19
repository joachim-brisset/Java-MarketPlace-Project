package project.marketplace;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.App;

import java.time.Duration;
import java.util.*;

public class SelledProduct {

    /**
     * store all selledproduct to keep reference to it
     */
    private static HashMap<Integer, SelledProduct> selledProductsById = new HashMap<>();
    public static Map<Integer, SelledProduct> getSelledProducts() {
        return Collections.unmodifiableMap(selledProductsById);
    }

    private static int counter = 0;

    /**
     * load all product from table 'selledproduct' of XLSX database defined in project.App
     * @return the number of selledproduct loaded
     */
    public static int load() {
        XSSFSheet sheet = App.workook.getSheet("selledproduct");
        Iterator<Row> rowIt = sheet.rowIterator();
        rowIt.next(); //skip header

        int count = 0;
        while(rowIt.hasNext()) {
            Row row = rowIt.next();

            Cell cell;
            cell = row.getCell(0);
            int id = (int) cell.getNumericCellValue();

            cell = row.getCell(1);
            int sellerid = (int) cell.getNumericCellValue();

            cell = row.getCell(2);
            int productid = (int) cell.getNumericCellValue();

            cell = row.getCell(3);
            long stock = (long) cell.getNumericCellValue();

            cell = row.getCell(4);
            long shippingTime = (long) ((Objects.isNull(cell)) ? 0 : cell.getNumericCellValue());

            cell = row.getCell(5);
            double price = (Objects.isNull(cell)) ? 0 : cell.getNumericCellValue();

            cell = row.getCell(26);
            double shippingPrice = (Objects.isNull(cell)) ? 0 : cell.getNumericCellValue();


            SelledProduct selledProduct = new SelledProduct(id ,stock, price, shippingPrice, Duration.ofDays(shippingTime));
            selledProductsById.put(id, selledProduct);
            if (counter <= id) counter = id +1;

            Seller.getSellers().get(sellerid).registerNewSelledProduct(
                    Product.getProducts().get(productid),
                    selledProduct
            );
            count++;
        }

        return count;
    }

    private Product product;
    private Seller seller;

    private int id;

    private long stock;
    private double price;
    private double shippingPrice;
    private Duration shippingTime;

    private SelledProduct(int id, long stock, double price, double shippingPrice, Duration shippingTime) {
        this(stock, price, shippingPrice, shippingTime);
        this.id = id;
    }
    public SelledProduct(long stock, double price, double shippingPrice, Duration shippingTime) {
        this.stock = stock;
        this.price = price;
        this.shippingPrice = shippingPrice;
        this.shippingTime = shippingTime;
    }

    /**
     * register it
     */
    public void register(Product product, Seller seller) {
        if (!selledProductsById.containsValue(this)) {
            this.id = counter++;
            selledProductsById.put(this.id, this);
        }

        this.product = product;
        this.seller = seller;
    }

    /**
     * remove this selledProuct from app
     */
    public void unregister() {
        selledProductsById.remove(this.id);
        this.seller.unregister(this);
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
    private void setProduct(Product product) {
        this.product = product;
    }
    public Seller getSeller() {
        return seller;
    }
    private void setSeller(Seller seller) {
        this.seller = seller;
    }

    public long getStock() {
        return stock;
    }
    public void setStock(long stock) {
        if (stock < 0) throw new IllegalArgumentException("stock must not be inferior to 0");
        this.stock = stock;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        if (stock < 0) throw new IllegalArgumentException("price must not be inferior to 0");
        this.price = price;
    }
    public double getShippingPrice() {
        return shippingPrice;
    }
    public void setShippingPrice(double shippingPrice) {
        if (stock < 0) throw new IllegalArgumentException("stock must not be inferior to 0");
        this.shippingPrice = shippingPrice;
    }
    public Duration getShippingTime() {
        return shippingTime;
    }
    public void setShippingTime(Duration shippingTime) {
        this.shippingTime = shippingTime;
    }
}
