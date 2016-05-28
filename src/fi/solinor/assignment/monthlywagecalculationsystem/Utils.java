package fi.solinor.assignment.monthlywagecalculationsystem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * utility functions wrapper.
 * @author rui yang
 */
public class Utils {
	/**
	 * verify the settings.
	 * @return true if setting is in correct format. Otherwise false.
	 */
	public static boolean verifySetting(){
		println("----------------------------------------------------");
		println("start processing setting file");
		try{
			/* verify dataSrcPath */
			if(Setting.dataSrcPath != null && Setting.dataSrcPath.length() > 0){
				printlnWithMark("data path is:" + Setting.dataSrcPath);
			}else{
				printlnErrorWithMark("dataSrcPath not set, abort!");
				return false;
			}
			
			/* verify dataSrcName */
			if(Setting.dataSrcName != null && Setting.dataSrcName.length() > 0){
				printlnWithMark("data src name is:" + Setting.dataSrcName);
			}else{
				printlnErrorWithMark("dataSrcName not set, abort!");
				return false;
			}
			
			if(Setting.enableHourlyWage){
				/* verify hourly wage */
				if(withinRangeExcludingLargeValue(Setting.hourlyWage, 0, 100000)){
					printlnWithMark("hourly wage is:" + Setting.hourlyWage);
				}else{
					printlnErrorWithMark("hourly wage not set or too big, abort!");
					return false;
				}
			}
			
			if(Setting.enableEvnWorkComp){
				/* verify extraWage */
				if(withinRangeExcludingLargeValue(Setting.extraWage, 0, 100000)){
					printlnWithMark("extra wage for evening work is:" + Setting.extraWage);
				}else{
					printlnErrorWithMark("extra wage for evening work not set or too big, abort!");
					return false;
				}
				
				/* verify startHour and startMinute */
				if( is24Hour(Setting.startHour) && isMinute(Setting.startMinute)){
					printlnWithMark("evening work start at [" + Setting.startHour + ":" + Setting.startMinute + "]");
				}else{
					printlnErrorWithMark("startHour or/and startMinute not set or with incorrect value, abort!");
					return false;
				}
				
				/* verify endHour and endMinute */
				if( is24Hour(Setting.endHour) && isMinute(Setting.endMinute)){
					printlnWithMark("evening work end at [" + Setting.endHour + ":" + Setting.endMinute + "]");
				}else{
					printlnErrorWithMark("endHour or/and endMinute not set or with incorrect value, abort!");
					return false;
				}
			}
			
			if(Setting.enableOvertimeComp){
				/* verify overtimeWageMilestone */
				double preMileStone = 0;
				for(int i = 0; i < Setting.overtimeWageMilestone.length; i++){
					if(
						withinRangeExcludingLargeValue( Setting.overtimeWageMilestone[i][0],
									0,
									17)
					){
						if(preMileStone >= Setting.overtimeWageMilestone[i][0]){
							printlnErrorWithMark("incorrect order of overtime measurements, abort");
							return false;
						}
						preMileStone = Setting.overtimeWageMilestone[i][0];
						
						printlnWithMark( i + "th measure milestone is:" + 
										 Setting.overtimeWageMilestone[i][0] + 
										 " = hourly wage + " + 
										 Setting.overtimeWageMilestone[i][1]);
					}else{
						printlnErrorWithMark("measure milestone not set or the value is incorrect, abort!");
						return false;
					}
				}// end of for loop.
			}// end of checking Setting.enableOvertimeComp.
			
		}// end of try.
		catch(RuntimeException e){
			printlnErrorWithMark("[fatal error] required setting(s) not provided.");
			return false;
		}
		
		printlnWithMark("Processing setting done.");
		println("----------------------------------------------------");
		return true;
	}// end of verifySetting().
	
	/**
	 * calculate salary based on normal working hours. If time exceed the 8 hours, extra wage will be added.
	 * @param workingHour
	 * @return wage based on the workingHour.
	 */
	public static double calculateNormalHourlySalary(double workingHour){
		if(workingHour < 0 && workingHour > 24) {
			printlnError("incorrect working hour.");
			return 0;
		}
		
		double salary = 0;
		
		if(Setting.enableHourlyWage){
			salary = Setting.hourlyWage * workingHour;
			
			if(Setting.enableOvertimeComp){
				/* add extra working hour wage */
				workingHour -= 8;
				double calculatedHour = 0;
				int i = 0;
				while(calculatedHour < workingHour && i < Setting.overtimeWageMilestone.length){
					salary += (Math.min(workingHour, Setting.overtimeWageMilestone[i][0]) - calculatedHour) * 
							Setting.overtimeWageMilestone[i][1] * // percentage.
							Setting.hourlyWage;	// hourly wage.
					
					calculatedHour = Setting.overtimeWageMilestone[i][0];
					
					i++;
				}
			}
		}
		
		return salary;
	}// end of calculateNormalHourlySalary().
	
	/**
	 * process the file which contains employees' salary data.
	 * @param employees parsing result.
	 * @return String month. 
	 * @throws IOException 
	 */
	public static String processSalaryData(Map<String, EmployeeInfo> employees) throws IOException{
		/* read file */
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(Setting.dataSrcPath + Setting.dataSrcName);
		} catch (FileNotFoundException e) {
			printlnError("file not found. Please check your setting of dataSrcPath and dataSrcName. Make sure there is a / at the end of dataSrcPath.");
			return null;
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strMonth = null;
		boolean monthSet = false;
		/* Read File Line By Line */
		String strLine = br.readLine(); // skip first line.
		while ((strLine = br.readLine()) != null){
			println("[processing] " + strLine);
			
			String[] strTokens = strLine.split(",");
			
			/* to check input is valid or not */
			assert strTokens.length == 5;
			
			/* get the month only once */
			if(!monthSet){
				strMonth = deleteDate(strTokens[2]);
				monthSet = true;
			}
			
			String name = strTokens[0];
			String id = strTokens[1];
			
			/* find employee with id */
			EmployeeInfo employee = employees.get(id);
			if(employee == null){
				//not found then create one.
				employee = new EmployeeInfo(name);
				employees.put(id, employee);
			}
			
			String strBeginTime = strTokens[3];
			String strEndTime = strTokens[4];
			Time beginTime = parseStringToTime(strBeginTime);
			Time endTime = parseStringToTime(strEndTime);
			
			employee.processWorkingHour(beginTime, endTime);
		}

		//Close the input stream
		br.close();
		
		return strMonth;
	}// end of processSalaryData(...).
	
	/**
	 * this function is to make a precision of a double value.
	 * @param target target value.
	 * @param position position after dot.
	 * @return a double value with a precision of the target value.
	 */
	public static double removeValueAfterDot(double target, int position){
		if(position <= 0) return target;
		double var = target * Math.pow(10, position);
		if(Double.MAX_VALUE < var) return target; // value is too big to convert.
		
		return ( (long)var ) / Math.pow(10, position);
	}
	
	/* parse the time presented in string to Time instance */
	public static Time parseStringToTime(String strTime){
		String[] timeTokens = strTime.split(":");
		
		assert timeTokens.length == 2;
		
		return new Time(Integer.parseInt(timeTokens[0]), 
						Integer.parseInt(timeTokens[1]));
	}
	
	/* remove date from full syntax of date. For instance, 12.03.2016 to 03.2016 */
	public static String deleteDate(String date){
		int index = date.indexOf(".");
		return index == -1? null : date.substring(index + 1);
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
