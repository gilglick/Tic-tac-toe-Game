import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class TicTacToePane extends BorderPane implements TicTacToeFinals {

	private static int numOfPanes = 0;
	private char whoseTurn = 'X';
	private Cell[][] cell = new Cell[3][3];
	private GridPane gPane = new GridPane();
	private Label lblStatus = new Label("X's turn to play");
	private CareTaker careTaker = new CareTaker();
	private UndoButton jbtUndo = new UndoButton("Undo", careTaker);
	private RedoButton jbtRedo = new RedoButton("Redo", careTaker);
	private Button clearButton = new Button("Clear");

	protected RandomAccessFile raf;

	public EventHandler<MouseEvent> mouseEvent = (event) -> {
		((Command) event.getSource()).execute();
		if (event.getSource() instanceof Cell) {
			Memento memento = new Memento((Cell) event.getSource(), ((Cell) event.getSource()).getToken());
			this.careTaker.add(memento);
		}
	};

	private TicTacToePane() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				gPane.add(cell[i][j] = new Cell(this), i, j);
				cell[i][j].setOnMouseClicked(this.mouseEvent);
			}
		HBox hbox = new HBox();
		hbox.getChildren().addAll(jbtUndo, jbtRedo, clearButton, lblStatus);
		hbox.setSpacing(10);
		this.setBottom(hbox);
		this.setCenter(gPane);
		jbtUndo.setOnMouseClicked(this.mouseEvent);
		jbtRedo.setOnMouseClicked(this.mouseEvent);
		clearButton.setOnMouseClicked(e -> {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					cell[i][j].setToken(' ');
			;
			careTaker.getMementoList().clear();
			setTurn('X');
			setLabel(this.whoseTurn + "'s turn to play");
			try {
				raf.setLength(0);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		try {
			raf = new RandomAccessFile("aviv.txt", "rw");
			if (raf.length() == 0)
				return;
			raf.seek(0);
			while (raf.getFilePointer() != raf.length()) {
				whoseTurn = raf.readChar();
				String place = raf.readUTF();
				int i = place.charAt(0) - '0';
				int j = place.charAt(2) - '0';
				cell[i][j].setToken(whoseTurn);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public char getTurn() {
		return this.whoseTurn;
	}

	public void setTurn(char t) {
		this.whoseTurn = t;
	}

	public void setLabel(String s) {
		this.lblStatus.setText(s);
	}

	public static TicTacToePane getInstance() {
		numOfPanes++;
		if (numOfPanes <= MAX_PANES)
			return new TicTacToePane();
		return null;
	}

	/** Determine if the cell are all occupied */
	public boolean isFull() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (cell[i][j].getToken() == ' ')
					return false;
		return true;
	}

	/** Determine if the player with the specified token wins */
	public boolean isWon(char token) {
		for (int i = 0; i < 3; i++)
			if (cell[i][0].getToken() == token && cell[i][1].getToken() == token && cell[i][2].getToken() == token)
				return true;
		for (int j = 0; j < 3; j++)
			if (cell[0][j].getToken() == token && cell[1][j].getToken() == token && cell[2][j].getToken() == token)
				return true;
		if (cell[0][0].getToken() == token && cell[1][1].getToken() == token && cell[2][2].getToken() == token)
			return true;
		if (cell[0][2].getToken() == token && cell[1][1].getToken() == token && cell[2][0].getToken() == token)
			return true;
		return false;
	}

	public Cell[][] getCell() {
		return cell;
	}

	public void setCell(Cell[][] cell) {
		this.cell = cell;
	}

}
