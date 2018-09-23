package pl.sdacademy.vending.service;

import pl.sdacademy.vending.controller.services.EmployeeService;
import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.model.Tray;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.service.repositories.VendingmachineRepository;
import pl.sdacademy.vending.util.Configuration;

import java.util.List;
import java.util.Optional;

public class DefaultEmployeeService implements EmployeeService {

    private final VendingmachineRepository vendingmachineRepository;
    private Configuration configuration;

    public DefaultEmployeeService(VendingmachineRepository vendingmachineRepository, Configuration configuration) {

        this.vendingmachineRepository = vendingmachineRepository;
        this.configuration = configuration;
    }

    @Override
    public String addTray(Tray tray) {
        VendingMachine vendingMachine = vendingmachineRepository.load()
                .orElse(new VendingMachine(configuration));

        boolean trayWasAdded = vendingMachine.placeTray(tray);
        vendingmachineRepository.save(vendingMachine);


        if (trayWasAdded) {
            return null;
        } else {
            return "Could not add tray with symbol " + tray.getSymbol();
        }

    }

    @Override
    public String removeTray(String symbol) {
        Optional<VendingMachine> loadedMachine = vendingmachineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            Optional<Tray> removedTray = machine.removeTrayWithSymbol(symbol);
            if (removedTray.isPresent()) {
                vendingmachineRepository.save(machine);
                return null;
            } else {
                return "Could not remove tray";
            }

        } else {
            return "NO Machine configured";
        }
    }

    @Override
    public String addProducts(String symbol, String productName, Integer amount) {
        Optional<VendingMachine> machineOptional = vendingmachineRepository.load();
        if (machineOptional.isPresent()) {
            VendingMachine machine = machineOptional.get();
            for (int i = 0; i < amount; i++) {
                Product product = new Product(productName);
                machine.addProductToTray(symbol, product);
            }
            vendingmachineRepository.save(machine);
            return null;
        } else {
            return "No Machine configured";
        }
    }

    @Override
    public String emptyTray(String symbol) {
        Optional<VendingMachine> loadedMachine = vendingmachineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            Optional<Tray> obtainedTray = machine.getTrayForSymbol(symbol);
            if (obtainedTray.isPresent()) {
                List<Product> removedProducts = obtainedTray.get().purge();
                vendingmachineRepository.save(machine);
                System.out.println("Removed" + removedProducts.size() + "products");
                return null;
            } else {
                return "No tray with provided symbol";
            }
        } else {
            return "Vending machine not configurated";
        }

    }

}
