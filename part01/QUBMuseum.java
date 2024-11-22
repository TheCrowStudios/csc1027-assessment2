package part01;

import java.util.ArrayList;
import java.util.Scanner;

public class QUBMuseum {
	static Scanner in = new Scanner(System.in);
	static ArrayList<Exhibit> exhibits = new ArrayList<Exhibit>();
	static ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
	static ArrayList<AnnualPlan> annualPlans = new ArrayList<AnnualPlan>(); 

	public static void main(String[] args) {
		Menu menu = new Menu("QUB Museum", new String[] {"Manage Artifacts", "Manage Exhibits", "Manage Annual Plan", "Exit"});
	
		int opt = -1;
		
		while (opt != 4) {
			opt = menu.getUserChoice();
			
			switch (opt) {
				case 1:
					manageArtifacts();
					break;
				case 2:
					manageExhibits();
					break;
				case 3:
					manageAnnualPlans();
					break;
				case 4:
					break;
				default:
					break;
			}
		}
	}
	
	public static boolean confirmAction() {
		String input;

		do {
			input = in.nextLine();
			System.out.println("input: " + input);
		} while (input != "y" && input != "n");
		
		return (input == "y" ? true : false);
	}
	
	public static void manageArtifacts() {
		Menu menu = new Menu("Manage Artifacts", new String[] {"Add Artifact", "View Artifacts", "Delete Artifact", "Go Back"});
		
		int opt = -1;
		Artifact artifact;
		String input = "";
		
		while (opt != 4) {
			opt = menu.getUserChoice();
			
			switch (opt) {
				case 1:
					addArtifact();
					break;
				case 2:
					viewArtifacts();
					break;
				case 3:
					artifact = selectArtifact();
					if (artifact != null) {
						System.out.println("Are you sure you want to delete exhibit " + artifact.getName() + "y/n");
						if (confirmAction()) {
							if (deleteArtifact(artifact)) System.out.println("Artifact deleted");
							else System.out.println("Could not delete artifact");
						} else {
							System.out.println("Did not delete artifact");
						}
					}
					break;
				case 4:
					break;
				default:
					break;
			}
		}
	}
	
	static Artifact selectArtifact() {
		if (artifacts.size() == 0) {
			System.out.println("There are no artifacts, add some and try again");
			return null;
		}
		
		Menu menu = new Menu("Search options", new String[] {"Select by ID", "View artifacts by name", "Go back" });
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
					artifact = (Artifact) Helper.findById(artifacts, id);
					if (artifact != null) {
						System.out.println("Selected artifact:");
						System.out.println(artifact);
					}
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Artifact>) Helper.findByName(artifacts, name);
					if (searchResults.size() == 0) System.out.println("No artifacts found with that name");
					else System.out.println("Found the artifacts: ");
					
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
		System.out.print("artifact name  : ");
		String artifactName = in.nextLine(); 
		System.out.print("engagement time: ");
		int engagementTime = in.nextInt(); 
		in.nextLine(); 
		artifacts.add(new Artifact(artifactName, engagementTime));
		System.out.println("Artifacn added with id: " + artifacts.getLast().getId());
	}

	static void viewArtifacts() {
		// artifact name
		for (int i = 0; i < artifacts.size(); i++) {
			System.out.println(artifacts.get(i));
			System.out.println();
		}

		if (artifacts.size() == 0) System.out.println("There are no artifacts");
		
		Menu menu = new Menu("Search options", new String[] {"ID", "Name", "Exhibit ID", "Go back" });
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
					Artifact artifact = Helper.findArtifactById(artifacts, id);
					if (artifact != null) searchResults.add(artifact);
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = Helper.findArtifactsByName(artifacts, name);
					break;
				case 3:
					System.out.println("Exhibit ID to look for: ");
					id = in.nextInt();
					in.nextLine();
					Exhibit exhibit = (Exhibit) Helper.findById(exhibits, id);
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
			for (int i = 0; i < searchResults.size(); i++) {
				System.out.println(searchResults.get(i));
				System.out.println();
			}
			
			if (searchResults.size() == 0) System.out.println("Could not find any artifacts with the specified search filters");
		}
	}

	static boolean deleteArtifact(Artifact artifact) {
		for (int i = 0; i < artifacts.size(); i++) {
			if (artifacts.get(i).getId() == artifact.getId()) {
				artifacts.remove(i);
				return true;
			}
		}
		
		return false;
	}

	public static void manageExhibits() {
		Menu menu = new Menu("Manage Exhibits", new String[] {"Add Exhibit", "View Exhibits", "Delete Exhibit", "Update Exhibits", "Go Back"});
		
		int opt = -1;
		
		while (opt != 5) {
			opt = menu.getUserChoice();
			Exhibit exhibit;
			String input = "";
			
			switch (opt) {
				case 1:
					addExhibit();
					break;
				case 2:
					viewExhibits();
					break;
				case 3:
					exhibit = selectExhibit();
					if (exhibit != null) {
						System.out.println("Are you sure you want to delete exhibit " + exhibit.getName() + "y/n");
						do {
							input = in.nextLine();
						} while (input != "y" && input != "n");
					}
					if (input == "y") deleteExhibit(exhibit);
					break;
				case 4:
					exhibit = selectExhibit();
					if (exhibit != null) updateExhibit(selectExhibit());
					break;
				case 5:
					break;
				default:
					break;
			}
		}
	}
	
	public static void addExhibit() {
		System.out.print("exhibit name  : ");
		String name = in.nextLine();
		exhibits.add(new Exhibit(name));
		System.out.println("Exhibit added with id: " + exhibits.getLast().getId());
	}
	
	public static void viewExhibits() {
		if (exhibits.size() == 0) {
			System.out.println("There are no exhibits, add some and try again");
			return;
		}

		for (int i = 0; i < exhibits.size(); i++) {
			System.out.println(exhibits.get(i));
			System.out.println();
		}
		
		
		Menu menu = new Menu("Search options", new String[] {"ID", "Name", "Go back" });
		int opt = -1;
		
		while (opt != 3) {
			ArrayList<Exhibit> searchResults = new ArrayList<Exhibit>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1: // search by id
					System.out.println("ID to look for: ");
					int id = in.nextInt();
					in.nextLine();
					Exhibit exhibit = (Exhibit) Helper.findById(exhibits, id);
					if (exhibit != null) searchResults.add(exhibit);
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = Helper.findExhibitsByName(exhibits, name);
					break;
				case 3: // return
					break;
			}
			
			// print search results
			for (int i = 0; i < searchResults.size(); i++) {
				System.out.println(searchResults.get(i));
				System.out.println();
			}
			
			if (searchResults.size() == 0) System.out.println("Could not find any exhibits with the specified search filters");
		}

		System.out.println();
	}
	
	public static boolean deleteExhibit(Exhibit exhibit) {
		for (int i = 0; i < exhibits.size(); i++) {
			if (exhibits.get(i).getId() == exhibit.getId()) {
				exhibits.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public static Exhibit selectExhibit() {
		if (exhibits.size() == 0) {
			System.out.println("There are no exhibits, add some and try again");
			return null;
		}
		
		Menu menu = new Menu("Search options", new String[] {"Select by ID", "View exhibits by name", "Go back" });
		int opt = -1;
		
		Exhibit exhibit = null;
		
		while (opt != 3 && exhibit == null) {
			ArrayList<Exhibit> searchResults = new ArrayList<Exhibit>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1: // search by id
					System.out.println("ID to select: ");
					int id = in.nextInt();
					in.nextLine();
					exhibit = (Exhibit) Helper.findById(exhibits, id);
					if (exhibit != null) {
						System.out.println("Selected exhibit:");
						System.out.println(exhibit);
					}
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Exhibit>) Helper.findByName(exhibits, name);
					if (searchResults.size() == 0) System.out.println("No exhibits found with that name");
					else System.out.println("Found the following exhibits: ");
					
					for (int i = 0; i < searchResults.size(); i++) {
						System.out.println(searchResults.get(i));
					}
					break;
				case 3: // return
					break;
			}
			
		}

		System.out.println();
		return exhibit;
	}
	
	public static void updateExhibit(Exhibit exhibit) {
		if (exhibit == null) return;
		
		Menu menu = new Menu("Update exhibit " + exhibit.getName(), new String[] {"Add artifact to exhibit", "Remove artifact from exhibit", "Go Back"});
		
		int opt = -1;
		Artifact artifact;
		
		while (opt != 4) {
			opt = menu.getUserChoice();
			
			switch (opt) {
				case 1:
					artifact = selectArtifact();
					break;
				case 2:
					artifact = selectArtifact();
					break;
				case 3:
					break;
				case 4:
					break;
				default:
					break;
			}
		}
	}
	
	public static void manageAnnualPlans() {
		Menu menu = new Menu("Manage Annual Plan", new String[] {"Create Annual Plan", "View All Annual Plans", "View exhibits in an annual plan", "Modify Annual Plan", "Go Back"});
		
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
		annualPlans.add(new AnnualPlan(name));
		System.out.println("Added new annual plan with id: " + annualPlans.getLast().getId());
	}

	static void viewAnnualPlans() {
		for (int i = 0; i < annualPlans.size(); i++) {
			System.out.println(annualPlans.get(i));
			System.out.println();
		}
		
		Menu menu = new Menu("Search options", new String[] {"ID", "Name", "Go back" });
		int opt = -1;
		
		while (opt != 3) {
			ArrayList<Exhibit> searchResults = new ArrayList<Exhibit>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1: // search by id
					System.out.println("ID to look for: ");
					int id = in.nextInt();
					in.nextLine();
					Exhibit exhibit = (Exhibit) Helper.findById(exhibits, id);
					if (exhibit != null) searchResults.add(exhibit);
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Exhibit>) Helper.findByName(annualPlans, name);
					break;
				case 3: // return
					break;
			}
			
			// print search results
			for (int i = 0; i < searchResults.size(); i++) {
				System.out.println(searchResults.get(i));
				System.out.println();
			}
			
			if (searchResults.size() == 0) System.out.println("Could not find any annual plans with the specified search filters");
		}

		System.out.println();
		
	}

	static void viewAnnualPlan(AnnualPlan annualPlan) {
		if (annualPlan == null) return;

	}

	static void modifyAnnualPlan(AnnualPlan annualPlan) {
		if (annualPlan == null) return;
		
		Menu menu = new Menu("Update annual plan " + annualPlan.getName(), new String[] {"Add exhibit to annual plan", "Remove exhibit from annual plan", "Change the date of an exhibit", "View annual plan details", "Go Back"});
		
		int opt = -1;
		Exhibit exhibit;
		
		while (opt != 5) {
			opt = menu.getUserChoice();
			
			switch (opt) {
				case 1:
					exhibit = selectExhibit();
					if (exhibit != null) {
						System.out.println("Add exhibit " + exhibit.getName() + " to annual plan " + annualPlan.getName() + "? y/n");
						// TODO - get date
						if (confirmAction()) {
							if (annualPlan.addExhibit(exhibit, null)) System.out.println("Exhibit added");
							else System.out.println("Could not add exhibit, conflicting dates");
						} else System.out.println("Did not add exhibit");
					}
					break;
				case 2:
					exhibit = selectExhibit();
					if (exhibit != null) {
						System.out.println("Add exhibit " + exhibit.getName() + " to annual plan " + annualPlan.getName() + "? y/n");
						// TODO - get date
						if (confirmAction()) {
							if (annualPlan.removeExhibit(exhibit.getId())) System.out.println("Exhibit removed from annual plan");
							else System.out.println("Could not remove exhibit");
						} else System.out.println("Did not remove exhibit");
					}
					break;
				case 3:
					exhibit = selectExhibit();
					if (exhibit != null) {
						
					}
					break;
				case 4:
					System.out.println(annualPlan);
					break;
				case 5:
					break;
				default:
					break;
			}
		}
	}

	public static AnnualPlan selectAnnualPlan() {
		if (annualPlans.size() == 0) {
			System.out.println("There are no exhibits, add some and try again");
			return null;
		}
		
		Menu menu = new Menu("Search options", new String[] {"Select annual plan by ID", "View annual plans by name", "Go back" });
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
					annualPlan = (AnnualPlan) Helper.findById(annualPlans, id);
					if (annualPlan != null) {
						System.out.println("Selected annual plan:");
						System.out.println(annualPlan);
					}
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = (ArrayList<Exhibit>) Helper.findByName(annualPlans, name);
					
					if (searchResults.size() == 0) System.out.println("No annual plans found with that name");
					else System.out.println("Found the following annual plans: ");
					
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
	
	/*static ArrayList<Object> sort(ArrayList<Object> array) {
		arrayToSort
		if (array.getClass() == (new ArrayList<Exhibit>()).getClass()) {
			System.out.println("true");
			array = (ArrayList<Exhibit>)array;
		}
		
	}*/
}
