import javafx.scene.control.Button;

public class RedoButton extends Button implements Command {

	CareTaker careTaker;
	
	public RedoButton(String text, CareTaker careTaker) {
		this.setText(text);
		this.careTaker = careTaker;
	}

	@Override
	public void execute() {
		Memento m= careTaker.getNext();
		
		if(m != null) {
			m.getCell().setToken(m.getChar());
		}
	}

}
