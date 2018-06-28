package model;

import validation.ShipValidator;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private List<GameBoardObserver> observers;
    private PlayersBoard humanBoard;
    private PlayersBoard computerBoard;
    private ShipValidator validator;


    public GameBoard(PlayersBoard humanBoard, PlayersBoard computerBoard, ShipValidator validator) {
        observers = new ArrayList<>();
        this.humanBoard = humanBoard;
        this.computerBoard = computerBoard;
        this.validator = validator;
    }

    public BoardField shootHumanBoard(Point shotLocation) {
        BoardField shootedPoint = humanBoard.getSeaElement(shotLocation);
        switch (shootedPoint) {
            case WATER:
                this.humanBoard = humanBoard.updateSea(shotLocation, BoardField.MISS);
                break;

            case SHIP:
                this.humanBoard = humanBoard.updateSea(shotLocation, BoardField.SHIP_HIT);
                break;
        }
        updateObservers();
        return shootedPoint;
    }

    public BoardField getHumanBoardElement(Point point) {
        return humanBoard.getSeaElement(point);
    }

    public BoardField shootComputerBoard(Point shotLocation) {
        BoardField shootedPoint = computerBoard.getSeaElement(shotLocation);
        switch (shootedPoint) {
            case WATER:
                this.computerBoard = computerBoard.updateSea(shotLocation, BoardField.MISS);
                break;

            case SHIP:
                this.computerBoard = computerBoard.updateSea(shotLocation, BoardField.SHIP_HIT);
                break;
        }
        updateObservers();
        return shootedPoint;
    }

    public BoardField getComputerBoardElement(Point shotLocation) {
        return computerBoard.getSeaElement(shotLocation);
    }

    public boolean addHumanShip(Ship ship) {
        if(validator.isValid(ship, humanBoard)) {
            this.humanBoard = humanBoard.addShip(ship);
            updateObservers();
            return true;
        }
        return false;
    }

    public boolean addComputerShip(Ship ship) {
        if(validator.isValid(ship, computerBoard)) {
            this.computerBoard = computerBoard.addShip(ship);
            updateObservers();
            return true;
        }
        return false;
    }

    public void register(GameBoardObserver observer) {
        observers.add(observer);
    }

    public void unregister(GameBoardObserver observer) {
        observers.remove(observer);
    }

    private void updateObservers() {
        observers.forEach(observer -> observer.update(this));
    }
}
