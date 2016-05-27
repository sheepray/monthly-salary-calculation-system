package fi.solinor.assignment.monthlywagecalculationsystem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * main entry for this wage calculation system.
 * @author rui yang
 */
public class MainEntry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* print welcome page */
		Utils.printWelcomePage();
		
		/* verify setting */
		if(!Utils.verifySetting()){
			Utils.printlnError("Failed to read setting.");
		}
		
		/* processing salary data */
		Map<String, EmployeeInfo> employees = new HashMap<String, EmployeeInfo>();
		String month = "";
		try {
			month = Utils.processSalaryData(employees);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* output to console */
		Utils.println("Monthly wage " + month + ":");
		
		for (Map.Entry<String, EmployeeInfo> entry : employees.entrySet()) {
		    String id = entry.getKey();
		    EmployeeInfo employee = entry.getValue();

		    Utils.println(id + ", " + employee.getName() + ", " + employee.getFinalSalary());
		}
		
	}

}
