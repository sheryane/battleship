package user;

public class GameMasterRunnable implements Runnable {

    private GameMasterLogic logic;

    public GameMasterRunnable(GameMasterLogic logic) {
        this.logic = logic;
    }

    @Override
    public void run() {
        logic.placeHumanShips();
        logic.placeComputerShips();

        while (logic.getWinner() == null) {
            logic.playTurn();
        }
        logic.getWinner().sendMessage("You won!");
    }
}
