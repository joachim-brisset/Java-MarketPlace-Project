package project.delivery;

import project.User;

import java.util.ArrayList;

public class Deliverer extends User {

    private int delivererID;
    private ArrayList<Parcel> parcels;
    private String drivingLicence;

    public int getDelivererID() {
        return delivererID;
    }

    public void setDelivererID(int delivererID) {
        this.delivererID = delivererID;
    }

    public ArrayList<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(ArrayList<Parcel> parcels) {
        this.parcels = parcels;
    }

    public String getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(String drivingLicence) {
        this.drivingLicence = drivingLicence;
    }
}
