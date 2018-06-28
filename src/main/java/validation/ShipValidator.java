package validation;

import model.PlayersBoard;
import model.Ship;

public interface ShipValidator {

    boolean isValid(Ship ship, PlayersBoard board);

}
