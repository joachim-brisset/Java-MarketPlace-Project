package project.gui.models.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.ParseException;
import java.util.Optional;

/**
 * Represent an address
 */
public class Address {

    private static final String SERIAL_SEPARATOR = "%";

    private final IntegerProperty streetNumber = new SimpleIntegerProperty();
    private final StringProperty streetName = new SimpleStringProperty();
    private final StringProperty cityName = new SimpleStringProperty();
    private final IntegerProperty postalCode = new SimpleIntegerProperty();
    private final StringProperty countryName = new SimpleStringProperty();
    private final StringProperty additionalInfos = new SimpleStringProperty();

    public Address(String serializedString) throws ParseException {
        if (!serializedString.startsWith("#{") || !serializedString.endsWith("}#") || serializedString.length() < 4) throw new ParseException("Wrong delimiter", 1);
        String[] fields = serializedString.split(SERIAL_SEPARATOR);

        try {
            setStreetNumber(Integer.parseInt(fields[0]));
            setStreetName(fields[1]);
            setCityName(fields[2]);
            setPostalCode(Integer.parseInt(fields[3]));
            setStreetName(fields[4]);
            setAdditionalInfos(fields[5]);
        } catch (Exception e){
            System.err.println("Can not parse String to address : ");
            throw new ParseException("", 0);
        }
    }

    public Address() {
        this(0, "", "", 0, "", "");
    }

    public Address(int streetNumber, String streetName, String cityName, int postalCode, String countryName, String additionalInfos) {
        this.streetNumber.set(streetNumber);
        this.streetName.set(Optional.ofNullable(streetName).orElse(""));
        this.cityName.set(Optional.ofNullable(cityName).orElse(""));
        this.postalCode.set(postalCode);
        this.countryName.set(Optional.ofNullable(countryName).orElse(""));
        this.additionalInfos.set(Optional.ofNullable(additionalInfos).orElse(""));
    }

    public Address(Address address) {
        this(address.getStreetNumber(), address.getStreetName(), address.getCityName(), address.getPostalCode(), address.getCountryName(), address.getAdditionalInfos());
    }

    /**
     * get a string formated representation of this address
     * @return a string formated address
     */
    public String getStringAddress() {
        return streetNumber.get() + " " + streetName.get() + ", " + postalCode.get() + " " + cityName.get() + " " + countryName.get() + ", " + additionalInfos.get();
    }

    public static String stringSerialize(Address address) {
        if (address == null) address = new Address();
        return "#{" + address.streetNumber.get() + SERIAL_SEPARATOR + address.streetName.get() + SERIAL_SEPARATOR + address.postalCode.get() + SERIAL_SEPARATOR + address.cityName.get() + SERIAL_SEPARATOR + address.countryName.get() + SERIAL_SEPARATOR + address.additionalInfos.get() + "}#";
    }

    public int getStreetNumber() {
        return streetNumber.get();
    }
    public IntegerProperty streetNumberProperty() {
        return streetNumber;
    }
    public void setStreetNumber(int streetNumber) {
        if (streetNumber <= 0) throw new IllegalArgumentException("Provided integer for street number is inferior to 1");
        this.streetNumber.set(streetNumber);
    }

    public String getStreetName() {
        return streetName.get();
    }
    public StringProperty streetNameProperty() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName.set(streetName);
    }

    public String getCityName() {
        return cityName.get();
    }
    public StringProperty cityNameProperty() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName.set(cityName);
    }

    public int getPostalCode() {
        return postalCode.get();
    }
    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }
    public void setPostalCode(int postalCode) {
        if (postalCode < 1) throw new IllegalArgumentException("Provided Integer for postal code is inferior to 1");

        this.postalCode.set(postalCode);
    }

    public String getCountryName() {
        return countryName.get();
    }
    public StringProperty countryNameProperty() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName.set(countryName);
    }

    public String getAdditionalInfos() {
        return additionalInfos.get();
    }
    public StringProperty additionalInfosProperty() {
        return additionalInfos;
    }
    public void setAdditionalInfos(String additionalInfos) {
        this.additionalInfos.set(additionalInfos);
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetNumber=" + streetNumber +
                ", streetName=" + streetName +
                ", cityName=" + cityName +
                ", postalCode=" + postalCode +
                ", countryName=" + countryName +
                ", additionalInfos=" + additionalInfos +
                '}';
    }

    public boolean isEmpty() {
        return streetNumber.get() == 0 && streetName.get().equals("") && cityName.get().equals("") && postalCode.get() == 0 && countryName.get().equals("") && additionalInfos.get().equals("");
    }

}
