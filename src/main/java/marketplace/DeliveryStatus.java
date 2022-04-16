package marketplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum DeliveryStatus {
	CANCELED(),
	WAITING(),
	DELIVERING(),
	DELIVERED();
	
	private ArrayList<Parcel> pcl = new ArrayList<>();
	
	public List<Parcel> getpcl() {
		return Collections.unmodifiableList(pcl);
	}
}
