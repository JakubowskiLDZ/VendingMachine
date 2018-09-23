package pl.sdacademy.vending;

import pl.sdacademy.vending.controller.CustomerOperationController;
import pl.sdacademy.vending.controller.EmplyeeController;
import pl.sdacademy.vending.service.DefaultEmployeeService;
import pl.sdacademy.vending.controller.services.EmployeeService;
import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.repository.HardDriveVendingMachineRepository;
import pl.sdacademy.vending.service.repositories.VendingmachineRepository;
import pl.sdacademy.vending.util.Configuration;
import pl.sdacademy.vending.util.PropertiesFileConfiguration;

import java.util.Optional;
import java.util.Scanner;

/**
 * Klasa, która zarządza zleżnościami w naszym projekcie, oraz udostępnia główną metodę startującą aplikację.
 */
public class Application {
    // pole do przechowywania głównego kontrolera
    private final CustomerOperationController customerOperationController;
    private final EmplyeeController emplyeeController;


    /**
     * Kostruktor dba o ustawienie wszystkich wymaganych zależności w klasach projektu.
     */
    public Application() {
        // najpierw tworzymy klasy, które nie posiadają żadnych zależności - w tym przypadku konfigurację.
        Configuration configuration = PropertiesFileConfiguration.getInstance();
        // Mając konfigurację aplikacji, możemy utworzyć VendingMachine, który jej wymagał. Wcześniej nie było to możliwe,
        // ponieważ najepierw trzeba było stworzyć wymagany obiekt


        VendingmachineRepository vendingMachineRepository = new HardDriveVendingMachineRepository(configuration);
        // po utworzeniu VendingMachine, możemy przekazać go do konstruktora CustomerOperationController, tworząc tym samym
        // instancję głównego kontrolera. Zapisujemy tę instancję do pola w klasie.
        customerOperationController = new CustomerOperationController(vendingMachineRepository);

        EmployeeService employeeService = new DefaultEmployeeService(vendingMachineRepository, configuration);

        this.emplyeeController = new EmplyeeController(employeeService);
        EmplyeeController emplyeeController = this.emplyeeController;
        CustomerOperationController customerOperationController = new CustomerOperationController(vendingMachineRepository);
    }

    /**
     * Metoda uruchamiająca Automat Sprzedający.
     */
    public void start() {
        int userSelection = -1;
        do {
            customerOperationController.printMachine();
            printMenu();
            userSelection = getUserInput();
            switch (userSelection) {
                case 1:
                    System.out.print("Select product: ");
                    String selectedSymbol = new Scanner(System.in).nextLine();
                    Optional<Product> boughtProduct = customerOperationController.buyProduct(selectedSymbol);
                    String productName = boughtProduct
                            .map(Product::getName)
                            .orElse("Sold out");
                    System.out.println(productName);
                    break;
                case 9:
                    System.out.println("Bye");
                    break;
                case 0:
                    startServiceMenu();
                    break;
                default:
                    System.out.println("Invalid selection");
            }
            // pobierz wybór użytkownika i zapisz w zmiennej userSelection
            // jeżeli użytkownik wybrał 1 - to pobierz od niego symbol tacki
            //      i kup produkt
            // jeżeli użytkownik wybrał 9, to wyświetl pożegnanie
            // jeżeli użytkownik wybrał dowolną inną opcję (nieistniejącą)
            //      to wyświetl komunikat błednego wyboru
        } while (userSelection != 9);
    }

    private void startServiceMenu() {
        int userSelection = -1;
        do {
            customerOperationController.printMachine();
            printServiceMenu();
            userSelection = getUserInput();
            switch (userSelection) {
                case 1:
                    emplyeeController.addTray();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 9:
                    System.out.println("Leaving Service Menu");
                    break;
                default:
                    System.out.println("Leaving Service Menu");
            }
            // pobierz wybór użytkownika i zapisz w zmiennej userSelection
            // jeżeli użytkownik wybrał 1 - to pobierz od niego symbol tacki
            //      i kup produkt
            // jeżeli użytkownik wybrał 9, to wyświetl pożegnanie
            // jeżeli użytkownik wybrał dowolną inną opcję (nieistniejącą)
            //      to wyświetl komunikat błednego wyboru
        } while (userSelection != 9);

    }

    private void printMenu() {
        System.out.println("1. Buy product");
        System.out.println("9. Exit");
        System.out.println("0. Service Menu");
    }

    private void printServiceMenu() {
        System.out.println("1. Add Tray");
        System.out.println("2. Remove Tray");
        System.out.println("3. Add product to Tray");
        System.out.println("4. Remove Product from tray");
        System.out.println("5. Change Price");
        System.out.println("9. Exit");


    }

    private int getUserInput() {
        System.out.print("Your selection: ");
        String userText = new Scanner(System.in).nextLine();
        try {
            int userNumber = Integer.parseInt(userText);
            return userNumber;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
