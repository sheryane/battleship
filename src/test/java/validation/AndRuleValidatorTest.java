package validation;

import model.PlayersBoard;
import model.Ship;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AndRuleValidatorTest {

    @Test
    public void whenAllNestValidatorsAcceptThenAccept() {
        ShipValidator nestedValidator1 = mock(ShipValidator.class);
        when(nestedValidator1.isValid(any(), any())).thenReturn(true);
        ShipValidator nestedValidator2 = mock(ShipValidator.class);
        when(nestedValidator2.isValid(any(), any())).thenReturn(true);
        ShipValidator nestedValidator3 = mock(ShipValidator.class);
        when(nestedValidator3.isValid(any(), any())).thenReturn(true);

        AndRuleValidator testedValidator = new AndRuleValidator(nestedValidator1, nestedValidator2, nestedValidator3);
        boolean shipCanBePlaced = testedValidator.isValid(mock(Ship.class), mock(PlayersBoard.class));

        assertTrue(shipCanBePlaced);
    }

    @Test
    public void whenAtLeastOneNestedValidatorRejectThenReject() {
        ShipValidator shipValidator1 = mock(ShipValidator.class);
        when(shipValidator1.isValid(any(), any())).thenReturn(true);
        ShipValidator shipValidator2 = mock(ShipValidator.class);
        when(shipValidator2.isValid(any(), any())).thenReturn(false);

        AndRuleValidator testedValidator = new AndRuleValidator(shipValidator1, shipValidator2);

        boolean shipCanBePlaced = testedValidator.isValid(mock(Ship.class), PlayersBoard.fresh());

        assertFalse(shipCanBePlaced);
    }

    @Test
    public void shouldAcceptAnyShipWhenNoNestedRules() {
        AndRuleValidator testedValidator = new AndRuleValidator();

        boolean shipCanBePlaced = testedValidator.isValid(mock(Ship.class), PlayersBoard.fresh());

        assertTrue(shipCanBePlaced);
    }
}