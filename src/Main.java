import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Main extends Application {

    //textfält
    private TextField tfFirstName;
    private TextField tfLastName;
    private TextField tfTelefon;
    private TextField tfAdress;

    //label för sparad medlem
    private Label lbResult;

    //tidtagarur
    Label lbTime;
    private int seconds = 0;

    //Tråd objekt
    private ClockRunnable clockRunnable;
    private Thread thread;

    @Override
    public void start(Stage primaryStage) {


        //medlems formulär
        tfFirstName = new TextField();
        tfLastName = new TextField();
        tfTelefon = new TextField();
        tfAdress = new TextField();

        Button btnAdd = new Button("Lägg till medlem");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        //labels för textfält
        gridPane.add(new Label("Förnamn: "), 0, 0);
        gridPane.add(tfFirstName, 1, 0);

        gridPane.add(new Label("Lastname: "), 0, 1);
        gridPane.add(tfLastName, 1, 1);

        gridPane.add(new Label("Telefon: "), 0, 2);
        gridPane.add(tfTelefon, 1, 2);

        gridPane.add(new Label("Adress: "), 0, 3);
        gridPane.add(tfAdress, 1, 3);

        gridPane.add(btnAdd, 1, 4);

        lbResult = new Label("ingen medlem är sparas än");

        btnAdd.setOnAction(e -> { //knapp för att spara medlem
            String text = "Medlem sparad: " + tfFirstName.getText() + " " + tfLastName.getText() + " " + tfAdress.getText();

            lbResult.setText(text);
        });


        //Tråd tidtagarur
        lbTime = new Label("00:00:00");
        lbTime.setStyle("-fx-font-size: 20;");

        Button btnStart = new Button("Start");
        Button btnStop = new Button("Stop");

        //Start knapp startar tråden
        btnStart.setOnAction(e -> startClock());
        //stop knapp som stoppar tråden
        btnStop.setOnAction(e -> stopClock());

        HBox timeButtons = new HBox(10, btnStart, btnStop);
        timeButtons.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10, lbTime, timeButtons);
        vBox.setAlignment(Pos.CENTER);


        VBox root = new VBox(40, gridPane, lbResult, vBox);
        root.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("tidtagarur");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void startClock() { //startar klockan i egen tråd

        if (thread != null && thread.isAlive()) {
            return;
        }

        clockRunnable = new ClockRunnable(this);
        thread = new Thread(clockRunnable);

        thread.setDaemon(true);
        thread.start();
    }

    private void stopClock() {
        if (clockRunnable != null) {
            clockRunnable.stop(); //tråden avslutas
        }
    }

    public void updateTimeLabel(int totalseconds) { //metod för att räkna sekunder och sätta texten

        int hours = totalseconds / 3600;
        int minutes = totalseconds % 3600 / 60;
        int second = totalseconds % 60;

        lbTime.setText(String.format("%02d:%02d:%02d", hours, minutes, second));
    }

    public static void main(String[] args) {
        launch(args);
    }
}