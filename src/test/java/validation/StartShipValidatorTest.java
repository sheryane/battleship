package validation;

import model.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StartShipValidatorTest {

    @Test
    public void whenStartingOnSeaReturnsTrue() {
        StartShipValidator validator = new StartShipValidator();
        PlayersBoard board = PlayersBoard.fresh();
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.HORIZONTAL, new Point(1, 1));

        board.addShip(ship);

        assertTrue(validator.isValid(ship, board));
    }

    @Test
    public void whenStartingBeyondSeaReturnsFalse() {
        StartShipValidator validator = new StartShipValidator();
        PlayersBoard board = PlayersBoard.fresh();
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.HORIZONTAL, new Point(120, 5));

        board.addShip(ship);

        assertFalse(validator.isValid(ship, board));
    }

}