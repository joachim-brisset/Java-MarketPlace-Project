package project.gui.models.marketplace;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import project.gui.Main;
import project.gui.models.delivery.DeliveryPoint;
import project.gui.models.delivery.DeliveryStatus;
import project.gui.models.utils.Generator;
import project.utils.DateUtils;

import java.text.ParseException;
import java.util.*;

public class Order {

    private final StringProperty reference = new SimpleStringProperty();
    private final ObjectProperty<Buyer> buyer = new SimpleObjectProperty<>(null);

    private final ObjectProperty<DeliveryStatus> deliveryStatus = new SimpleObjectProperty<>(null);
    private final ObjectProperty<DeliveryPoint> deliveryPoint = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Date> creationDate = new SimpleObjectProperty<>(null);

    private ObservableList<CartedProduct> cartedProducts = FXCollections.observableArrayList(item -> new Observable[] {item.quantityProperty(), item.selledProductProperty()} );

    private final DoubleBinding price = Bindings.createDoubleBinding(
            () -> cartedProducts.stream().mapToDouble( item -> item.getQuantity() * item.getSelledProduct().getPrice()).sum()
    );

    private Order(String reference, Buyer buyer, DeliveryStatus deliveryStatus, DeliveryPoint deliveryPoint, List<CartedProduct> cartedProducts, Date creationDate) {
        this(buyer, deliveryStatus, deliveryPoint, cartedProducts, creationDate);
        this.reference.set(reference);
    }

    public Order(Buyer buyer, DeliveryStatus deliveryStatus, DeliveryPoint deliveryPoint, List<CartedProduct> cartedProducts, Date creationDate) {
        this.buyer.set(buyer);
        this.deliveryStatus.set(deliveryStatus);
        this.deliveryPoint.set(deliveryPoint);
        if (cartedProducts != null) this.cartedProducts.addAll(cartedProducts);
        this.creationDate.set(creationDate);
    }

    /** Altered constructor from cart object */
    public Order(Cart cart) {
        this(cart.getOwner(), DeliveryStatus.WAITING, new DeliveryPoint(cart.getOwner().getAddress()), cart.getCartedProducts(), new Date());
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

    public Buyer getBuyer() {
        return buyer.get();
    }
    public ObjectProperty<Buyer> buyerProperty() {
        return buyer;
    }
    public void setBuyer(Buyer buyer) {
        this.buyer.set(buyer);
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus.get();
    }
    public ObjectProperty<DeliveryStatus> deliveryStatusProperty() {
        return deliveryStatus;
    }
    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus.set(deliveryStatus);
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint.get();
    }
    public ObjectProperty<DeliveryPoint> deliveryPointProperty() {
        return deliveryPoint;
    }
    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint.set(deliveryPoint);
    }

    public Date getCreationDate() {
        return creationDate.get();
    }
    public ObjectProperty<Date> creationDateProperty() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate.set(creationDate);
    }

    public ObservableList<CartedProduct> getCartedProducts() {
        return cartedProducts;
    }
    public void setCartedProducts(ObservableList<CartedProduct> cartedProducts) {
        this.cartedProducts = cartedProducts;
    }



    /* ============== moce code elsewhere =========== */

    // Database column/fields correspondence
    private static final int REFERENCE_FIELD = 0;
    private static final int BUYER_ID_FIELD = 1;
    private static final int DELIVERY_STATUS_FIELD = 2;
    private static final int DELIVERY_POINT_FIELD = 3; //serialized
    private static final int CREATION_DATE_FIELD = 4;
    private static final int PRODUCT_LIST_FIELD = 5; //serialized

    //map for linking objects after
    public static final HashMap<Integer, Order> orderById = new HashMap<>();
    public static final HashMap<Order, Integer> buyerIdByOrder = new HashMap<>();

    public static final HashMap<String, Order> orderByReference = new HashMap<>();

    private static final XSSFSheet sheet = Main.workbook.getSheet("order");
    private static int counterID = 1;

    /**
     * load all product from table 'selledproduct' of XLSX database defined in project.App
     * @return the number of selledproduct loaded
     */
    public static int load() {
        Iterator<Row> rowIt = sheet.rowIterator();
        if (rowIt.hasNext()) rowIt.next(); //skip header

        int count = 0;
        while(rowIt.hasNext()) {
            Row row = rowIt.next();

            int id = row.getRowNum();

            String reference = row.getCell(REFERENCE_FIELD).getStringCellValue();
            int buyerid = (int) row.getCell(BUYER_ID_FIELD).getNumericCellValue();
            DeliveryStatus status = DeliveryStatus.valueOf(row.getCell(DELIVERY_STATUS_FIELD).getStringCellValue());
            //TODO: DeliveryPoint deliveryPoint =  (row.getCell(SHIPPING_TIME_FIELD) == null ? 0 : row.getCell(SHIPPING_TIME_FIELD).getNumericCellValue());

            Date date;
            try {
                date = DateUtils.DATE_FORMATER.parse(row.getCell(CREATION_DATE_FIELD).getStringCellValue());
            } catch (ParseException e) { System.err.println("invalide date format in order's id " + id + ". setting default value"); date = new Date(0); }

            ArrayList<CartedProduct> cartedProduct;
            try {
                cartedProduct = CartedProduct.unstringSerialize(row.getCell(PRODUCT_LIST_FIELD).getStringCellValue());
            } catch (ParseException e) { System.err.println("invalide cart format in order'id " + id + ". setting default value"); cartedProduct = null; }

            Order order = new Order(reference, null, status, null, null, date);

            orderById.put(id, order);
            buyerIdByOrder.put(order, buyerid);
            orderByReference.put(reference, order);

            if (counterID <= id) counterID = id +1; //keep track if the next possible ID

            count++;
        }
        return count;
    }

    /**
     * using object/id map, link SelledProduct object with object associated
     */
    public static void linkModels() {
        for(Order order : orderById.values()) {
            order.setBuyer(Buyer.buyerById.get(buyerIdByOrder.get(order)));
            Buyer.buyerById.get(buyerIdByOrder.get(order)).getOrders().add(order);
        }
    }

    public static void unload() {
        for (Map.Entry<Integer, Order> entry: orderById.entrySet()) {
            Row row = Optional.ofNullable(sheet.getRow(entry.getKey())).orElseGet( () -> sheet.createRow(entry.getKey()));
            Optional.ofNullable(row.getCell(REFERENCE_FIELD)).orElseGet( () -> row.createCell(REFERENCE_FIELD)).setCellValue(entry.getValue().getReference());
            Optional.ofNullable(row.getCell(BUYER_ID_FIELD)).orElseGet( () -> row.createCell(BUYER_ID_FIELD)).setCellValue(entry.getValue().getBuyer().getId());
            Optional.ofNullable(row.getCell(DELIVERY_STATUS_FIELD)).orElseGet( () -> row.createCell(DELIVERY_STATUS_FIELD)).setCellValue(entry.getValue().getDeliveryStatus().name());
            //Optional.ofNullable(row.getCell(DELIVERY_STATUS_FIELD)).orElseGet( () -> row.createCell(DELIVERY_STATUS_FIELD)).setCellValue(entry.getValue().getDeliveryStatus().name());
            Optional.ofNullable(row.getCell(CREATION_DATE_FIELD)).orElseGet( () -> row.createCell(CREATION_DATE_FIELD)).setCellValue(DateUtils.DATE_FORMATER.format(entry.getValue().getCreationDate()));
            Optional.ofNullable(row.getCell(PRODUCT_LIST_FIELD)).orElseGet( () -> row.createCell(PRODUCT_LIST_FIELD)).setCellValue(CartedProduct.stringSerialize(entry.getValue().getCartedProducts()));
        }
    }

    public static String generateUniqueReference() {
        String generated;
        do {
            generated = Generator.UPPER.generate();
        } while (orderByReference.containsKey(generated));
        return generated;
    }

    public static Order register(Order order) {
        return order.register();
    }
    public Order register() {
        if(orderById.containsValue(this)) return null; //check all

        //set reference if not defined
        if (getReference() == null || getReference().isBlank()) this.setReference(generateUniqueReference());
        orderById.put(counterID++, this);

        return this;
    }
}
