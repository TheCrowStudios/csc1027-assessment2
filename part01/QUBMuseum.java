package part01;

import java.util.Scanner;

public class QUBMuseum {
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
		Scanner in = new Scanner(System.in);
		System.out.print("artifact name  : ");
		String artifactName = in.nextLine(); 
		System.out.print("engagement time: ");
		double engagementTime = in.nextDouble(); 
		in.nextLine(); 
		new Artifact(artifactName, engagementTime);
	}

	static void ViewArtifacts() {
		// artifact name
	}

	static void DeleteArtifact() {
		// artifact name
	}

	public static void ManageExhibits() {
		Menu menu = new Menu("Manage Exhibits", new String[] {"Add Exhibit", "View Exhibits", "Delete Exhibit", "Go Back"});
		
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
		
	}
	
	public static void ViewExhibits() {
		
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
		
	}

	static void ViewAnnualPlan() {
		
	}

	static void ModifyAnnualPlan() {
		
	}
}
