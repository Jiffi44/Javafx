import javafx.application.Platform;

public class ClockRunnable implements Runnable {

    private volatile boolean running = true;
    private int seconds = 0;
    private Main main; //refererar till Main så att metod kan anropas
    public ClockRunnable(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Thread.sleep(1000);

                seconds++; //ökar räknare

                int currentSeconds = seconds;

                Platform.runLater(() -> main.updateTimeLabel(currentSeconds));
            }
        } catch (InterruptedException e) {
            System.out.println("Klocktråden avbröts");
        }
    }
    public void stop() {
        running = false;
    }
}
