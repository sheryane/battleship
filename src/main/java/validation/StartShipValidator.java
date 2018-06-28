package validation;

import model.PlayersBoard;
import model.Point;
import model.Ship;

public class StartShipValidator implements ShipValidator {

    @Override
    public boolean isValid(Ship ship, PlayersBoard board) {
        Integer x = ship.getPosition().getX();
        Integer y = ship.getPosition().getY();

        if (x > 10 || x < 0 || y > 10 || y < 0 || ship.getPosition() == null) {
            return false;
        }
        return true;
    }
}
