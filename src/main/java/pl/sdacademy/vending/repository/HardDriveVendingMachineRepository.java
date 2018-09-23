package pl.sdacademy.vending.repository;

import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.service.repositories.VendingmachineRepository;
import pl.sdacademy.vending.util.Configuration;

import java.io.*;
import java.util.Optional;

public class HardDriveVendingMachineRepository implements VendingmachineRepository {
    private final String fileLocalisationName;


    public HardDriveVendingMachineRepository(Configuration config) {
        fileLocalisationName = config.getProperty(
                "repository.location.vendingMachine", "VenMach.ser");
    }


    @Override
    public VendingMachine save(VendingMachine vendingMachine) {
        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(new FileOutputStream(fileLocalisationName))) {
            objectOutputStream.writeObject(vendingMachine);
            return vendingMachine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<VendingMachine> load() {
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(fileLocalisationName))) {
            VendingMachine readVendingMachine = (VendingMachine) objectInputStream.readObject();
            return Optional.ofNullable(readVendingMachine);
        } catch (ClassNotFoundException | IOException e) {
        }
        return Optional.empty();
    }
}
