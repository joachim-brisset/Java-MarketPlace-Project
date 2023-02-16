package project.gui.models.delivery;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import project.gui.models.utils.Address;

public class DeliveryPoint {

    private ObjectProperty<Address> address = new SimpleObjectProperty<>(null);

    public DeliveryPoint(Address address) {
        this.address.set(address);
    }

    public Address getAddress() {
        return address.get();
    }
    public ObjectProperty<Address> addressProperty() {
        return address;
    }
    public void setAddress(Address address) {
        this.address.set(address);
    }
}
