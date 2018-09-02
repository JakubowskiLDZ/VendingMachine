package pl.sdacademy.vending.controler;

import pl.sdacademy.vending.model.VendingMachine;


public class CustomerOperationControler {
    private VendingMachine machine;

    public CustomerOperationControler() {
        machine = new VendingMachine();
    }

    public void printMachine() {
        for (int row = 0; row <= machine.rowsSize(); row++) {
            for (int col = 0; col < machine.colSize(); col++) {
                printUpperBoundryForCell(row, col);

            }
            System.out.println();

            for (int col = 0; col < machine.colSize(); col++) {
                //drukuj symbole
                printSymbolForCell(row, col);
            }
            System.out.println();
            for (int col = 0; col < machine.colSize(); col++) {
                //drukuj belke
                printLowerBoundryForCell(row, col);
            }
            System.out.println();
        }

    }

    private void printUpperBoundryForCell(int row, int col) {
        System.out.print("+--------+");
    }

    private void printLowerBoundryForCell(int row, int col) {
        System.out.print("+--------+");
    }

    private void printSymbolForCell(int row, int col) {
        char rowSymbol = (char) ('A' + row);
        int colSymbol = col + 1;
        System.out.print("|   " + rowSymbol + colSymbol + "   |");
    }


}