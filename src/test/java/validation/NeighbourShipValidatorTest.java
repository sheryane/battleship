package validation;

import model.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class NeighbourShipValidatorTest {

    @Test
    public void shouldAllNeighbourFieldsBeWaterReturnsTrue() {
        NeighbourShipValidator validator = new NeighbourShipValidator();
        PlayersBoard board = PlayersBoard.fresh();
        Ship ship1 = new Ship(ShipType.CRUISER, Orientation.HORIZONTAL, new Point(0, 0));
        Ship ship2 = new Ship(ShipType.CRUISER, Orientation.HORIZONTAL, new Point(5, 3));

        board.addShip(ship1);
        PlayersBoard updatedBoard = board.addShip(ship2);

        assertTrue(validator.isValid(ship1, updatedBoard));
    }

    @Test
    public void shouldAllNeighbourFieldsBeWaterReturnsFalse() {
        NeighbourShipValidator validator = new NeighbourShipValidator();
        PlayersBoard board = PlayersBoard.fresh();
        Ship ship1 = new Ship(ShipType.BATTLESHIP, Orientation.HORIZONTAL, new Point(1, 1));
        Ship ship2 = new Ship(ShipType.CRUISER, Orientation.VERTICAL, new Point(1, 2));

        board.addShip(ship1);
        PlayersBoard updatedBoard  = board.addShip(ship2);

        assertFalse(validator.isValid(ship2, updatedBoard));
    }

}