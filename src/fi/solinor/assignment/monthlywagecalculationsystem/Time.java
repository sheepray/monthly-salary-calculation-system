package fi.solinor.assignment.monthlywagecalculationsystem;

/* Time class for hour and minute */
public class Time {
	int mHour;
	int mMinute;
	
	public Time(int hour, int minute){
		this.mHour = hour;
		this.mMinute = minute;
	}
	
	/* transfer minute to hour */
	public double toHours(){
		return toHoursFunc(this.mHour, this.mMinute);
	}
	
	/* factory method to transfer minute to hour */
	public static double toHoursFunc(int hour, int minute){
		return hour + (double)minute/60;
	}
	
	/* check whether this instance is bigger than the input Time instance or not */
	public boolean isBiggerThan(Time time){
		return this.toHours() > time.toHours();
	}
}
