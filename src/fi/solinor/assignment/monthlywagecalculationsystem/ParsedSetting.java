package fi.solinor.assignment.monthlywagecalculationsystem;

/* this class contains the settings read from Setting.java which are parsed into corresponding objects */
public class ParsedSetting {
	private static Time mBeginTime;
	private static Time mEndTime;
	
	static{
		mBeginTime = new Time(Setting.startHour, Setting.startMinute);
		Time tmpEndTime = new Time(Setting.endHour, Setting.endMinute);
		
		if(mBeginTime.toHours() > tmpEndTime.toHours()){
			mEndTime = new Time(Setting.endHour + 24, Setting.endMinute); // add 24 if it has passed the 24 clock.
		}
		else{
			mEndTime = new Time(Setting.endHour, Setting.endMinute);
		}
		
		tmpEndTime = null;
	}
	
	public static Time getBeginTime(){
		return mBeginTime;
	}
	
	public static Time getEndTime(){
		return mEndTime;
	}
}
