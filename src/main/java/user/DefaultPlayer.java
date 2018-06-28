package user;

import model.Point;
import model.Ship;
import model.ShipType;

public class DefaultPlayer implements Player {

    private UserInterface ui;

    public DefaultPlayer(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public Ship getNextShip(ShipType type) {
        return ui.askUserForShip(type);
    }

    @Override
    public Point getNextShot() {
        return ui.askUserForShot();
    }

    @Override
    public void sendMessage(String message) {
        ui.notifyUser(message);
    }
}
