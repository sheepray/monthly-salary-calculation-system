package fi.solinor.assignment.monthlywagecalculationsystem;

/**
 * utility functions wrapper.
 * @author rui yang
 */
public class Utils {
	/* verify the settings */
	public static boolean verifySetting(){
		println("----------------------------------------------------");
		println("start processing setting file");
		try{
			if(Setting.dataSrcPath != null && Setting.dataSrcPath.length() > 0){
				printlnWithMark("data path is:" + Setting.dataSrcPath);
			}else{
				printlnErrorWithMark("dataSrcPath not set, abort!");
				return false;
			}
			
			if(Setting.dataSrcName != null && Setting.dataSrcName.length() > 0){
				printlnWithMark("data src name is:" + Setting.dataSrcName);
			}else{
				printlnErrorWithMark("dataSrcName not set, abort!");
				return false;
			}
			
			if(withinRangeExcludingLargeValue(Setting.hourlyWage, 0, 100000)){
				printlnWithMark("hourly wage is:" + Setting.hourlyWage);
			}else{
				printlnErrorWithMark("hourly wage not set or too big.");
				return false;
			}
			
			if(withinRangeExcludingLargeValue(Setting.extraWage, 0, 100000)){
				printlnWithMark("extra wage for evening work is:" + Setting.extraWage);
			}else{
				printlnErrorWithMark("extra wage for evening work not set or too big");
				return false;
			}
			
			if( is24Hour(Setting.startHour) && isMinute(Setting.startMinute)){
				printlnWithMark("evening work start at [" + Setting.startHour + ":" + Setting.startMinute + "]");
			}else{
				printlnErrorWithMark("startHour or/and startMinute not set or with incorrect value.");
				return false;
			}
			
			if( is24Hour(Setting.endHour) && isMinute(Setting.endMinute)){
				printlnWithMark("evening work end at [" + Setting.endHour + ":" + Setting.endMinute + "]");
			}else{
				printlnErrorWithMark("endHour or/and endMinute not set or with incorrect value.");
				return false;
			}
			
			double preMileStone = 0;
			for(int i = 0; i < Setting.overtimeWageMilestone.length; i++){
				if(withinRangeExcludingLargeValue( Setting.overtimeWageMilestone[i][0],
						0,
						17)
				){
					if(preMileStone >= Setting.overtimeWageMilestone[i][0]){
						printlnErrorWithMark("incorrect order of overtime measurements.");
						return false;
					}
					preMileStone = Setting.overtimeWageMilestone[i][0];
					
					printlnWithMark( i + "th measure milestone is:" + 
									 Setting.overtimeWageMilestone[i][0] + 
									 " = hourly wage + " + 
									 Setting.overtimeWageMilestone[i][1]);
				}
			}
		}
		catch(RuntimeException e){
			printlnErrorWithMark("[fatal error] required setting(s) not provided.");
			return false;
		}
		
		printlnWithMark("Processing setting done.");
		println("----------------------------------------------------");
		return true;
	}
	
	/* is hour in 24 format? */
	public static boolean is24Hour(int hour){
		return withinRangeExcludingLargeValue(hour, 0, 24);
	}
	
	/* is minute? */
	public static boolean isMinute(int minute){
		return withinRangeExcludingLargeValue(minute, 0, 60);
	}
	
	/* within the provided range excluding the large value. */
	public static boolean withinRangeExcludingLargeValue(double target, int small, int large){
		return target >= small && target < large;
	}
	
	/* System.out.println wrapper */
	public static void println(String content){
		if(content == null) return;
		System.out.println(content);
	}
	
	/* System.out.println wrapper */
	public static void printlnWithMark(String content){
		if(content == null) return;
		println("[+]" + content);
	}
	
	/* System.err.println wrapper */
	public static void printlnError(String error){
		if(error == null) return;
		System.err.println(error);
	}
	
	/* System.err.println wrapper */
	public static void printlnErrorWithMark(String error){
		if(error == null) return;
		printlnError("[-]" + error);
	}
	
	public static void printWelcomePage(){
		println("****** Welcome to console version of Monthly Salary Calculation System. ******");
	}
}
