package project.marketplace;

import java.util.HashMap;

public class Order { 
	private long ID; 
	private DeliveryPoint deliveryPoint; 
	private int delivered; 
	private int received; 
	private HashMap<SelledProduct, Integer> productQuantity = new HashMap<>();
    private Buyer buyer;

	public Order(long ID, DeliveryPoint deliveryPoint, int delivered , int received , HashMap productQuantity, Buyer buyer) { 
		this.ID = ID; 
		this.deliveryPoint = deliveryPoint; 
		this.delivered = delivered; 
		this.received = received; 
		this.productQuantity = productQuantity;
		this.buyer = buyer;
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
	
	public HashMap<SelledProduct, Integer> getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(HashMap<SelledProduct, Integer> productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
 
	public void cancel() { 
		 
	} 
}
