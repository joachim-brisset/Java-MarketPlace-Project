package marketplace.utils;

import java.util.Objects;

public class Address {
    private int streetNumber;
    private String streetName;
    private String cityName;
    private int postalCode;
    private String countryName;
    private String additionalInfos;

    public Address(int streetNumber, String streetName, String cityName, int postalCode, String countryName, String additionalInfos) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.cityName = cityName;
        this.postalCode = postalCode;
        this.countryName = countryName;
        this.additionalInfos = additionalInfos;
    }

    public Address(String userData) {
    }

    public String getStringAddress() {
        return streetNumber + " " +
                streetName + " " +
                postalCode + " " +
                cityName + " " +
                countryName + ", " +
                additionalInfos;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        if(streetNumber <= 0) throw new IllegalArgumentException("street number must be a positive integer !");
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        if(streetName == null || streetName.length() == 0) throw new IllegalArgumentException("street name must be a non null string !");
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        if(cityName == null || cityName.length() == 0) throw new IllegalArgumentException("city name must be a non null string !");
        this.cityName = cityName;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        //TODO: verify condition on postalCode (currently unknown)
        this.postalCode = postalCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        if(countryName == null || countryName.length() == 0) throw new IllegalArgumentException("country name must be a non null string !");
        this.countryName = countryName;
    }

    public String getAdditionalInfos() {
        return additionalInfos;
    }

    public void setAdditionalInfos(String additionalInfos) {
        this.additionalInfos = additionalInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return streetNumber == address.streetNumber && postalCode == address.postalCode && streetName.equals(address.streetName) && cityName.equals(address.cityName) && countryName.equals(address.countryName) && Objects.equals(additionalInfos, address.additionalInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetNumber, streetName, cityName, postalCode, countryName, additionalInfos);
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetNumber=" + streetNumber +
                ", streetName='" + streetName + '\'' +
                ", City='" + cityName + '\'' +
                ", postalCode=" + postalCode +
                ", Country='" + countryName + '\'' +
                ", additionalInfos='" + additionalInfos + '\'' +
                '}';
    }

    public String serialize() {
        return "";
    }
}