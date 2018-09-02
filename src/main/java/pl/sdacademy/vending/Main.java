package pl.sdacademy.vending;

import pl.sdacademy.vending.controler.CustomerOperationControler;
import pl.sdacademy.vending.util.Configuration;

public class Main {
    public static void main(String[] args) {
        new CustomerOperationControler().printMachine();
        new Configuration();
    }
}
