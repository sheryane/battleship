package model;

        import org.junit.Test;

        import java.util.List;

        import static org.junit.Assert.*;

public class PlayersBoardTest {

    @Test
    public void shouldContainNoShipsAfterCreation() {
        //given
        PlayersBoard board = PlayersBoard.fresh();
        //when
        List<Ship> shipsOnBoard = board.getShips();
        //then
        assertNotNull(shipsOnBoard);
        assertEquals(0, shipsOnBoard.size());
    }

    @Test
    public void shouldContainOnlyWaterAfterCreation() {
        //given
        PlayersBoard board = PlayersBoard.fresh();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Point point = new Point(x, y);
                //when
                BoardField field = board.getSeaElement(point);
                //then
                assertEquals(BoardField.WATER, field);
            }
        }
    }

    @Test
    public void shouldBeAbleToChangeSeaElementValue() {
        //given
        PlayersBoard board = PlayersBoard.fresh();
        Point point = new Point(3, 7);
        BoardField miss = BoardField.MISS;
        //when
        PlayersBoard updatedBoard = board.updateSea(point, miss);
        //then
        assertEquals(miss, updatedBoard.getSeaElement(point));
    }

    @Test
    public void shouldBeAbleToAddNewShip() {
        //given
        PlayersBoard board = PlayersBoard.fresh();
        Ship destroyer = new Ship(ShipType.DESTROYER, Orientation.HORIZONTAL, new Point(3, 7));
        //when
        PlayersBoard updatedBoard = board.addShip(destroyer);
        //then
        assertEquals(1, updatedBoard.getShips().size());
    }

    @Test
    public void shouldReturnNewInstanceAfterSeaChange() {
        //given
        PlayersBoard board = PlayersBoard.fresh();
        Point point = new Point(1, 2);
        BoardField newSeaValue = BoardField.SHIP;
        //when
        PlayersBoard updatedBoard = board.updateSea(point, newSeaValue);
        //then
        assertNotSame(board, updatedBoard);
    }

    @Test
    public void shouldReturnNewInstanceAfterAddingShip() {
        //given
        PlayersBoard board = PlayersBoard.fresh();
        Ship destroyer = new Ship(ShipType.DESTROYER, Orientation.HORIZONTAL, new Point(3, 7));
        //when
        PlayersBoard updatedBoard = board.addShip(destroyer);
        //then
        assertNotSame(board, updatedBoard);
    }

    @Test
    public void shouldNotModifyExistingInstanceAfterSeaUpdate() {
        //given
        PlayersBoard board = PlayersBoard.fresh();
        Point point = new Point(1, 2);
        BoardField newSeaValue = BoardField.SHIP;
        //when
        board.updateSea(point, newSeaValue);
        //then
        assertEquals(BoardField.WATER, board.getSeaElement(point));
    }

    @Test
    public void shouldNotModifyExistingInstanceAfterShipAdd() {
        PlayersBoard board = PlayersBoard.fresh();
        Ship shipToBeAdded = new Ship(ShipType.CRUISER, Orientation.HORIZONTAL, new Point(3,3));

        board.addShip(shipToBeAdded);

        assertEquals(0,board.getShips().size());
    }

    @Test
    public void shouldReturnNoneWhenRightBoundaryIsExceeded() {
        PlayersBoard board = PlayersBoard.fresh();

        BoardField seaElement = board.getSeaElement(new Point(10, 5));

        assertEquals(BoardField.NONE, seaElement);
    }

    @Test
    public void shouldReturnNoneWhenLeftBoundaryIsExceeded() {
        PlayersBoard board = PlayersBoard.fresh();

        BoardField seaElement = board.getSeaElement(new Point(-1, 5));

        assertEquals(BoardField.NONE, seaElement);
    }

    @Test
    public void shouldReturnNoneWhenLowerBoundaryIsExceeded() {
        PlayersBoard board = PlayersBoard.fresh();

        BoardField seaElement = board.getSeaElement(new Point(5, -1));

        assertEquals(BoardField.NONE, seaElement);
    }

    @Test
    public void shouldReturnNoneWhenUpperBoundaryIsExceeded() {
        PlayersBoard board = PlayersBoard.fresh();

        BoardField seaElement = board.getSeaElement(new Point(5, 10));

        assertEquals(BoardField.NONE, seaElement);
    }

    @Test
    public void shouldMarkShipOnSea() {
        PlayersBoard board = PlayersBoard.fresh();
        Ship ship = new Ship(ShipType.DESTROYER, Orientation.HORIZONTAL, new Point(3, 6));

        PlayersBoard updatedBoard = board.addShip(ship);

        assertEquals(BoardField.SHIP, updatedBoard.getSeaElement(new Point(3, 6)));
        assertEquals(BoardField.SHIP, updatedBoard.getSeaElement(new Point(4, 6)));
    }

    @Test
    public void shouldMarkVerticalShipOnSea() {
        PlayersBoard board = PlayersBoard.fresh();
        Ship ship = new Ship(ShipType.CRUISER, Orientation.VERTICAL, new Point(2, 4));

        PlayersBoard updatedBoard = board.addShip(ship);

        assertEquals(BoardField.SHIP, updatedBoard.getSeaElement(new Point(2, 4)));
        assertEquals(BoardField.SHIP, updatedBoard.getSeaElement(new Point(2, 5)));
        assertEquals(BoardField.SHIP, updatedBoard.getSeaElement(new Point(2, 6)));
    }

    @Test
    public void shouldNotBePossibleToChangeElementBeyondSea() {
        PlayersBoard board = PlayersBoard.fresh();
        Point pointOutOfBound = new Point(15, 3);

        PlayersBoard updatedBoard = board.updateSea(pointOutOfBound, BoardField.MISS);

        assertEquals(BoardField.NONE, updatedBoard.getSeaElement(pointOutOfBound));
    }

    @Test
    public void shouldNotBePossibleToChangeElementBeyondSeaVertical() {
        PlayersBoard board = PlayersBoard.fresh();
        Point pointOutOfBound = new Point(5, 13);

        PlayersBoard updatedBoard = board.updateSea(pointOutOfBound, BoardField.MISS);

        assertEquals(BoardField.NONE, updatedBoard.getSeaElement(pointOutOfBound));
    }
}