package part01;

import java.util.ArrayList;

public class AnnualPlan extends IdNameClass {
	private final ArrayList<AnnualPlanExhibit> exhibits = new ArrayList<>(12);

	public AnnualPlan(String name) {
		super(name);
		for (int i = 0; i < 12; i++) {
			exhibits.add(null);
		}
	}
	
	/*public ArrayList<Exhibit> getExhibits() {
		return exhibits;
	}*/
	
	public boolean addExhibit(Exhibit exhibit, int month) {
		if (month < 1 || month > 12 && month != -1) return false;

		// find first available month
		if (month == -1) {
			boolean foundMonth = false;

			for (int i = 0; i < exhibits.size(); i++) {
				if (exhibits.get(i) == null) {
					foundMonth = true;
					month = i;
					break;
				}
			}

			if (!foundMonth) return false;
		}

		// TODO - check for conflicts between other annual plans
		exhibits.set(month, new AnnualPlanExhibit(exhibit, month));
		return true;
	}
	
	public boolean removeExhibit(int month) {
		if (exhibits.get(month) != null) {
			exhibits.remove(month);
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	public String toStringDetailed() {
		String detailed = toString() + "\n";
		
		if (exhibits.size() > 0) detailed += "Exhibits in annual plan:\n";
		else detailed += "No exhibits in annual plan";
		
		for (int i = 0; i < exhibits.size(); i++) {
			if (exhibits.get(i) == null) continue;
			detailed += exhibits.get(i) + "\n";
		}
		
		return detailed;
	}

    public ArrayList<Integer> getExhibitDates() {
		ArrayList<Integer> exhibitMonths = new ArrayList<>(); 

		for (int i = 0; i < exhibits.size(); i++) {
			if (exhibits.get(i) != null) exhibitMonths.add(i);
		}

        return exhibitMonths;
    }
}
