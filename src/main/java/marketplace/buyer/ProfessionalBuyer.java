package marketplace.buyer;

import marketplace.utils.Address;
import marketplace.Company;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class ProfessionalBuyer extends Buyer{

    private Company company;

    public ProfessionalBuyer() {}

    @Override
    public void loadData() throws IOException, InvalidFormatException {
        super.loadData();
        // TODO: 14/04/2022 add company to database 
    }

    @Override
    public String getBuyerName() {
        return company.getName() + "," + getFamilyName() + " " + getFirstName();
    }

    @Override
    public Address getBuyerAddress() {
        return company.getAddress();
    }
}
