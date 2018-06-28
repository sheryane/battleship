package user;

import model.*;

import java.util.function.Function;

public class ConsoleUI implements UserInterface, GameBoardObserver {

    private JavaConsoleDelegate console;
    private GameBoard board;

    public ConsoleUI(JavaConsoleDelegate console) {
        this.console = console;
    }

    @Override
    public void update(GameBoard gb) {
        this.board = gb;
        printMaps();
    }

    @Override
    public void printMaps() {
        console.printToConsole("            YOU                     OPPONENT");
        console.printToConsole("");
        console.printToConsole("  | A B C D E F G H I J |   | A B C D E F G H I J |");
        for (int y = 0; y < 10; y++) {
            String humanLine = createHumanPlayerLine(y);
            String otherLine = createComputerPlayerLine(y);
            console.printToConsole(humanLine + " " + otherLine);
        }
        console.printToConsole("  +---------------------+   +---------------------+");
    }

    private String createHumanPlayerLine(int y) {
        return createLineForSource(y, board::getHumanBoardElement);
    }

    private String createComputerPlayerLine(int y) {
        return createLineForSource(y, board::getComputerBoardElement).replace("O", " ");
    }

    private String createLineForSource(int y, Function<Point, BoardField> loadBoardFieldFunction) {
        StringBuilder builder = new StringBuilder();
        int lineNumber = y + 1;
        if (lineNumber < 10) {
            builder.append(" ");
        }
        builder.append(lineNumber).append("|");
        for (int x = 0; x < 10; x++) {
            Point address = new Point(x, y);
            BoardField mapElement = loadBoardFieldFunction.apply(address);
            builder.append(" ").append(decodeElement(mapElement));
        }
        return builder.append(" |").toString();
    }

    private String decodeElement(BoardField mapElement) {
        switch (mapElement) {
            case SHIP:
                return "O";
            case SHIP_HIT:
                return "X";
            case MISS:
                return "*";
            default:
                return " ";
        }
    }

    @Override
    public void notifyUser(String msg) {
        console.printToConsole(msg);
    }

    @Override
    public Point askUserForShot() {
        console.printToConsole("Provide shot location (e.g.: F3)");
        return readPointFromUser();
    }

    private Point readPointFromUser() {
        String userInput = console.readFromConsole();
        userInput = userInput.toUpperCase();

        char firstCharacter = userInput.charAt(0);
        String providedNumber = userInput.substring(1);

        try {
            Integer x = firstCharacter - 'A';
            Integer y = Integer.parseInt(providedNumber) - 1;
            return new Point(x, y);
        } catch (NumberFormatException e) {
            return new Point(-1, -1);
        }
    }

    @Override
    public Ship askUserForShip(ShipType type) {
        console.printToConsole("Provide prow location of " + type + " ship: ");
        Point prow = readPointFromUser();

        console.printToConsole("Is ship horizontal? (Y/N)");
        Orientation shipOrientation = readOrientationFromUser();

        return new Ship(type, shipOrientation, prow);
    }

    private Orientation readOrientationFromUser() {
        String userInput = console.readFromConsole().toUpperCase();
        if (userInput.equals("Y") || userInput.equals("YES")) {
            return Orientation.HORIZONTAL;
        } else {
            return Orientation.VERTICAL;
        }
    }
}
