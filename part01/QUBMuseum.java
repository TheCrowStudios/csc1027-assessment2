package part01;

import java.awt.im.InputMethodHighlight;
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
					ManageArtifacts();
					break;
				case 2:
					ManageExhibits();
					break;
				case 3:
					ManageAnnualPlan();
					break;
				case 4:
					break;
				default:
					break;
			}
		}
	}
	
	public static void ManageArtifacts() {
		Menu menu = new Menu("Manage Artifacts", new String[] {"Add Artifact", "View Artifacts", "Delete Artifact", "Go Back"});
		
		int opt = -1;
		Artifact artifact;
		String input = "";
		
		while (opt != 4) {
			opt = menu.getUserChoice();
			
			switch (opt) {
				case 1:
					AddArtifact();
					break;
				case 2:
					ViewArtifacts();
					break;
				case 3:
					artifact = selectArtifact();
					if (artifact != null) {
						System.out.println("Are you sure you want to delete exhibit " + artifact.getName() + "y/n");
						do {
							input = in.nextLine();
						} while (input != "y" && input != "n");
					}
					if (input == "y") DeleteArtifact(artifact);
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
						System.out.println("Found artifact:");
						System.out.println(artifact);
					}
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = Helper.findArtifactsByName(artifacts, name);
					break;
				case 3: // return
					break;
			}
			
		}

		System.out.println();
		return artifact;
		
	}
	
	static void AddArtifact() {
		// artifact name
		System.out.print("artifact name  : ");
		String artifactName = in.nextLine(); 
		System.out.print("engagement time: ");
		int engagementTime = in.nextInt(); 
		in.nextLine(); 
		artifacts.add(new Artifact(artifactName, engagementTime));
		System.out.println("Artifacn added with id: " + artifacts.getLast().getId());
	}

	static void ViewArtifacts() {
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

	static boolean DeleteArtifact(Artifact artifact) {
		for (int i = 0; i < artifacts.size(); i++) {
			if (artifacts.get(i).getId() == artifact.getId()) {
				artifacts.remove(i);
				return true;
			}
		}
		
		return false;
	}

	public static void ManageExhibits() {
		Menu menu = new Menu("Manage Exhibits", new String[] {"Add Exhibit", "View Exhibits", "Delete Exhibit", "Update Exhibits", "Go Back"});
		
		int opt = -1;
		
		while (opt != 5) {
			opt = menu.getUserChoice();
			Exhibit exhibit;
			String input = "";
			
			switch (opt) {
				case 1:
					AddExhibit();
					break;
				case 2:
					ViewExhibits();
					break;
				case 3:
					exhibit = SelectExhibit();
					if (exhibit != null) {
						System.out.println("Are you sure you want to delete exhibit " + exhibit.getName() + "y/n");
						do {
							input = in.nextLine();
						} while (input != "y" && input != "n");
					}
					if (input == "y") DeleteExhibit(exhibit);
					break;
				case 4:
					exhibit = SelectExhibit();
					UpdateExhibit(SelectExhibit());
					break;
				case 5:
					break;
				default:
					break;
			}
		}
	}
	
	public static void AddExhibit() {
		System.out.print("exhibit name  : ");
		String name = in.nextLine();
		exhibits.add(new Exhibit(name));
		System.out.println("Exhibit added with id: " + exhibits.getLast().getId());
	}
	
	public static void ViewExhibits() {
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
	
	public static boolean DeleteExhibit(Exhibit exhibit) {
		for (int i = 0; i < exhibits.size(); i++) {
			if (exhibits.get(i).getId() == exhibit.getId()) {
				exhibits.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public static Exhibit SelectExhibit() {
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
						System.out.println("Found exhibit:");
						System.out.println(exhibit);
					}
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = Helper.findExhibitsByName(exhibits, name);
					break;
				case 3: // return
					break;
			}
			
		}

		System.out.println();
		return exhibit;
	}
	
	public static void UpdateExhibit(Exhibit exhibit) {
		if (exhibit == null) return;
		
	}
	
	public static void ManageAnnualPlan() {
		Menu menu = new Menu("Manage Annual Plan", new String[] {"Create Annual Plan", "View Annual Plan", "Modify Annual Plan", "Go Back"});
		
		int opt = -1;
		
		while (opt != 4) {
			opt = menu.getUserChoice();
			
			switch (opt) {
				case 1:
					CreateAnnualPlan();
					break;
				case 2:
					ViewAnnualPlan();
					break;
				case 3:
					ModifyAnnualPlan();
					break;
				case 4:
					break;
				default:
					break;
			}
		}
	}
	
	static void CreateAnnualPlan() {
		// Generate annual plan
		System.out.print("Annual plan name: ");
		String name = in.nextLine();
		annualPlans.add(new AnnualPlan(name));
		System.out.println("Added new annual plan with id: " + annualPlans.getLast().getId());
	}

	static void ViewAnnualPlan() {
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
			
			if (searchResults.size() == 0) System.out.println("Could not find any exhibits with the specified search filters");
		}

		System.out.println();
		
	}

	static void ModifyAnnualPlan() {
		
	}
	
	/*static ArrayList<Object> sort(ArrayList<Object> array) {
		arrayToSort
		if (array.getClass() == (new ArrayList<Exhibit>()).getClass()) {
			System.out.println("true");
			array = (ArrayList<Exhibit>)array;
		}
		
	}*/
}
