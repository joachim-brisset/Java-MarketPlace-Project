package project.marketplace;

import project.delivery.DeliveryPoint;
import project.delivery.DeliveryStatus;
import project.delivery.Parcel;

import java.util.*;
import java.util.Map.Entry;

/**
 * Represent an order
 */
public class Order {

    /**
     * Counter variable for id
     */
    private static int counter = 0;

    /** id of the order */
    private int id;
    /** buyer of the order */
    private Buyer buyer;

    /** status of the delivery */
    private DeliveryStatus deliveryStatus;
    /** place to deliverer the order */
    private DeliveryPoint deliveryPoint;

    /** list of all product of the order */
    private HashMap<SelledProduct, Integer> orderedProducts;
    /** list of all parcel of the order
     * TODO: too much ?
     */
    private ArrayList<Parcel> parcels;

    /** Basic Constructor
     * @param id the order's id
     * @param buyer the order's buyer
     * @param deliveryStatus the order's status
     * @param deliveryPoint the order's delivery point
     * @param orderedProduct the list of ordered product in this order
     */
    private Order(int id, Buyer buyer, DeliveryStatus deliveryStatus, DeliveryPoint deliveryPoint, HashMap<SelledProduct, Integer> orderedProduct) {
        this(buyer, deliveryStatus, deliveryPoint, orderedProduct);
        this.id = id;
    }

    /** Public construstor
     * This order does not have an id yet. Registering it with register will.
     * @param buyer
     * @param deliveryStatus
     * @param deliveryPoint
     * @param orderedProduct
     */
    public Order(Buyer buyer, DeliveryStatus deliveryStatus, DeliveryPoint deliveryPoint, HashMap<SelledProduct, Integer> orderedProduct) {
        this.buyer = buyer;
        this.deliveryStatus = deliveryStatus;
        this.deliveryPoint = deliveryPoint;
        this.orderedProducts = orderedProduct;

        for (Entry<SelledProduct, Integer> entry : orderedProduct.entrySet()) {
            for(int i = 0; i < entry.getValue(); i++) {
                Parcel parcel = new Parcel(this, entry.getKey().getProduct());
                parcels.add(parcel);
            }
        }
    }

    /**
     * Register an order
     * give it an id so as to load it
     * TODO: exception ?
     */
    public void register() {
        this.buyer.getOrders().add(this);
        this.id = counter++;
    }

    /** Cancel an order
     * unschedule all parcel and abandone order delivery
     * TODO: why unregister parcel ?
     */
    public boolean cancel() {
        for (Parcel parcel : parcels) {
            if(parcel.getStatus() != DeliveryStatus.WAITING) return false;
        }

        deliveryStatus = DeliveryStatus.CANCELED;
        for (Entry<SelledProduct, Integer> entry : orderedProducts.entrySet()) {
            entry.getKey().setSelledCount(entry.getKey().getSelledCount() - entry.getValue());
            entry.getKey().setStock(entry.getKey().getStock() + entry.getValue());
        }

        for (Parcel parcel : parcels) {
            parcel.unregister();
        }

        return true;
    }

    /** id getter */
    public int getId() {
        return id;
    }
    /** id setter
     * @param id
     * TODO : check if id is unique ?
     */
    private void setId(int id) {
        this.id = id;
    }
    /** buyer getter */
    public Buyer getBuyer() {
        return buyer;
    }

    /** buyer setter
     * @param buyer
     * TODO: private ?
     */
    private void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }
    /** status getter */
    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }
    /** status setter */
    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    /** delivery point getter */
    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }
    /** delivery point setter */
    public Map<SelledProduct, Integer> getOrderedProductList() { return  Collections.unmodifiableMap(orderedProducts); }
    public List<Parcel> getParcelsList() {
        return Collections.unmodifiableList(parcels);
    }
}
