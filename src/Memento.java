import java.util.ArrayList;
import java.util.List;

public class Memento {

	private Cell cellState;
	private char charState;

	public Memento(Cell cell, char charState) {
		this.cellState = cell;
		this.charState = charState;
	}

	public Cell getCell() {
		return this.cellState;
	}

	public char getChar() {
		return this.charState;
	}
}

class CareTaker {
	private List<Memento> mementoList = new ArrayList<Memento>();
	private int index;

	public CareTaker() {
		index = mementoList.size();
	}

	public List<Memento> getMementoList() {
		return mementoList;
	}

	public void add(Memento state) {
		if (state != null)
			mementoList.add(state);
		index = mementoList.size();
	}

	public Memento getNext() {
		if (mementoList.isEmpty() || index > mementoList.size() - 1)
			return null;
		return mementoList.get(index++);
	}

	public Memento getPrevious() {
		if (mementoList.isEmpty() || index <= 0)
			return null;
		return mementoList.get(--index);
	}
}
