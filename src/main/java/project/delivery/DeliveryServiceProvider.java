package project.delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeliveryServiceProvider{

    private ArrayList<Deliverer> deliverers = new ArrayList<>();
    private ArrayList<Vehicule> vehicules = new ArrayList<>();

    private String name;


    public DeliveryServiceProvider(String name) {
        this.name = name;
    }


    public List<Deliverer> getDeliverers() {
        return Collections.unmodifiableList(deliverers);
    }

    public List<Vehicule> getVehicules() {
        return Collections.unmodifiableList(vehicules);
    }


}
