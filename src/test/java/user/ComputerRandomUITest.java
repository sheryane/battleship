package user;

import model.Point;
import model.Ship;
import model.ShipType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ComputerRandomUITest {

    private ComputerRandomUI ui;

    @Before
    public void init() {
        ui = new ComputerRandomUI();
    }

    @Test
    public void shouldReturnRandomPoint() {
        Point point1 = ui.askUserForShot();
        Point point2 = ui.askUserForShot();

        assertNotNull(point1);
        assertNotNull(point2);
        assertNotSame(point1, point2);
    }

    @Test
    public void shouldReturnRandomShip() {
        Ship ship1 = ui.askUserForShip(ShipType.CRUISER);
        Ship ship2 = ui.askUserForShip(ShipType.CRUISER);

        assertNotNull(ship1);
        assertNotNull(ship2);
        assertEquals(ShipType.CRUISER, ship1.getShipType());
        assertEquals(ShipType.CRUISER, ship2.getShipType());
        assertNotSame(ship1, ship2);
    }

    @Test
    public void shouldNotReturnSamePointTwice() {
        Set<String> generatedPoints = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            Point point = ui.askUserForShot();
            String pointAsString = point.getX() + ";" + point.getY();
            generatedPoints.add(pointAsString);
        }

        assertEquals(100, generatedPoints.size());
    }

}