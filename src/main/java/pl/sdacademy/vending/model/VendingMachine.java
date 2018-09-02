package pl.sdacademy.vending.model;

import pl.sdacademy.vending.util.Configuration;

public class VendingMachine {
    private final Configuration configuration;


    public VendingMachine() {

        configuration = Configuration.getInstance();
    }

    public Long rowsSize(){
        return configuration.getProperty("machine.size.row",6L);
    }
    public Long colSize(){
        return configuration.getProperty("machine.size.cols", 4L);
    }
}
