package jumpingalien.model;

import java.util.ArrayList;

public class GenericPosition<P> {
	
	public ArrayList<P> get() {
		return this.position;
	}

	public void set(ArrayList<P> position) {
		this.position = position;
	}
	
	private ArrayList<P> position = new ArrayList<P>(2);
}
