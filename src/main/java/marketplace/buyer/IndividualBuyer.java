package marketplace.buyer;

import marketplace.utils.Address;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class IndividualBuyer extends Buyer{

    public static int addressCol = 7;

    private Address livingAddress = null;

    @Override
    public void loadData() throws IOException, InvalidFormatException {
        super.loadData();
        livingAddress = new Address(getUserData(addressCol)); //TODO: deserialization
    }

    @Override
    public String getBuyerName() {
        return getFamilyName() + " " + getFirstName();
    }

    @Override
    public Address getBuyerAddress() {
        return livingAddress;
    }

    public Address getLivingAddress() {
        return livingAddress;
    }

    public void setLivingAddress(Address livingAddress) throws IOException, InvalidFormatException {
        if (isConnected()) {
            setUserData(addressCol, livingAddress.serialize());
            this.livingAddress = livingAddress;
        }
    }
}
