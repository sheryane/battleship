package validation;

import model.BoardField;
import model.PlayersBoard;
import model.Point;
import model.Ship;

public class NeighbourShipValidator implements ShipValidator {

    @Override
    public boolean isValid(Ship ship, PlayersBoard board) {
        Point shipStartLocation = ship.getPosition();
        Point shipEndLocation = ship.getShipEnd();
        int startX = shipStartLocation.getX() - 1;
        int stopX = shipEndLocation.getX() + 1;
        int startY = shipStartLocation.getY() - 1;
        int stopY = shipEndLocation.getY() + 1;
        for (int y = startY; y <= stopY; y++) {
            for (int x = startX; x <= stopX; x++) {
                BoardField currentElement = board.getSeaElement(new Point(x, y));
                if (currentElement == BoardField.SHIP) {
                    return false;
                }
            }
        }
        return true;
    }
}
