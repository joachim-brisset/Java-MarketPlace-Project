package marketplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import marketplace.utils.Address;

public class Deliverer {
	private String familyName;
	private Address address;
	private String drivingLicence;
	
	/**
	 * @return the familyName
	 */
	public String getFamilyName() {
		return familyName;
	}
	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	/**
	 * @return the drivingLicence
	 */
	public String getDrivingLicence() {
		return drivingLicence;
	}
	/**
	 * @param drivingLicence the drivingLicence to set
	 */
	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}
	
	private ArrayList<Parcel> pcl = new ArrayList<>();
	private ArrayList<DeliveryServiceProvider> dsp = new ArrayList<>();
	private ArrayList<User> user = new ArrayList<>();
	
	public List<User> getuser() {
		return Collections.unmodifiableList(user);
	}
	
	public List<Parcel> getpcl() {
		return Collections.unmodifiableList(pcl);
	}
	
	public List<DeliveryServiceProvider> getdsp() {
		return Collections.unmodifiableList(dsp);
	}
}
