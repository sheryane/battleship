package model;

public class Ship {
    private final ShipType shipType;
    private final Orientation orientation;
    private final Point position;


    public Ship(ShipType shipType, Orientation orientation, Point position) {
        this.shipType = shipType;
        this.orientation = orientation;
        this.position = position;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Point getPosition() {
        return position;
    }

    public Point getShipEnd() {
        Long shipLength = getShipType().size() - 1;
        Integer x = getPosition().getX();
        Integer y = getPosition().getY();
        Point endingPoint;

        if (getOrientation() == Orientation.HORIZONTAL) {
            endingPoint = new Point((int) (x + shipLength), y);
        } else {
            endingPoint = new Point(x, (int) (y + shipLength));
        }

        return endingPoint;
    }
}
