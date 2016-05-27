package fi.solinor.assignment.monthlywagecalculationsystem;

/* one instance of this class stands for one employee */
public class EmployeeInfo {
	String mName;
	double mTotalWorkingHour;
	double mSalary;
	boolean mTotalSalaryCaculated = false;
	
	public EmployeeInfo(String name){
		this.mName = name;
		mTotalWorkingHour = 0;
		mSalary = 0;
	}
	
	/* get salary by calculating salary if this function is called the first time. */
	public double getFinalSalary(){
		if (!this.mTotalSalaryCaculated){
			mSalary += Utils.calculateNormalHourlySalary(this.mTotalWorkingHour);
			this.mTotalSalaryCaculated = true;
		}
		
		return mSalary;
	}
	
	/* processing working hours */
	public void processWorkingHour(Time beginTime, Time endTime){
		double beginTimeInHours = beginTime.toHours();
		double endTimeInHours = endTime.toHours();
		
		if(beginTimeInHours > endTimeInHours) endTimeInHours += 24; // over night.
		
		this.mTotalWorkingHour += endTimeInHours - beginTimeInHours;
		
		if(Setting.enableEvnWorkComp){
			//add money to salary if it is in the evening.
			double lowFlag = Math.max(beginTimeInHours, ParsedSetting.getBeginTime().toHours());
			double highFlag = Math.min(endTimeInHours, ParsedSetting.getEndTime().toHours());
			
			this.mSalary += (highFlag - lowFlag) * Setting.extraWage; // duration of over night working.
		}
		
	}
	
	public String getName(){
		return this.mName;
	}
}