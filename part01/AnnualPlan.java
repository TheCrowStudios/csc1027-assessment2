package part01;

import java.util.ArrayList;

public class AnnualPlan extends IdNameClass {
	private final ArrayList<AnnualPlanExhibit> exhibits = new ArrayList<>(12);
	private static int currentId;

	public AnnualPlan(String name) {
		super(currentId, name);
		currentId += 1;
		for (int i = 0; i < 12; i++) { // fill exhibits arraylist with null values as they are inserted into the
										// arraylist at the position of the month
			exhibits.add(null);
		}
	}

	/*
	 * public ArrayList<Exhibit> getExhibits() {
	 * return exhibits;
	 * }
	 */

	/**
	 * adds exhibit to the exhibits arraylist at the position of the month so that
	 * it's always sorted
	 * 
	 * @param exhibit
	 * @param month
	 * @return
	 */
	public boolean addExhibit(Exhibit exhibit, int month) {
		if ((month < 1 || month > 12) && month != -1)
			return false;

		// find first available month
		if (month == -1) {
			boolean foundMonth = false;

			for (int i = 0; i < exhibits.size(); i++) {
				if (exhibits.get(i) == null) {
					foundMonth = true;
					month = i + 1;
					break;
				}
			}

			if (!foundMonth)
				return false;
		}

		// TODO - check for conflicts between other annual plans
		exhibits.set(month - 1, new AnnualPlanExhibit(exhibit, month)); // add exhibit to the position of the arraylist
																		// that corresponds to the month
		return true;
	}

	public boolean removeExhibit(int month) {
		try {
			if (exhibits.get(month - 1) != null) {
				exhibits.remove(month - 1);
				return true;
			}
		} catch (Exception e) {

		}

		return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public String toStringDetailed() {
		String detailed = toString() + "\n";

		if (exhibits.size() > 0)
			detailed += "Exhibits in annual plan:\n";
		else
			detailed += "No exhibits in annual plan";

		for (int i = 0; i < exhibits.size(); i++) {
			if (exhibits.get(i) == null)
				continue;
			detailed += exhibits.get(i) + "\n";
		}

		return detailed;
	}

	public ArrayList<Integer> getExhibitDates() {
		ArrayList<Integer> exhibitMonths = new ArrayList<>();

		for (int i = 0; i < exhibits.size(); i++) {
			if (exhibits.get(i) != null)
				exhibitMonths.add(i);
		}

		return exhibitMonths;
	}
}
