package user;

import model.*;

import java.util.List;

public class GameMasterLogic {

    private final Player humanPlayer;
    private final Player computerPlayer;
    private List<ShipType> availableShipTypes;
    private GameBoard gameBoard;
    private Player currentPlayer;
    private int shipHumanBoardFields;
    private int shipComputerBoardFields;

    public GameMasterLogic(
            Player humanPlayer,
            Player computerPlayer,
            List<ShipType> availableShipTypes,
            GameBoard gameBoard) {
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        this.availableShipTypes = availableShipTypes;
        this.gameBoard = gameBoard;
        currentPlayer = humanPlayer;
        shipHumanBoardFields = shipFields();
        shipComputerBoardFields = shipFields();
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void placeHumanShips() {
        for (ShipType shipType : availableShipTypes) {
            boolean wasPlaced;
            do {
                Ship playerShip = humanPlayer.getNextShip(shipType);
                wasPlaced = gameBoard.addHumanShip(playerShip);
            }
            while (!wasPlaced);
        }
    }

    public void placeComputerShips() {
        for (ShipType shipType : availableShipTypes) {
            boolean wasPlaced;
            do {
                Ship computerShip = computerPlayer.getNextShip(shipType);
                wasPlaced = gameBoard.addComputerShip(computerShip);
            }
            while (!wasPlaced);
        }
    }

    public void playTurn() {
        Point shotLocation = currentPlayer.getNextShot();
        BoardField shotResult;

        if (isHumansTurn()) {
            shotResult = gameBoard.shootComputerBoard(shotLocation);
        } else {
            shotResult = gameBoard.shootHumanBoard(shotLocation);
        }

        if (shipWasNotHit(shotResult)) {
            changeCurrentPlayer();
        } else {
            if (isHumansTurn()) {
                shipComputerBoardFields--;
            } else {
                shipHumanBoardFields--;
            }
        }

    }

    private boolean shipWasNotHit(BoardField shotResult) {
        return shotResult != BoardField.SHIP;
    }

    private void changeCurrentPlayer() {
        if (isHumansTurn()) {
            currentPlayer = computerPlayer;
        } else {
            currentPlayer = humanPlayer;
        }
    }

    private boolean isHumansTurn() {
        return currentPlayer == humanPlayer;
    }

    protected Player getWinner() {
        if (shipComputerBoardFields == 0) {
            return humanPlayer;
        } else if (shipHumanBoardFields == 0) {
            return computerPlayer;
        }
        return null;
    }

    private int shipFields() {
        int result = 0;
        for (ShipType ShipType : availableShipTypes) {
            switch (ShipType) {
                case BATTLESHIP:
                    result += ShipType.BATTLESHIP.size();
                    break;
                case CRUISER:
                    result += ShipType.CRUISER.size();
                    break;
                case DESTROYER:
                    result += ShipType.DESTROYER.size();
                    break;
                case SUBMARINE:
                    result += ShipType.SUBMARINE.size();
                    break;
            }
        }
        return result;
    }
}

