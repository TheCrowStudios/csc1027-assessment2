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
					DeleteArtifact();
					break;
				case 4:
					break;
				default:
					break;
			}
		}
	}
	
	static void AddArtifact() {
		// artifact name
		System.out.print("artifact name  : ");
		String artifactName = in.nextLine(); 
		System.out.print("engagement time: ");
		int engagementTime = in.nextInt(); 
		in.nextLine(); 
		artifacts.add(new Artifact(artifactName, engagementTime));
	}

	static void ViewArtifacts() {
		// artifact name
		for (int i = 0; i < artifacts.size(); i++) {
			System.out.println(artifacts.get(i));
			System.out.println();
		}
		
		Menu menu = new Menu("Search options", new String[] {"ID", "Name", "Go back" });
		int opt = -1;
		
		while (opt != 3) {
			ArrayList<Artifact> searchResults = new ArrayList<Artifact>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1: // search by id
					System.out.println("ID to look for: ");
					int id = in.nextInt();
					in.nextLine();
					Artifact artifact = Helper.findArtifactById(artifacts, id);
					if (artifact != null) searchResults.add(artifact);
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					searchResults = Helper.findArtifactsByName(artifacts, name);
					break;
				case 3: // return
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

	static void DeleteArtifact() {
		String input = null;
		
		while (input != "") {
			System.out.println("Enter the exhibit ID or leave empty to go back");
			input = in.nextLine();
		}
	}

	public static void ManageExhibits() {
		Menu menu = new Menu("Manage Exhibits", new String[] {"Add Exhibit", "View Exhibits", "Delete Exhibit", "Update Exhibits", "Go Back"});
		
		int opt = -1;
		
		while (opt != 5) {
			opt = menu.getUserChoice();
			
			switch (opt) {
				case 1:
					AddExhibit();
					break;
				case 2:
					ViewExhibits();
					break;
				case 3:
					DeleteExhibit();
					break;
				case 4:
					UpdateExhibits();
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
					Exhibit exhibit = Helper.findExhibitById(exhibits, id);
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
	
	public static void DeleteExhibit() {
		
	}
	
	public static void UpdateExhibits() {
		
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
			ArrayList<AnnualPlan> searchResults = new ArrayList<AnnualPlan>();
			opt = menu.getUserChoice();
			switch (opt) {
				case 1: // search by id
					System.out.println("ID to look for: ");
					int id = in.nextInt();
					in.nextLine();
					Exhibit exhibit = Helper.findExhibitById(exhibits, id);
					if (exhibit != null) searchResults.add(exhibit);
					break;
				case 2: // search by name
					System.out.println("Name to look for: ");
					String name = in.nextLine();
					ArrayList<IdNameClass> paramList = (ArrayList<IdNameClass>)exhibits;
					searchResults = Helper.findByName(, name);
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
