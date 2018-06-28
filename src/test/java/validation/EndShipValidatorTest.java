package validation;

import model.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class EndShipValidatorTest {

    @Test
    public void whenEndingOnSeaReturnsTrue() {
        EndShipValidator validator = new EndShipValidator();
        PlayersBoard board = PlayersBoard.fresh();
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.HORIZONTAL, new Point(1, 1));

        board.addShip(ship);

        assertTrue(validator.isValid(ship, board));
    }

    @Test
    public void whenEndingBeyondSeaReturnsFalse() {
        EndShipValidator validator = new EndShipValidator();
        PlayersBoard board = PlayersBoard.fresh();
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.HORIZONTAL, new Point(9, 4));

        board.addShip(ship);

        assertFalse(validator.isValid(ship, board));
    }


}