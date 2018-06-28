package validation;

import model.PlayersBoard;
import model.Ship;

import java.util.Arrays;
import java.util.List;


public class AndRuleValidator implements ShipValidator {

    //    private ShipValidator[] validators;
    private List<ShipValidator> validators;

    public AndRuleValidator(ShipValidator... validators) {
        this.validators = Arrays.asList(validators);
    }

    @Override
    public boolean isValid(Ship ship, PlayersBoard board) {
        for (ShipValidator validator : validators) {
            boolean shipCanBePlaced = validator.isValid(ship, board);
            if (!shipCanBePlaced) {
                return false;
            }
        }
        return true;
    }

}
