package user;

import model.Point;
import model.Ship;
import model.ShipType;

public interface Player {
    Ship getNextShip(ShipType type);
    Point getNextShot();
    void sendMessage(String message);

}
