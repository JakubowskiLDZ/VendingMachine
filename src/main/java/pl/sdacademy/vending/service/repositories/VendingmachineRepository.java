package pl.sdacademy.vending.service.repositories;

import pl.sdacademy.vending.model.VendingMachine;

import java.util.Optional;

public interface VendingmachineRepository {
    VendingMachine save(VendingMachine vendingMachine);
    Optional<VendingMachine>load();

}
