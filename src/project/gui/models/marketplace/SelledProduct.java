package project.gui.models.marketplace;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.gui.Main;

public class SelledProduct {
	private static ObservableList<SelledProduct> selledProductList = FXCollections.observableArrayList(
			item -> new Observable[] {
					item.productProperty(),
					item.sellerProperty(),
					item.stockProperty(),
					item.priceProperty(),
					item.shippingPriceProperty(),
					item.shippingTimeProperty(),
					item.selledCountProperty()
			});
	public static void setSelledProductList(ObservableList<SelledProduct> selledProductList) {
		SelledProduct.selledProductList = selledProductList;
	}
	public static ObservableList<SelledProduct> getSelledProductList() {
		return selledProductList;
	}
	
	
	private final ObjectProperty<Product> product = new SimpleObjectProperty<>();
    private final ObjectProperty<Seller> seller = new SimpleObjectProperty<>();

    private int id;

    private final LongProperty stock = new SimpleLongProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final DoubleProperty shippingPrice = new SimpleDoubleProperty();
    private final ObjectProperty<Duration> shippingTime = new SimpleObjectProperty<>();
    private final LongProperty selledCount = new SimpleLongProperty();

	public SelledProduct(Product product, Seller seller, int id, long stock, double price, double shippingPrice,
			Duration shippingTime, long selledCount) {
		this.product.set(product);
		this.seller.set(seller);
		this.id = id;
		this.stock.set(stock);
		this.price.set(price);
		this.shippingPrice.set(shippingPrice);
		this.shippingTime.set(shippingTime);
		this.selledCount.set(selledCount);
	}

    public SelledProduct() {
        this(null, null, -1, 0, 0, 0, null, 0);
    }

	public Product getProduct() {
		return product.get();
	}
	public ObjectProperty<Product> productProperty() {
		return product;
	}
	public void setProduct(Product product) {
		this.product.set(product);
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public long getStock() {
		return stock.get();
	}
	public LongProperty stockProperty() {
		return stock;
	}
	public void setStock(long stock) {
		this.stock.set(stock);
	}

	public double getPrice() {
		return price.get();
	}
	public DoubleProperty priceProperty() {
		return price;
	}
	public void setPrice(double price) {
		this.price.set(price);
	}

	public double getShippingPrice() {
		return shippingPrice.get();
	}
	public DoubleProperty shippingPriceProperty() {
		return shippingPrice;
	}
	public void setShippingPrice(double shippingPrice) {
		this.shippingPrice.set(shippingPrice);
	}

	public Duration getShippingTime() {
		return shippingTime.get();
	}
	public ObjectProperty<Duration> shippingTimeProperty() {
		return shippingTime;
	}
	public void setShippingTime(Duration shippingTime) {
		this.shippingTime.set(shippingTime);
	}

	public long getSelledCount() {
		return selledCount.get();
	}
	public LongProperty selledCountProperty() {
		return selledCount;
	}
	public void setSelledCount(long selledCount) {
		this.selledCount.set(selledCount);
	}

	@Override
	public String toString() {
		return "SelledProduct{" +
				"product=" + product +
				", seller=" + seller +
				", id=" + id +
				", stock=" + stock +
				", price=" + price +
				", shippingPrice=" + shippingPrice +
				", shippingTime=" + shippingTime +
				", selledCount=" + selledCount +
				'}';
	}

	/* ============ Move code else where ============== */

	// Database column/fields correspondence
	private static final int SELLER_ID_FIELD = 0;
	private static final int PRODUCT_ID_FIELD = 1;
	private static final int STOCK_FIELD = 2;
	private static final int SHIPPING_TIME_FIELD = 3;
	private static final int PRICE_FIELD = 4;
	private static final int SHIPPING_PRICE_FIELD = 5;
	private static final int SELLED_COUNT_FIELD = 6;

	//map for linking Object after
	public static final HashMap<Integer, SelledProduct> selledProductById = new HashMap<>();
	public static final HashMap<SelledProduct, Integer> sellerIdBySelledProduct = new HashMap<>();
	public static final HashMap<SelledProduct, Integer> productIdBySelledProduct = new HashMap<>();

	private static int counterID = 0;
	private static final XSSFSheet sheet = Main.workbook.getSheet("selledproduct");

	/**
	 * load all product from table 'selledproduct' of XLSX database defined in project.App
	 * @return the number of selledproduct loaded
	 */
	public static int load() {
		Iterator<Row> rowIt = sheet.rowIterator();
		rowIt.next(); //skip header

		int count = 0;
		while(rowIt.hasNext()) {
			Row row = rowIt.next();

			int id = row.getRowNum();

			int sellerid = (int) row.getCell(SELLER_ID_FIELD).getNumericCellValue();
			int productid = (int) row.getCell(PRODUCT_ID_FIELD).getNumericCellValue();
			long stock = (long) row.getCell(STOCK_FIELD).getNumericCellValue();
			long shippingTime = (long) (row.getCell(SHIPPING_TIME_FIELD) == null ? 0 : row.getCell(SHIPPING_TIME_FIELD).getNumericCellValue());
			double price = row.getCell(PRICE_FIELD).getNumericCellValue();
			double shippingPrice = (row.getCell(SHIPPING_PRICE_FIELD) == null ? 0 : row.getCell(SHIPPING_PRICE_FIELD).getNumericCellValue());
			long selledCount = (long) (row.getCell(SELLED_COUNT_FIELD) == null ? 0 : row.getCell(SELLED_COUNT_FIELD).getNumericCellValue());


			SelledProduct selledProduct = new SelledProduct(id ,stock, price, shippingPrice, Duration.ofDays(shippingTime), selledCount);

			selledProductById.put(id, selledProduct);
			sellerIdBySelledProduct.put(selledProduct, sellerid);
			productIdBySelledProduct.put(selledProduct, productid);

			if (counterID <= id) counterID = id +1; //keep track if the next possible ID

			count++;
		}
		return count;
	}

	/**
	 * using object/id map, link SelledProduct object with object associated
	 */
	public static void linkModels() {
		for(SelledProduct selledProduct : selledProductById.values()) {
			try {
				Seller.getSellers().get(sellerIdBySelledProduct.get(selledProduct)).registerNewSelledProduct(
						Product.getProducts().get(productIdBySelledProduct.get(selledProduct)),
						selledProduct
				);
			} catch (Seller.SellerAlreadySellProduct e) {  }
			selledProductList.add(selledProduct);
		}
	}

	public static void unload() {
		for (SelledProduct selledProduct : selledProductList) {
			Row row = Optional.ofNullable(sheet.getRow(selledProduct.id)).orElseGet( () -> sheet.createRow(selledProduct.id) );
			Optional.ofNullable(row.getCell(SELLER_ID_FIELD)).orElseGet( () -> row.createCell(SELLER_ID_FIELD) ).setCellValue(selledProduct.getSeller().getIdAsSeller());
			Optional.ofNullable(row.getCell(PRODUCT_ID_FIELD)).orElseGet( () -> row.createCell(PRODUCT_ID_FIELD) ).setCellValue(selledProduct.getProduct().getId());
			Optional.ofNullable(row.getCell(STOCK_FIELD)).orElseGet( () -> row.createCell(STOCK_FIELD) ).setCellValue(selledProduct.getStock());
			Optional.ofNullable(row.getCell(SHIPPING_TIME_FIELD)).orElseGet( () -> row.createCell(SHIPPING_TIME_FIELD) ).setCellValue(selledProduct.getShippingTime().toDays());
			Optional.ofNullable(row.getCell(PRICE_FIELD)).orElseGet( () -> row.createCell(PRICE_FIELD) ).setCellValue(selledProduct.getPrice());
			Optional.ofNullable(row.getCell(SHIPPING_PRICE_FIELD)).orElseGet( () -> row.createCell(SHIPPING_TIME_FIELD) ).setCellValue(selledProduct.getShippingPrice());
			Optional.ofNullable(row.getCell(SELLED_COUNT_FIELD)).orElseGet( () -> row.createCell(SELLED_COUNT_FIELD) ).setCellValue(selledProduct.getSelledCount());
		}
	}

	/**
	 * Create a new SelledProduct object. IT'S JUST A DATA CONTAINER IT IS NOT REGISTERED.
	 * Use register method to set it a seller and a product
	 * @param id
	 * @param stock
	 * @param price
	 * @param shippingPrice
	 * @param shippingTime
	 * @param selledCount
	 */
	private SelledProduct(int id, long stock, double price, double shippingPrice, Duration shippingTime, long selledCount) {
		this(stock, price, shippingPrice, shippingTime);
		this.id = id;
		this.selledCount.set(selledCount);
	}
	public SelledProduct(long stock, double price, double shippingPrice, Duration shippingTime) {
		this.stock.set(stock);
		this.price.set(price);
		this.shippingPrice.set(shippingPrice);
		this.shippingTime.set(shippingTime);
	}

	/**
	 * Register a SelledProduct
	 * Give it an id and set it's seller and Product
	 */
	public void register(Product product, Seller seller) {
		if (!selledProductList.contains(this)) {
			if (this.id == -1) this.id = counterID++;
			selledProductList.add(this);
		}

		this.product.set(product);
		this.seller.set(seller);
	}

	/**
	 * Unregister this SelledProduct
	 * remove it from SelledProduct list and SelledProduct list of it's seller
	 */
	public void unregister() {
		selledProductList.remove(this);
		this.seller.get().unregister(this);
	}

	/**
	 * Check can sell a quantity
	 * @param quantity
	 * @return
	 */
	public boolean canSell(int quantity) {
		return quantity < stock.get();
	}

	/**
	 * Apply sell modifycation
	 * decrease SellerProduct Stock and increase SelledProduct selledCount
	 * @param quantity
	 */
	public void sell(int quantity) throws SelledProductNotEnoughStock {
		if(canSell(quantity)) throw new SelledProductNotEnoughStock();

		stock.set(stock.get() - quantity);
		selledCount.set(selledCount.get() + quantity);
	}

	public class SelledProductNotEnoughStock extends Throwable { }
}
