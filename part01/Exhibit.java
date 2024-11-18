package part01;

import java.util.ArrayList;

public class Exhibit {
	private String name;
	private ArrayList<E> artifacts = new ArrayList<E>();
	
	public Exhibit(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
