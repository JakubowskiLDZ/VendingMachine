package pl.sdacademy.vending.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProperiesFileConfigurationTest {

    @Test
    public void shouldReturnTextPropertyFromConfiguraton(){
        //given
        ProperiesFileConfiguration configuration = ProperiesFileConfiguration.getInstance();
        String propertyName= "property.text.value";

        //when
        String retrivedValue = configuration
                .getProperty(propertyName,"DEFAULT_VALUE");


        //then
        assertEquals("some text",retrivedValue);
    }

    @Test
    public void shouldReturnedLongPropertyFromConfiguration(){
        ProperiesFileConfiguration configuration = ProperiesFileConfiguration.getInstance();
        String propertyName = "property.long.value";
        Long defaultValue = 1337L;
        //when
        Long retrievedNumber = configuration
                .getProperty(propertyName,2L);
        //then
        assertEquals((Long)1337L,retrievedNumber);
    }

    @Test
    public void shouldReturnDefaultValueWhenTextPropertyDoesNotExist(){
        //given
        ProperiesFileConfiguration configuration = ProperiesFileConfiguration.getInstance();
        String propertyName = "notExisiting";
        String defaultValue = "I'm Default";
        //when
        String retrievedValue = configuration.getProperty(propertyName, defaultValue);
        //then
        assertEquals(defaultValue, retrievedValue);

    }
    @Test
    public void shouldReturnDefaultValueWhenLongPropertyDoesNotExist() {
        //given
        ProperiesFileConfiguration configuration = ProperiesFileConfiguration.getInstance();
        String propertyName = "notExisiting";
        Long defaultValue = 555L;
        //when
        Long retrievedValue = configuration.getProperty(propertyName, defaultValue);
        //then
        assertEquals(defaultValue, retrievedValue);

    }
}