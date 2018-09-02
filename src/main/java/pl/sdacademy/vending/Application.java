package pl.sdacademy.vending;

import pl.sdacademy.vending.controler.CustomerOperationControler;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.util.Configuration;
import pl.sdacademy.vending.util.ProperiesFileConfiguration;

public class Application {
    private final CustomerOperationControler customerOperationControler;

    public Application(){

        Configuration configuration = ProperiesFileConfiguration.getInstance();
        VendingMachine vendingMachine = new VendingMachine(configuration);
        customerOperationControler = new CustomerOperationControler(vendingMachine);
    }

    public void start(){
        customerOperationControler.printMachine();
    }
}
