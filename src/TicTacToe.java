import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class TicTacToe extends Application implements TicTacToeFinals {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Stage[] stages = new Stage[NUMBER_OF_OBJECTS];
		Scene[] scenes = new Scene[NUMBER_OF_OBJECTS];
		TicTacToePane[] panes = new TicTacToePane[NUMBER_OF_OBJECTS];
		try {
			for (int i = 0; i < NUMBER_OF_OBJECTS; i++) {
				panes[i] = TicTacToePane.getInstance();
				scenes[i] = new Scene(panes[i], 475, 170);
				stages[i] = new Stage();
				stages[i].setTitle("TicTacToe #" + (i + 1));
				stages[i].setScene(scenes[i]);
				stages[i].setResizable(true);
				stages[i].show(); // show the window on screen
				stages[i].setAlwaysOnTop(true);
			}
		} catch (Exception e) {
			System.out.println(SINGLETON_MASSAGE);
		}
	}

}
