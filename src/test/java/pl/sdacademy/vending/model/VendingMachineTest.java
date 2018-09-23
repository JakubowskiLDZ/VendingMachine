package pl.sdacademy.vending.model;

import org.junit.Test;
import pl.sdacademy.vending.util.Configuration;
import pl.sdacademy.vending.util.PropertiesFileConfiguration;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VendingMachineTest {
    // nazwa parametru ilości rzędów
    private static final String PARAM_NAME_COLS = "machine.size.cols";
    // nazwa parametru ilości kolumn
    private static final String PARAM_NAME_ROWS = "machine.size.rows";

    @Test
    public void shouldBeAbleToCreateMachineWithProperSize() {
        // given
        // aby przeprowadzić test VendingMachine, musimy najpierw wytworzyć wymaganą zależność. Nie chcemy tworzyć całego
        // obiektu konfiguracji, wobec tego stworzy coś, co udaje konfigurację - mocka
        Configuration mockedConfig = mock(Configuration.class);
        // definiujemy zachowanie mocka, mówiące o tym, że gdy ktoś (nasz program) wywoła na nim operację getProperty
        // oraz przekaże jako pierwszy parametr wartość równą wartości zapisanej w PARAM_NAME_COLS, a jako drugi parametr
        // dowolną wartość typu LONG, to zwróć wartość 8.
        when(mockedConfig.getProperty(eq(PARAM_NAME_COLS), anyLong()))
                .thenReturn(8L);
        // definicja zachowania w chwili, gdy ktoś chce pobrać wartość dla PARAM_NAME_ROWS. Metody eq() oraz anyLong() są
        // nazywane Matcherami, czyli "wzorcami dopasowania"
        when(mockedConfig.getProperty(eq(PARAM_NAME_ROWS), anyLong()))
                .thenReturn(14L);

        // when
        // utowrzenie testowego automatu sprzedającego, do któego przekazujemy sztuczną konfigurację.
        VendingMachine testedMachine = new VendingMachine(mockedConfig);

        // then
        // sprawdzenie, czy automat używa przekazanej przez nas konfiguracji.
        assertEquals((Long) 8L, testedMachine.colsSize());
        assertEquals((Long) 14L, testedMachine.rowsSize());
    }

    // w adnotacji Test definiujemy, jaki wyjątek powinien zostać wyrzucony.
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenThereAreTooManyRows() {
        // given
        // towrzymy sztuczną konfigurację
        Configuration mockedConfig = mock(Configuration.class);
        // definiujemy zwracane przez konfigurację wartości
        when(mockedConfig.getProperty(eq(PARAM_NAME_COLS), anyLong()))
                .thenReturn(8L);
        // maksymalną poprawną wartością dla ilości wierszy jest 26. Ta konfiguracja zwróci zbyt dużą ilość wierszy.
        when(mockedConfig.getProperty(eq(PARAM_NAME_ROWS), anyLong()))
                .thenReturn(27L);

        // when
        // testujemy tylko to, czy uda się utworzyć automat
        new VendingMachine(mockedConfig);
    }

    // w adnotacji Test definiujemy, jaki wyjątek powinien zostać wyrzucony.
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenThereAreTooFewRows() {
        // given
        // towrzymy sztuczną konfigurację
        Configuration mockedConfig = mock(Configuration.class);
        // definiujemy zwracane przez konfigurację wartości
        when(mockedConfig.getProperty(eq(PARAM_NAME_COLS), anyLong()))
                .thenReturn(8L);
        // minimalną poprawną wartością dla ilości wierszy jest 1. Ta konfiguracja zwróci zbyt dużą ilość wierszy.
        when(mockedConfig.getProperty(eq(PARAM_NAME_ROWS), anyLong()))
                .thenReturn(0L);

        // when
        // testujemy tylko to, czy uda się utworzyć automat
        new VendingMachine(mockedConfig);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenThereAreTooManyCols() {
        // given
        Configuration mockedConfig = mock(Configuration.class);
        // 10 jest pierwsza niepoprawna wartoscia
        when(mockedConfig.getProperty(eq(PARAM_NAME_COLS), anyLong()))
                .thenReturn(10L);
        when(mockedConfig.getProperty(eq(PARAM_NAME_ROWS), anyLong()))
                .thenReturn(16L);

        // when
        // testujemy tylko to, czy uda się utworzyć automat
        new VendingMachine(mockedConfig);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenThereAreTooFewCols() {
        // given
        Configuration mockedConfig = mock(Configuration.class);
        // nie mozna utowrzyc automatu mniejszego niz 1
        when(mockedConfig.getProperty(eq(PARAM_NAME_COLS), anyLong()))
                .thenReturn(0L);
        when(mockedConfig.getProperty(eq(PARAM_NAME_ROWS), anyLong()))
                .thenReturn(21L);

        // when
        // testujemy tylko to, czy uda się utworzyć automat
        new VendingMachine(mockedConfig);
    }

    /*
    ---da się dodać tackę do pustego automatu
    ---nie powinno się dać tacki w miejsce gdzie już jest
    ---nie dodać tacki o istniejącym już symbolu
    ---nie powinno być możliwości dodatnia tacki w miejsce które nie istnieje
     */
    @Test
    public void shouldBeAbleToAddTrayToEmptyMachine(){
        //given
        VendingMachine vendingMachine = new VendingMachine(PropertiesFileConfiguration.getInstance());//używamy metody bo jest to singleton
        Tray tray = Tray.builder("B2").build();
        //when
        boolean successFullyAdded = vendingMachine.placeTray(tray);

        //then
        assertTrue(successFullyAdded);
        Optional<Tray>obtainedTray =
        vendingMachine.trayDetailsAtPosition(1,1);
        assertTrue(obtainedTray.isPresent());
        Tray trayFromMachine = obtainedTray.get();
        assertEquals("B2", trayFromMachine.getSymbol());


    }
    @Test
    public void shouldNotAllowToPlaceTrayTwiceOnTheSamePosition(){
        //given
        VendingMachine vendingMachine =
                new VendingMachine(PropertiesFileConfiguration.getInstance());
        Tray firstTray = Tray.builder("C1").build();
        Tray secondTray = Tray.builder("C1").build();

        //when
        vendingMachine.placeTray(firstTray);
        boolean resultOfSecondPlacement = vendingMachine.placeTray(secondTray);

        //then
        assertFalse(resultOfSecondPlacement);
    }
    @Test
    public void shouldNotBeAbleToAddTrayOnNotExistingPosition(){
        //given
        VendingMachine vendingMachine =
                new VendingMachine(PropertiesFileConfiguration.getInstance());
        Tray tray = Tray.builder("A0").build();

        //when
        boolean placementResult = vendingMachine.placeTray(tray);

        //then
        assertFalse(placementResult);
    }

}