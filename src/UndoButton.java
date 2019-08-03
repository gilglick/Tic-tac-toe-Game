import javafx.scene.control.Button;

public class UndoButton extends Button implements Command {

	private CareTaker careTaker;

	public UndoButton(String text, CareTaker careTaker) {
		this.setText(text);
		this.careTaker = careTaker;
	}

	public void execute() {

		try {
			Memento memento= careTaker.getPrevious();
			if (memento != null) {
				memento.getCell().setToken(' ');
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
