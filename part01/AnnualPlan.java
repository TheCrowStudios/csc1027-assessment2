package part01;

import java.util.ArrayList;

public class AnnualPlan extends IdNameClass {
	private int id;
	private String name;
	private ArrayList<Exhibit> exhibits = new ArrayList<Exhibit>();
	private ArrayList<String> exhibitDates = new ArrayList<String>();
	static int currentId = 0;

	public AnnualPlan(String name) {
		super(name);
	}
	
	public int getId() {
		return id;
	}

	public ArrayList<Exhibit> getExhibits() {
		return exhibits;
	}
	
	public boolean addExhibit(Exhibit exhibit, String date) {
		if (Helper.findExhibitById(exhibits, id) == null) {
			exhibits.add(exhibit);
			exhibitDates.add(date);
			return true;
		}
		
		return false;
	}
	
	public boolean removeExhibit(int id) {
		for (int i = 0; i < exhibits.size(); i++) {
			if (exhibits.get(i).getId() == id) {
				exhibits.remove(i);
				exhibitDates.remove(i);
				return true;
			}
		}
		
		return false;
	}
	public String toString() {
		return String.format("ID: %s\n"
				+ "Name: %s", id, name);
	}

	public String getName() {
		return name;
	}
}
