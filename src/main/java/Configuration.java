import model.GameBoard;
import model.PlayersBoard;
import model.ShipType;
import user.*;
import validation.*;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private final GameMasterRunnable runnable;
    private final GameMasterLogic logic;
    private final DefaultPlayer humanPlayer;
    private final DefaultPlayer computerPlayer;

    public Configuration() {
        ConsoleUI humanUI = new ConsoleUI(new JavaConsoleDelegate());
        humanPlayer = new DefaultPlayer(humanUI);

        ComputerRandomUI computerUI = new ComputerRandomUI();
        computerPlayer = new DefaultPlayer(computerUI);

        GameBoard gameBoard = new GameBoard(
                PlayersBoard.fresh(),
                PlayersBoard.fresh(),
                getValidator());

        gameBoard.register(humanUI);
        gameBoard.register(computerUI);

        logic = new GameMasterLogic(
                humanPlayer,
                computerPlayer,
                getShipTypes(),
                gameBoard);
        runnable = new GameMasterRunnable(logic);
    }

    public Runnable getGameRunnable() {
        return runnable;
    }

    private ShipValidator getValidator() {
        StartShipValidator validator1 = new StartShipValidator();
        EndShipValidator validator2 = new EndShipValidator();
        NeighbourShipValidator validator3 = new NeighbourShipValidator();
        return new AndRuleValidator(validator1, validator2, validator3);
    }

    private List<ShipType> getShipTypes() {
        List<ShipType> shipTypes = new ArrayList<>();
        shipTypes.add(ShipType.BATTLESHIP);
        shipTypes.add(ShipType.CRUISER);
        shipTypes.add(ShipType.CRUISER);
        shipTypes.add(ShipType.DESTROYER);
        shipTypes.add(ShipType.DESTROYER);
        shipTypes.add(ShipType.DESTROYER);
        shipTypes.add(ShipType.SUBMARINE);
        shipTypes.add(ShipType.SUBMARINE);
        shipTypes.add(ShipType.SUBMARINE);
        shipTypes.add(ShipType.SUBMARINE);
        return shipTypes;
    }
}
