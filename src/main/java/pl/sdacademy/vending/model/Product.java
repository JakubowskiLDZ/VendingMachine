package pl.sdacademy.vending.model;

import java.io.Serializable;

/**
 * Klasa reprezentująca produkt, który można kupić. Przechowuje nazwę produktu.
 */
public class Product implements Serializable {
    public static final long serialVersionUID =1L;
    private final String name;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}