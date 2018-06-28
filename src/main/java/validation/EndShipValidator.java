package validation;

import model.*;

public class EndShipValidator implements ShipValidator {

    @Override
    public boolean isValid(Ship ship, PlayersBoard board) {
        Long shipSize = ship.getShipType().size();
        Integer x = ship.getPosition().getX();
        Integer y = ship.getPosition().getY();
        Point endingPoint = ship.getShipEnd();

        if ( endingPoint.getX() > 10 || endingPoint.getX() < 0 || endingPoint.getY() > 10 || endingPoint.getY() < 0 || ship.getPosition() == null) {
            return false;
        }
        return true;
    }
}
