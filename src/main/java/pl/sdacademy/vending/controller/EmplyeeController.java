package pl.sdacademy.vending.controller;

import pl.sdacademy.vending.controller.services.EmployeeService;
import pl.sdacademy.vending.model.Tray;

import java.util.Scanner;

public class EmplyeeController {
    private final EmployeeService employeeService;
    private Scanner scanner = new Scanner(System.in);

    public EmplyeeController(EmployeeService employeeService){

        this.employeeService = employeeService;
    }
    public void addTray(){
        System.out.println("New tray symbol: ");
        String providedSymbol = getStringUserInput();

        System.out.println("New tray price: ");

        String providePrice = getStringUserInput();
        Long convertedPrice = (long)(Double.parseDouble(providePrice) * 100);

        Tray tray = Tray.builder(providedSymbol).price(convertedPrice).build();

        String errorMessage = employeeService.addTray(tray);
        if(errorMessage != null){
            System.out.println(errorMessage);
        }else{
            System.out.println("Tray added");
        }

    }
    public void removeTray(){
        System.out.println("Removed tray symbol");
        String userprovidedSymbol = getStringUserInput();
        String message = employeeService.removeTray(userprovidedSymbol);
        if( message != null){
            System.out.println(message);
        }else{
            System.out.println("Tray removed");
        }


    }

    private String getStringUserInput(){
        return scanner.nextLine();

    }
}
