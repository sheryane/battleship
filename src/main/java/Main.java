public class Main {
    public static void main(String[] args) {
        Runnable runnable = new Configuration().getGameRunnable();
        new Thread(runnable).start();

    }
}
