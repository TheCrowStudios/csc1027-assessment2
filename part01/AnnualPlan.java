package part01;

import java.util.ArrayList;

public class AnnualPlan extends IdNameClass {
	private ArrayList<Exhibit> exhibits = new ArrayList<Exhibit>();
	private ArrayList<String> exhibitDates = new ArrayList<String>();

	public AnnualPlan(String name) {
		super(name);
	}
	
	public ArrayList<Exhibit> getExhibits() {
		return exhibits;
	}
	
	public boolean addExhibit(Exhibit exhibit, String date) {
		if (Helper.findExhibitById(exhibits, exhibit.getId()) == null) {
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
		return super.toString();
	}
	
	public String toStringDetailed() {
		String detailed = toString() + "\n";
		
		if (exhibits.size() == 0) detailed += "Exhibits in annual plan:\n";
		
		for (int i = 0; i < exhibits.size(); i++) {
			detailed += exhibits.get(i) + "\n";
			detailed += exhibitDates.get(i) + "\n";
		}
		
		return detailed;
	}
}
