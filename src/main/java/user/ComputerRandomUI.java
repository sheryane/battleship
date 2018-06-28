package user;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerRandomUI implements UserInterface, GameBoardObserver {

    private Random generator;
    private List<Point> availableShots;


    public ComputerRandomUI() {
        generator = new Random();
        availableShots = new ArrayList<>();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                availableShots.add(new Point(x, y));
            }
        }
    }

    @Override
    public void update(GameBoard gb) {

    }

    @Override
    public void printMaps() {
        //OK, moze byc puste
    }

    @Override
    public void notifyUser(String msg) {
        //OK, moze byc puste
    }

    @Override
    public Point askUserForShot() {
        int shotPosition = generator.nextInt(availableShots.size());
        Point shot = availableShots.get(shotPosition);
        availableShots.remove(shot);

        return shot;
    }

    @Override
    public Ship askUserForShip(ShipType type) {
        return new Ship(type, getOrientation(), getPoint());
    }

    private Point getPoint() {
        return new Point(generator.nextInt(10), generator.nextInt(10));
    }

    private Orientation getOrientation() {
        return generator.nextBoolean()
                ? Orientation.HORIZONTAL
                : Orientation.VERTICAL;
    }
}
