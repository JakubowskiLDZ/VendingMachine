package pl.sdacademy.vending.util;

public interface Configuration {
    String getProperty(String propertyName, String deafaultValue);
    Long getProperty(String propertyName, Long defaultValue);


}
