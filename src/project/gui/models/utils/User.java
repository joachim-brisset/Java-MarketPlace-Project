package project.gui.models.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();

    public User(String firstName, String lastName) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                '}';
    }
}
