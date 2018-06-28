package model;

import java.util.ArrayList;
import java.util.List;

public class PlayersBoard {

    private final BoardField[][] sea;
    private final List<Ship> shipList;

    private PlayersBoard(Builder builder) {
        this.sea = builder.seaToBeAdjusted;
        this.shipList = builder.shipListToBeAdjusted;
    }

    public static PlayersBoard fresh() {

        BoardField[][] freshSea = new BoardField[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                freshSea[x][y] = BoardField.WATER;
            }
        }
        List<Ship> freshShips = new ArrayList<>();

        return new Builder(freshSea, freshShips).build();
    }

    public List<Ship> getShips() {
        return shipList;
    }

    public BoardField getSeaElement(Point point) {
        if (point.getX() < 0 || point.getX() > sea.length - 1 || point.getY() < 0 || point.getY() > sea.length - 1) {
            return BoardField.NONE;
        }
        return sea[point.getX()][point.getY()];
    }

    public PlayersBoard updateSea(Point point, BoardField newSeaValue) {
        return new Builder(sea, shipList).seaElement(point, newSeaValue).build();
    }

    public PlayersBoard addShip(Ship ship) {
        return new Builder(sea, shipList).addShip(ship).build();
    }

    private static class Builder {
        private BoardField[][] seaToBeAdjusted;
        private List<Ship> shipListToBeAdjusted;

        Builder(BoardField[][] seaToBeAdjusted, List<Ship> shipListToBeAdjusted){
            this.seaToBeAdjusted = new BoardField[10][10];
            for(int x = 0; x < 10; x++) {
                for(int y = 0; y < 10; y++) {
                    this.seaToBeAdjusted[x][y] = seaToBeAdjusted[x][y];
                }
            }
            this.shipListToBeAdjusted = new ArrayList<>(shipListToBeAdjusted);
        }

        private Builder seaElement(Point p, BoardField newSeaValue) {
            if (p == null || p.getX() < 0 || p.getX() > seaToBeAdjusted.length - 1 || p.getY() < 0 || p.getY() > seaToBeAdjusted.length - 1) {
//                System.out.println("You can't change the value beyond the Sea (10x10).");
                return this;
            }
            seaToBeAdjusted[p.getX()][p.getY()] = newSeaValue;
            return this;
        }

        private Builder addShip(Ship ship) {

            shipListToBeAdjusted.add(ship);
            seaElement(ship.getPosition(), BoardField.SHIP);

            for(int i = 0; i < ship.getShipType().size(); i++){
                Integer x = ship.getPosition().getX();
                Integer y = ship.getPosition().getY();

                if(ship.getOrientation() == Orientation.HORIZONTAL) {
                    seaElement(new Point(x + i, y), BoardField.SHIP);
                } else {
                    seaElement(new Point(x, y + i), BoardField.SHIP);
                }
            }
            return this;
        }

        private PlayersBoard build() {
            return new PlayersBoard(this);
        }
    }
}
