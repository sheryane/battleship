import model.*;
import user.ConsoleUI;
import user.JavaConsoleDelegate;
import validation.AndRuleValidator;

public class TestMain {
    public static void main(String[] args) {

        JavaConsoleDelegate consoleDelegate = new JavaConsoleDelegate();
        ConsoleUI ui = new ConsoleUI(consoleDelegate);


        GameBoard gameBoard = new GameBoard(PlayersBoard.fresh(), PlayersBoard.fresh(), new AndRuleValidator());

        Ship humanShip1 = new Ship(ShipType.BATTLESHIP, Orientation.HORIZONTAL, new Point(1, 1));
        Ship humanShip2 = new Ship(ShipType.DESTROYER, Orientation.VERTICAL, new Point(5, 5));

        gameBoard.addHumanShip(humanShip1);
        gameBoard.addHumanShip(humanShip2);

        gameBoard.shootHumanBoard(new Point(1, 1));
        gameBoard.shootHumanBoard(new Point(7, 8));

        Ship computerShip1 = new Ship(ShipType.BATTLESHIP, Orientation.HORIZONTAL, new Point(1, 1));
        Ship computerShip2 = new Ship(ShipType.DESTROYER, Orientation.VERTICAL, new Point(5, 5));

        gameBoard.addComputerShip(computerShip1);
        gameBoard.addComputerShip(computerShip2);


        gameBoard.shootComputerBoard(new Point(1,1));
        gameBoard.shootComputerBoard(new Point(7,8));

        ui.update(gameBoard);
        ui.printMaps();
    }
}
