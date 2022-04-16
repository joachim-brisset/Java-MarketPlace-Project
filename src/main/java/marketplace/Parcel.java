package marketplace;

import marketplace.utils.Dimension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import marketplace.utils.Address;

public class Parcel {
	private long ID;
	private Dimension dimension;
	private double weight;
	private Address shippingAddress;
	private Date shippingDate;
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public Dimension getDimension() {
		return dimension;
	}
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public Address getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public Date getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	
	private ArrayList<Deliverer> deliverer = new ArrayList<>();
	private ArrayList<DeliveryStatus> dlvs = new ArrayList<>();
	private ArrayList<Order> order = new ArrayList<>();
	private ArrayList<SelledProduct> selledproduct = new ArrayList<>();
	
	public List<Deliverer> getdeliverer() {
		return Collections.unmodifiableList(deliverer);
	}
	
	public List<DeliveryStatus> getdlvs() {
		return Collections.unmodifiableList(dlvs);
	}
	
	public List<Order> getorder() {
		return Collections.unmodifiableList(order);
	}
	
	public List<SelledProduct> getselledproduct() {
		return Collections.unmodifiableList(selledproduct);
	}
	
}
