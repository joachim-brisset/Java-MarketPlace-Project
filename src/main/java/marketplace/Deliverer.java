public class Deliverer{
	private String familyName;
	private Address address;
	private String drivingLicence;
	private planning;
	
	public Deliverer(String familyName, Address address, String drivingLicence, planning) {
		this.familyName= familyName;
		this.address= address;
		this.drivingLicence= drivingLicence;
		this.planning= planning;
	}
	
	public boolean notifyClient(Parcel) {
		return returnParcel();
	}
	
	public String getfamilyName() {
		return familyName;
	}
	public void setfamilyName() {
		this.familyName = familyName;
	}
	public Address getaddress() {
		return address;
	}
	public void setaddress() {
		this.address = address;
	}
	public String getdrivingLicence() {
		return drivingLicence;
	}
	public void setdrivingLicence() {
		this.drivingLicence = drivingLicence;
	}
	
}