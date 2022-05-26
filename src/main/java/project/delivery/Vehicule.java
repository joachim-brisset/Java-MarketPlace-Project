package project.delivery;

public class Vehicule {

    private String drivingLicence;
    private Dimension chestDimension;
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
    /**
     * @return the chestDimension
     */
    public Dimension getChestDimension() {
        return chestDimension;
    }
    /**
     * @param chestDimension the chestDimension to set
     */
    public void setChestDimension(Dimension chestDimension) {
        this.chestDimension = chestDimension;
    }
}