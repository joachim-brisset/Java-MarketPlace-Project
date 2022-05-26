package project.delivery;

import project.marketplace.Order;
import project.utils.Address;

/**
 * Represent a place where a parcel can be deliver
 */
public class DeliveryPoint {

	/** address where to deliverer */
	private Address address;

	/**
	 * Basic constructor
	 * @param address address of the deliveryPoint
	 */
	protected DeliveryPoint(Address address) {
		this.address = address;
	} 

	/** address getter */
	public Address getAddress() {
		return address; 
	} 

	/**
	 * address setter
	 * @param address Address
	 * @throws IllegalArgumentException when address is null
	 */
	public void setAddress(Address address) throws IllegalArgumentException{
		if (address == null) throw new IllegalArgumentException("address must not be null");
		this.address = address; 
	}

	/**
	 * update delivery status
	 * TODO: review this code
	 * @param order
	 * @implNote not implemented yet
	 */
	public void delivered (Order order){

	} 
} 
