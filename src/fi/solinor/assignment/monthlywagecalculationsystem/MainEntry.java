package fi.solinor.assignment.monthlywagecalculationsystem;

/**
 * main entry for this wage calculation system.
 * @author rui yang
 */
public class MainEntry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Utils.printWelcomePage();
		
		if(!Utils.verifySetting()){
			Utils.printlnError("Failed to read setting.");
		}
	}

}
