package part01;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.WeakHashMap;

public class QUBMuseum {
	static Scanner in = new Scanner(System.in);
	static Museum museum = new Museum();

	public static void main(String[] args) {
		museum = Helper.generateMuseum();
		Menu menu = new Menu("QUB Museum",
				new String[] { "Manage Artifacts", "Manage Exhibits", "Manage Annual Plan", "Exit" });

		int opt = -1;

		while (opt != 4) {
			opt = menu.getUserChoice();

			switch (opt) {
				case 1 -> manageArtifacts();
				case 2 -> manageExhibits();
				case 3 -> manageAnnualPlans();
				case 4 -> {
				}
				default -> {
				}
			}
		}
	}

	public static boolean confirmAction() {
		String input;

		do {
			input = in.nextLine();
			System.out.println("input: " + input);
		} while (!input.equals("y") && !input.equals("n"));

		return input.equals("y");
	}

	public static void manageArtifacts() {
		Menu menu = new Menu("Manage Artifacts",
				new String[] { "Add Artifact", "View Artifacts", "View artifacts sorted alphabetically",
						"Delete Artifact", "Go Back" });

		int opt = -1;
		Artifact artifact;

		while (opt != 5) {
			opt = menu.getUserChoice();

			switch (opt) {
				case 1 -> addArtifact(); // add artifact
				case 2 -> viewArtifacts(false); // view artifacts
				case 3 -> viewArtifacts(true);
				case 4 -> { // delete artifact
					artifact = selectArtifact();
					if (artifact != null) {
						System.out.println("Are you sure you want to delete artifact " + artifact.getName() + "y/n");
						if (confirmAction()) {
							if (deleteArtifact(artifact))
								System.out.println("Artifact deleted");
							else
								System.out.println("Could not delete artifact");
						} else {
							System.out.println("Did not delete artifact");
						}
					}
				}
				case 5 -> { // go back
				}
				default -> {
				}
			}
		}
	}

	static Artifact selectArtifact() {
		if (museum.artifacts.size() == 0) {
			System.out.println("There are no artifacts, add some and try again");
			return null;
		}

		Menu menu = new Menu("Search options", new String[] { "Select by ID", "View artifacts by name", "Go back" });
		int opt = -1;

		Artifact artifact = null;

		while (opt != 3 && artifact == null) {
			ArrayList<Artifact> searchResults = new ArrayList<Artifact>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1: // search by id
					System.out.println("ID to select: ");
					int id = in.nextInt();
					in.nextLine();
					artifact = (Artifact) Helper.findById(museum.artifacts, id);
					if (artifact != null) {
						System.out.println("Selected artifact:");
						System.out.println(artifact);
					} else
						System.out.println("Could not find artifact with specified id");
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Artifact>) Helper.findByName(museum.artifacts, name);
					if (searchResults.size() == 0)
						System.out.println("No artifacts found with that name");
					else
						System.out.println("Found the artifacts: ");

					for (int i = 0; i < searchResults.size(); i++) {
						System.out.println(searchResults.get(i));
					}
					break;
				case 3: // return
					break;
			}

		}

		System.out.println();
		return artifact;

	}

	static void addArtifact() {
		// artifact name
		System.out.print("Artifact name  : ");
		String artifactName = in.nextLine();
		Menu artifactTypeMenu = new Menu("Select artifact type",
				new String[] { "Artifact", "Painting", "Sculpture", "Digital", "Tactile", "Other" });
		ArtifactType type = ArtifactType.fromInt(artifactTypeMenu.getUserChoice());
		System.out.print("Engagement time: ");
		int engagementTime = in.nextInt();
		in.nextLine();
		museum.artifacts.add(new Artifact(artifactName, type, engagementTime));
		System.out.println("Artifact added with id: " + museum.artifacts.getLast().getId());
	}

	static void viewArtifacts(Boolean sorted) {
		// artifact name
		ArrayList<Artifact> sortedArtifacts = null;
		if (sorted)
			sortedArtifacts = (ArrayList<Artifact>) Helper.sortByName(museum.artifacts);
		else
			sortedArtifacts = museum.artifacts;

		for (int i = 0; i < sortedArtifacts.size(); i++) {
			System.out.println(sortedArtifacts.get(i));
			System.out.println();
		}

		if (museum.artifacts.size() == 0)
			System.out.println("There are no artifacts");

		Menu menu = new Menu("Search options", new String[] { "ID", "Name", "Exhibit ID", "Go back" });
		int opt = -1;

		while (opt != 4) {
			ArrayList<Artifact> searchResults = new ArrayList<Artifact>();
			opt = menu.getUserChoice();
			int id = 0;
			switch (opt) {
				case 1: // search by id
					System.out.println("ID to look for: ");
					id = in.nextInt();
					in.nextLine();
					Artifact artifact = (Artifact) Helper.findById(museum.artifacts, id);
					if (artifact != null)
						searchResults.add(artifact);
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Artifact>) Helper.findByName(museum.artifacts, name);
					break;
				case 3:
					System.out.println("Exhibit ID to look for: ");
					id = in.nextInt();
					in.nextLine();
					Exhibit exhibit = (Exhibit) Helper.findById(museum.exhibits, id);
					if (exhibit != null) {
						System.out.println(exhibit);
						ArrayList<Artifact> exhibitArtifacts = exhibit.getArtifacts();
						for (int i = 0; i < exhibitArtifacts.size(); i++) {
							searchResults.add(exhibitArtifacts.get(i));
						}
					}
				case 4: // return
					break;
			}

			// print search results
			searchResults = (ArrayList<Artifact>) Helper.sortByName(searchResults);

			for (int i = 0; i < searchResults.size(); i++) {
				System.out.println(searchResults.get(i));
				System.out.println();
			}

			if (searchResults.size() == 0)
				System.out.println("Could not find any artifacts with the specified search filters");
		}
	}

	static boolean deleteArtifact(Artifact artifact) {
		for (int i = 0; i < museum.artifacts.size(); i++) {
			if (museum.artifacts.get(i).getId() == artifact.getId()) {
				for (int j = 0; j < museum.exhibits.size(); j++) {
					museum.exhibits.get(i).removeArtifact(museum.artifacts.get(i).getId()); // remove artifact from all exhibits
				}
				museum.artifacts.remove(i); // remove artifact
				return true;
			}
		}

		return false;
	}

	public static void manageExhibits() {
		Menu menu = new Menu("Manage Exhibits",
				new String[] { "Add exhibit", "View exhibits", "View exhibits sorted alphabetically", "Delete exhibit",
						"Update exhibits", "Go back" });

		int opt = -1;

		while (opt != 6) {
			opt = menu.getUserChoice();
			Exhibit exhibit;
			String input = "";

			switch (opt) {
				case 1 -> addExhibit();
				case 2 -> viewExhibits(false);
				case 3 -> viewExhibits(true);
				case 4 -> {
					exhibit = selectExhibit();
					if (exhibit != null) {
						System.out.println("Are you sure you want to delete exhibit " + exhibit.getName() + " y/n");
						if (confirmAction()) {
							if (deleteExhibit(exhibit))
								System.out.println("Exhibit deleted");
							else
								System.out.println("Could not delete exhibit");
						} else
							System.out.println("Did not delete exhibit");
					}
				}
				case 5 -> {
					exhibit = selectExhibit();
					if (exhibit != null)
						updateExhibit(selectExhibit());
				}
				case 6 -> {
				}
				default -> {
				}
			}
		}
	}

	public static void addExhibit() {
		System.out.print("exhibit name  : ");
		String name = in.nextLine();
		museum.exhibits.add(new Exhibit(name));
		System.out.println("Exhibit added with id: " + museum.exhibits.getLast().getId());
	}

	public static void viewExhibits(boolean sorted) {
		ArrayList<Exhibit> sortedExhibits = null;
		if (sorted)
			sortedExhibits = (ArrayList<Exhibit>) Helper.sortByName(museum.exhibits);
		else
			sortedExhibits = museum.exhibits;

		if (sortedExhibits.size() == 0) {
			System.out.println("There are no exhibits, add some and try again");
			return;
		}

		for (int i = 0; i < sortedExhibits.size(); i++) {
			System.out.println(sortedExhibits.get(i));
			System.out.println();
		}

		Menu menu = new Menu("Search options", new String[] { "ID", "Name", "Go back" });
		int opt = -1;

		while (opt != 3) {
			ArrayList<Exhibit> searchResults = new ArrayList<Exhibit>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1: // search by id
					System.out.println("ID to look for: ");
					int id = in.nextInt();
					in.nextLine();
					Exhibit exhibit = (Exhibit) Helper.findById(museum.exhibits, id);
					if (exhibit != null)
						searchResults.add(exhibit);
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Exhibit>) Helper.findByName(museum.exhibits, name);
					break;
				case 3: // return
					break;
			}

			// print search results
			for (int i = 0; i < searchResults.size(); i++) {
				System.out.println(searchResults.get(i));
				System.out.println();
			}

			if (searchResults.size() == 0)
				System.out.println("Could not find any exhibits with the specified search filters");
		}

		System.out.println();
	}

	public static boolean deleteExhibit(Exhibit exhibit) {
		for (int i = 0; i < museum.exhibits.size(); i++) {
			if (museum.exhibits.get(i).getId() == exhibit.getId()) {
				museum.exhibits.remove(i);
				return true;
			}
		}

		return false;
	}

	public static Exhibit selectExhibit() {
		if (museum.exhibits.size() == 0) {
			System.out.println("There are no exhibits, add some and try again");
			return null;
		}

		Menu menu = new Menu("Search options", new String[] { "Select by ID", "View exhibits by name", "Go back" });
		int opt = -1;

		Exhibit exhibit = null;

		while (opt != 3 && exhibit == null) {
			ArrayList<Exhibit> searchResults = new ArrayList<Exhibit>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1 -> {
					// search by id
					System.out.println("ID to select: ");
					int id = in.nextInt();
					in.nextLine();
					System.out.println("");
					exhibit = (Exhibit) Helper.findById(museum.exhibits, id);
					if (exhibit != null) {
						System.out.println("Selected exhibit:");
						System.out.println(exhibit);
						return exhibit;
					} else
						System.out.println("Could not find exhibit with specified id");
				}
				case 2 -> {
					// search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Exhibit>) Helper.findByName(museum.exhibits, name);
					if (searchResults.size() == 0)
						System.out.println("No exhibits found with that name");
					else
						System.out.println("Found the following exhibits: ");

					for (int i = 0; i < searchResults.size(); i++) {
						System.out.println(searchResults.get(i));
					}
				}
				case 3 -> {
				}
			}
			// return

		}

		System.out.println();
		return exhibit;
	}

	public static void updateExhibit(Exhibit exhibit) {
		if (exhibit == null)
			return;

		Menu menu = new Menu("Update exhibit " + exhibit.getName(),
				new String[] { "Add artifact to exhibit", "Remove artifact from exhibit", "Go Back" });

		int opt = -1;
		Artifact artifact;

		while (opt != 3) {
			opt = menu.getUserChoice();

			switch (opt) {
				case 1 -> { // add artifact to exhibit
					artifact = selectArtifact();
					if (artifact != null) {
						int signId = -1;
						Sign sign = null;
						while (sign == null) {
							System.out.println("Enter sign text or existing sign ID: ");
							String signStr = in.nextLine();

							try {
								signId = Integer.parseInt(signStr);
							} catch (Exception e) {
							}

							if (signId != -1) {
								sign = (Sign) Helper.findById(museum.signs, signId);
								if (sign == null)
									System.out.println("Could not find sign with id: " + signId);
							} else {
								sign = new Sign(signStr);
								museum.signs.add(sign);
							}
						}

						System.out.println(
								"Are you sure you want to add artifact " + artifact.getName() + " with sign " + sign.getName() + " to the exhibit? y/n");
						if (confirmAction()) {
							if (exhibit.addArtifact(artifact, sign))
								System.out.println("Artifact added");
							else
								System.out.println("Could not add artifact");
						} else
							System.out.println("Did not add arifact");
					}
				}
				case 2 -> { // remove artifact from exhibit
					artifact = selectArtifact();
					if (artifact != null) {
						System.out.println("Are you sure you want to remove artifact " + artifact.getName()
								+ " from the exhibit? y/n");
						if (confirmAction()) {
							if (exhibit.removeArtifact(artifact.getId()))
								System.out.println("Artifact removed");
							else
								System.out.println("Could not remove artifact");
						} else
							System.out.println("Did not remove arifact");
					}
				}
				case 3 -> {
				}
				default -> {
				}
			}
		}
	}

	public static void manageAnnualPlans() {
		Menu menu = new Menu("Manage Annual Plan", new String[] { "Create Annual Plan", "View All Annual Plans",
				"View exhibits in an annual plan", "Modify Annual Plan", "Go Back" });

		int opt = -1;

		while (opt != 5) {
			opt = menu.getUserChoice();

			switch (opt) {
				case 1:
					createAnnualPlan();
					break;
				case 2:
					viewAnnualPlans();
					break;
				case 3:
					viewAnnualPlan(selectAnnualPlan());
				case 4:
					modifyAnnualPlan(selectAnnualPlan());
					break;
				case 5:
					break;
				default:
					break;
			}
		}
	}

	static void createAnnualPlan() {
		// Generate annual plan
		System.out.print("Annual plan name (e.g. name of the exhibit hall): ");
		String name = in.nextLine();
		museum.annualPlans.add(new AnnualPlan(name));
		System.out.println("Added new annual plan with id: " + museum.annualPlans.getLast().getId());
	}

	static void viewAnnualPlans() {
		for (int i = 0; i < museum.annualPlans.size(); i++) {
			System.out.println(museum.annualPlans.get(i));
			System.out.println();
		}

		Menu menu = new Menu("Search options", new String[] { "ID", "Name", "Go back" });
		int opt = -1;

		while (opt != 3) {
			ArrayList<Exhibit> searchResults = new ArrayList<Exhibit>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1 -> {
					// search by id
					System.out.println("ID to look for: ");
					int id = in.nextInt();
					in.nextLine();
					Exhibit exhibit = (Exhibit) Helper.findById(museum.exhibits, id);
					if (exhibit != null)
						searchResults.add(exhibit);
				}
				case 2 -> {
					// search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Exhibit>) Helper.findByName(museum.annualPlans, name);
				}
				case 3 -> {
				}
			}
			// return

			// print search results
			for (int i = 0; i < searchResults.size(); i++) {
				System.out.println(searchResults.get(i));
				System.out.println();
			}

			if (searchResults.size() == 0)
				System.out.println("Could not find any annual plans with the specified search filters");
		}

		System.out.println();

	}

	static void viewAnnualPlan(AnnualPlan annualPlan) {
		if (annualPlan == null)
			return;

		System.out.println(annualPlan);
		System.out.println("View the layout of the annual plan? y/n");
		if (confirmAction())
			System.out.println(annualPlan.toStringDetailed());
	}

	static void modifyAnnualPlan(AnnualPlan annualPlan) {
		if (annualPlan == null)
			return;

		Menu menu = new Menu("Update annual plan " + annualPlan.getName(),
				new String[] { "Add exhibit to annual plan", "Remove exhibit from annual plan",
						"Change the date of an exhibit", "View annual plan details", "View all exhibits in annual plan",
						"Go Back" });

		int opt = -1;
		Exhibit exhibit;

		while (opt != 6) {
			opt = menu.getUserChoice();

			switch (opt) {
				// add exhibit to annual plan
				case 1 -> { // add exhibit to annual plan
					exhibit = selectExhibit();
					if (exhibit != null) {
						// TODO - REMOVE THIS AND MAKE IT SELECT THE DATE AUTOMATICALLY
						////////
						// get month
						////////
						System.out.println("Enter month number for exhibit");
						Boolean hasError = true;
						int month = -1;
						while (hasError) {
							hasError = false;
							String monthStr = in.nextLine();
							if (monthStr.equals(""))
								break; // if the input is empty, use first available date
							try {
								month = Integer.parseInt(monthStr);
							} catch (Exception e) {
								System.out.println("Enter a valid number between 1 and 12");
								hasError = true;
							}
							if (annualPlan.getExhibitDates().contains(month)) {
								System.out.println("The month " + month
										+ "is already being used by another exhibit in the annual plan");
								hasError = true;
							}

							if (month < 1 || month > 12) {
								System.out.println("The month range must be between 1 and 12");
								hasError = true;
							}

							if (hasError) {
								System.out.println("Try again? y/n");
								if (!confirmAction()) {
									System.out.println("Did not add exhibit to annual plan");
									break;
								}
							}
						}
						////////

						if (!hasError) {
							System.out.println("Add exhibit " + exhibit.getName() + " to annual plan "
									+ annualPlan.getName() + "? y/n");
							// TODO - get date
							if (confirmAction()) {
								if (annualPlan.addExhibit(exhibit, month))
									System.out.println("Exhibit added");
								else
									System.out.println("Could not add exhibit, conflicting dates");
							} else
								System.out.println("Did not add exhibit");
						}
					}
				}
				// remove exhibit
				case 2 -> {
					System.out.println("Enter the month of the exhibit in the annual plan to remove it");
					int month = in.nextInt();
					if (annualPlan.removeExhibit(month))
						System.out.println("Exhibit removed from annual plan");
					else
						System.out.println("Could not remove exhibit as there isn't any at month " + month);
				}
				case 3 -> {
					exhibit = selectExhibit();
					if (exhibit != null) {

					}
				}
				case 4 -> System.out.println(annualPlan);
				case 5 -> System.out.println(annualPlan.toStringDetailed());
				case 6 -> {
					return;
				}
				default -> {
				}
			}
		}
	}

	public static AnnualPlan selectAnnualPlan() {
		if (museum.annualPlans.size() == 0) {
			System.out.println("There are no exhibits, add some and try again");
			return null;
		}

		Menu menu = new Menu("Search options",
				new String[] { "Select annual plan by ID", "View annual plans by name", "Go back" });
		int opt = -1;

		AnnualPlan annualPlan = null;

		while (opt != 3 && annualPlan == null) {
			ArrayList<Exhibit> searchResults = new ArrayList<Exhibit>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1: // search by id
					System.out.println("ID to select: ");
					int id = in.nextInt();
					in.nextLine();
					annualPlan = (AnnualPlan) Helper.findById(museum.annualPlans, id);
					if (annualPlan != null) {
						System.out.println("Selected annual plan:");
						System.out.println(annualPlan);
					} else
						System.out.println("Could not find annual plan with specified id");
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Exhibit>) Helper.findByName(museum.annualPlans, name);

					if (searchResults.size() == 0)
						System.out.println("No annual plans found with that name");
					else
						System.out.println("Found the following annual plans: ");

					for (int i = 0; i < searchResults.size(); i++) {
						System.out.println(searchResults.get(i));
					}
					break;
				case 3: // return
					break;
			}
		}

		System.out.println();
		return annualPlan;
	}

	/*
	 * static ArrayList<Object> sort(ArrayList<Object> array) {
	 * arrayToSort
	 * if (array.getClass() == (new ArrayList<Exhibit>()).getClass()) {
	 * System.out.println("true");
	 * array = (ArrayList<Exhibit>)array;
	 * }
	 * 
	 * }
	 */
}
