import java.io.IOException;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class Cell extends Pane implements Command {

	private char token = ' ';
	private TicTacToePane t;

	public Cell(TicTacToePane t) {
		this.t = t;
		setStyle("-fx-border-color: black");
		this.setPrefSize(2000, 2000);
	}

	/** Return token */
	public char getToken() {
		return token;
	}

	/** Set a new token */
	public void setToken(char c) {
		token = c;
		
		if (token == 'X') {
			Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
			line1.endXProperty().bind(this.widthProperty().subtract(10));
			line1.endYProperty().bind(this.heightProperty().subtract(10));
			Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
			line2.startYProperty().bind(this.heightProperty().subtract(10));
			line2.endXProperty().bind(this.widthProperty().subtract(10));
			// Add the lines to the pane
			this.getChildren().addAll(line1, line2);
		} else if (token == 'O') {
			Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10,
					this.getHeight() / 2 - 10);
			ellipse.centerXProperty().bind(this.widthProperty().divide(2));
			ellipse.centerYProperty().bind(this.heightProperty().divide(2));
			ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
			ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
			ellipse.setStroke(Color.BLACK);
			ellipse.setFill(Color.WHITE);
			getChildren().add(ellipse); // Add the ellipse to the pane
		} else if (token == ' ') {
			this.getChildren().clear();
			t.setTurn((t.getTurn() == 'X') ? 'O' : 'X');
			
		}
	}

	/* Handle a mouse click event */
//	private void handleMouseClick() {
//		mooved to execute
//	}

	@Override
	public void execute() {
		if (token == ' ' && t.getTurn() != ' ') {
			setToken(t.getTurn()); // Set token in the cell
			try {
				t.raf.writeChar(token);
				t.raf.writeUTF(getMe(this));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Check game status
			if (t.isWon(t.getTurn())) {
				t.setLabel(t.getTurn() + " won! The game is over");
				t.setTurn(' ');// Game is over
			} else if (t.isFull()) {
				t.setLabel("Draw! The game is over");
				t.setTurn(' '); // Game is over
			} else { // Change the turn
				t.setTurn((t.getTurn() == 'X') ? 'O' : 'X');
				// Display whose turn
				t.setLabel(t.getTurn() + "'s turn");
			}
		}
	}

	public String getMe(Cell me) {
		for (int i = 0; i < t.getCell().length; i++) { // rows
			for (int j = 0; j < t.getCell()[0].length; j++) {// cols
				if (me == t.getCell()[i][j])
					return i + " " + j;
			}
		}
		return null;
	}

}