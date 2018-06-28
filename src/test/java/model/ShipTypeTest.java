package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShipTypeTest {

    @Test
    public void shouldReturnSize4ForBattleship() {
        //given
        ShipType ship = ShipType.BATTLESHIP;
        //when
        Long shipSize = ship.size();
        //then
        assertEquals((Long) 4L, shipSize);
    }
    @Test
    public void shouldReturnSize3ForCruider() {
        ShipType ship = ShipType.CRUISER;
        Long shipSize = ship.size();
        assertEquals((Long) 3L, shipSize);
    }

    @Test
    public void shouldReturnSize2ForDestroyer() {
        ShipType ship = ShipType.DESTROYER;
        Long shipSize = ship.size();
        assertEquals((Long) 2L, shipSize);
    }
    @Test
    public void shouldReturnSize1ForSubmarine() {
        ShipType ship = ShipType.SUBMARINE;
        Long shipSize = ship.size();
        assertEquals((Long) 1L, shipSize);
    }
}