package fi.solinor.assignment.monthlywagecalculationsystem;

/* this file contains all the setting. If values inside of it have to be changed after its deployment, this file must be re-compiled. */
public class Setting {
	/* input src */
	public final static String dataSrcPath = "/Users/ruiyang/Desktop/Job Apps/Solinor/Assignment/recruitment-nut/"; // src path.
	public final static String dataSrcName = "HourList201403.csv";	// src name.
	
	/* hourly wage */
	public final static boolean enableHourlyWage = true;	// enable calculating hourly wage.
	public final static double	hourlyWage = 3.75;	// hourly wage.
	
	/* evening work compensation */
	public final static boolean enableEvnWorkComp = true;	// enable calculating evening work compensation wage.
	public final static double	extraWage = 1.15;	// extra wage per hour over night.
	public final static int startHour = 18;	// starting hour.
	public final static int startMinute = 00;	// starting minute.
	public final static int endHour = 06;		// ending hour.
	public final static int endMinute = 00;	// ending minute.
	
	/* overtime compensations */
	final static boolean enableOvertimeComp = true;	// enable calculating overtime compensation wage.
	public final static double[][] overtimeWageMilestone = {
		{ 2, 0.25},	// first two hours, hourly wage + 25%;
		{ 4, 0.50}, // next two hours, hourly wage + 50%;
		{16, 1.00}	// after that, hourly wage + 100%.
	};
}
