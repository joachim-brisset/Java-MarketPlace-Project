package marketplace.seller;

import marketplace.Company;
import marketplace.utils.Address;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class ProfessionalExternSeller extends ExternSeller{
    private Company company;

    @Override
    public void loadData() throws IOException, InvalidFormatException {
        super.loadData();
        //TODO:
    }

    @Override
    public String getSellerName() {
        return company.getName() + "," + getFamilyName() + " " + getFirstName();
    }

    @Override
    public Address getSellerAddress() {
        return company.getAddress();
    }
}
