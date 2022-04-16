package main.java.marketplace;

public class Order {
	private long ID;
	private DeliveryPoint deliveryPoint;
	private int delivered;
	private int received;

	public Order(long ID, DeliveryPoint deliveryPoint, int delivered , int received) {
		this.ID = ID;
		this.deliveryPoint = deliveryPoint;
		this.delivered = delivered;
		this.received = received;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public DeliveryPoint getDeliveryPoint() {
		return deliveryPoint;
	}

	public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	
	public int getDelivered() {
		return delivered;
	}

	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}
	
	public int getReceived() {
		return received;
	}

	public void setReceived(int received) {
		this.received = received;
	}

	public void cancel() {
		
	}
}
