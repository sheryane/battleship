package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import validation.ShipValidator;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameBoardTest {

    private GameBoard gameBoard;
    @Mock
    private ShipValidator validator;

    @Before
    public void init() {
        PlayersBoard humanBoard = PlayersBoard.fresh();
        PlayersBoard computerBoard = PlayersBoard.fresh();
        gameBoard = new GameBoard(humanBoard, computerBoard, validator);
    }

    @Test
    public void shouldSetMissValueWhenShotIntoWater() {
        Point shotLocation = new Point(3, 4);

        gameBoard.shootHumanBoard(shotLocation);

        BoardField shotField = gameBoard.getHumanBoardElement(shotLocation);
        assertEquals(BoardField.MISS, shotField);
    }

    @Test
    public void shouldSetMissValueWhenShotIntoWaterOnComputerBoard() {
        Point shotLocation = new Point(3, 5);

        gameBoard.shootComputerBoard(shotLocation);

        BoardField shotField = gameBoard.getComputerBoardElement(shotLocation);
        assertEquals(BoardField.MISS, shotField);
    }

    @Test
    public void shouldSetHitValueWhenShotIntoShip() {
        when(validator.isValid(any(), any())).thenReturn(Boolean.TRUE);
        Point shotLocation = new Point(4, 5);
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.HORIZONTAL, shotLocation);
        gameBoard.addHumanShip(ship);

        gameBoard.shootHumanBoard(shotLocation);

        BoardField shotField = gameBoard.getHumanBoardElement(shotLocation);
        assertEquals(BoardField.SHIP_HIT, shotField);
    }

    @Test
    public void shouldSetHitValueWhenShotIntoShipOnComputerBoard() {
        when(validator.isValid(any(), any())).thenReturn(Boolean.TRUE);
        Point shotLocation = new Point(3, 2);
        Ship ship = new Ship(ShipType.CRUISER, Orientation.VERTICAL, shotLocation);
        gameBoard.addComputerShip(ship);

        gameBoard.shootComputerBoard(shotLocation);

        BoardField shotField = gameBoard.getComputerBoardElement(shotLocation);
        assertEquals(BoardField.SHIP_HIT, shotField);
    }

    @Test
    public void shouldReturnPreviousValueAfterShoot() {
        Point shotLocation = new Point(3, 4);

        BoardField shotField = gameBoard.shootHumanBoard(shotLocation);

        assertEquals(BoardField.WATER, shotField);

    }

    @Test
    public void shouldReturnPreviousValueAfterShootShip() {
        when(validator.isValid(any(), any())).thenReturn(Boolean.TRUE);
        Point shotLocation = new Point(4, 5);
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.HORIZONTAL, shotLocation);
        gameBoard.addHumanShip(ship);

        BoardField shotField = gameBoard.shootHumanBoard(shotLocation);

        assertEquals(BoardField.SHIP, shotField);
    }

    @Test
    public void shouldReturnPreviousValueAfterShootOnComputerBoard() {
        Point shotLocation = new Point(0, 0);

        BoardField shotField = gameBoard.shootComputerBoard(shotLocation);

        assertEquals(BoardField.WATER, shotField);
    }

    @Test
    public void shouldReturnPreviousValueAfterShootShipOnComputerBoard() {
        when(validator.isValid(any(), any())).thenReturn(Boolean.TRUE);
        Point shotLocation = new Point(3, 2);
        Ship ship = new Ship(ShipType.CRUISER, Orientation.VERTICAL, shotLocation);
        gameBoard.addComputerShip(ship);

        BoardField shotField = gameBoard.shootComputerBoard(shotLocation);

        assertEquals(BoardField.SHIP, shotField);
    }

    @Test
    public void whenComputerShipIsAddThenUpdateObserver() {
        when(validator.isValid(any(), any())).thenReturn(Boolean.TRUE);
        GameBoardObserver observer = mock(GameBoardObserver.class);
        Ship ship = new Ship(ShipType.DESTROYER, Orientation.HORIZONTAL, new Point(2, 3));
        gameBoard.register(observer);

        gameBoard.addComputerShip(ship);

        //zweryfikuj, że na obiekcie observer, została wykonana dokładnie 1 raz metoda update
        //verify(observer, times(1)).update(eq(gameBoard));
        verify(observer).update(eq(gameBoard));
    }

    @Test
    public void whenHumanShipIsAddThenUpdateObserver() {
        when(validator.isValid(any(), any())).thenReturn(Boolean.TRUE);
        GameBoardObserver observer = mock(GameBoardObserver.class);
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.VERTICAL, new Point(2, 3));
        gameBoard.register(observer);

        gameBoard.addHumanShip(ship);

        verify(observer).update(eq(gameBoard));
    }

    @Test
    public void whenComputerBoardIsShotThenUpdateObserver() {
        GameBoardObserver observer = mock(GameBoardObserver.class);
        gameBoard.register(observer);

        gameBoard.shootComputerBoard(new Point(2, 3));

        verify(observer).update(eq(gameBoard));
    }

    @Test
    public void whenHumanBoardIsShotThenUpdateObserver() {
        GameBoardObserver observer = mock(GameBoardObserver.class);
        gameBoard.register(observer);

        gameBoard.shootHumanBoard(new Point(2, 3));

        verify(observer).update(eq(gameBoard));
    }

    @Test
    public void whenObserverRegisteredAndUnregisteredAndShipIsAddNoUpdateIsMade() {
        GameBoardObserver observer = mock(GameBoardObserver.class);
        Ship ship = new Ship(ShipType.DESTROYER, Orientation.HORIZONTAL, new Point(2, 3));
        gameBoard.register(observer);
        gameBoard.unregister(observer);

        gameBoard.addHumanShip(ship);
        gameBoard.addComputerShip(ship);

        //verify(observer, never()).update(gameBoard);
        verifyZeroInteractions(observer);
    }

    @Test
    public void whenObserverRegisteredAndUnregisteredAndPlayersBoardIsShootNoUpdateIsMade() {
        GameBoardObserver observer = mock(GameBoardObserver.class);
        gameBoard.register(observer);
        gameBoard.unregister(observer);

        gameBoard.shootHumanBoard(new Point(2, 3));
        gameBoard.shootComputerBoard(new Point(2, 3));

        //verify(observer, never()).update(gameBoard);
        verifyZeroInteractions(observer);
    }

    @Test
    public void whenValidatorRejectsShipThenPlayersBoardDontChange() {
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.VERTICAL, new Point(1, 1));
        when(validator.isValid(eq(ship), (any()))).thenReturn(Boolean.FALSE);

        gameBoard.addHumanShip(ship);

        BoardField potentialShipStart = gameBoard.getHumanBoardElement(new Point(1, 1));
        assertFalse(potentialShipStart.equals(BoardField.SHIP));
        assertNotEquals(potentialShipStart, BoardField.SHIP);
    }

    @Test
    public void whenValidatorAcceptsShipThenPlayersBoardChange() {
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.VERTICAL, new Point(1, 1));
        when(validator.isValid(eq(ship), any())).thenReturn(Boolean.TRUE);

        gameBoard.addHumanShip(ship);

        BoardField potentialShipStart = gameBoard.getHumanBoardElement(new Point(1, 1));
        assertTrue(potentialShipStart.equals(BoardField.SHIP));
        assertEquals(potentialShipStart, BoardField.SHIP);
    }

    @Test
    public void whenValidatorRejectsShipThenReturnsFalse() {
        when(validator.isValid(any(), any())).thenReturn(Boolean.FALSE);
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.VERTICAL, new Point(1, 1));

        Boolean shipRejected = gameBoard.addHumanShip(ship);

        assertFalse(shipRejected);
    }

    @Test
    public void whenValidatorAcceptsShipThenReturnTrue() {
        when(validator.isValid(any(), any())).thenReturn(Boolean.TRUE);
        Ship ship = new Ship(ShipType.BATTLESHIP, Orientation.VERTICAL, new Point(1, 1));

        Boolean shipAdded = gameBoard.addHumanShip(ship);

        assertTrue(shipAdded);
    }


}