package project.delivery;

import project.utils.Address;

/**
 * Represent a place which store delivered parcel
 */
public class RelayPoint extends DeliveryPoint {

	/** name of the place */
	private String name;

	/**
	 * Basic constructor
	 * @param address
	 * @param name
	 * TODO: set attributes with setter ?
	 */
	private RelayPoint(Address address, String name) {
		super(address);
		this.name = name;
	}

	/** name getter */
	public String getName() { 
		return name; 
	} 

	/**
	 * name setter
	 * @param name a String
	 * @throws IllegalArgumentException when name length is < 2
	 */
	public void setName(String name) throws IllegalArgumentException{
		if (name == null || name.length() <= 1) throw new IllegalArgumentException("Name length must be superior to 1 character");
		this.name = name; 
	}
} 