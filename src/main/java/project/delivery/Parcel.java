package project.delivery;

import project.marketplace.Order;
import project.marketplace.Product;

public class Parcel {

    private int id = 0;
    private Product product;
    private DeliveryStatus status;
    private Order order;
    private Dimension dimension;
    private double weight;

    public Parcel(Order order, Product product) {
        this.order = order;
        this.product = product;
        this.status = DeliveryStatus.WAITING;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void unregister() {
    }
}
