package fi.solinor.assignment.monthlywagecalculationsystem;

/* one instance of this class stands for one employee */
public class EmployeeInfo {
	String mName;
	//double mTotalWorkingHour = 0;
	double mSalary = 0;
	boolean mTotalSalaryCaculated = false;
	
	public EmployeeInfo(String name){
		this.mName = name;
	}
	
	/* get salary by calculating salary if this function is called the first time. */
	public double getFinalSalary(){
		if (!this.mTotalSalaryCaculated){ // calculate only once when done processing all working hours.
			mSalary = Utils.removeValueAfterDot(mSalary, 2); // keep the precision to a cent.
			this.mTotalSalaryCaculated = true;
		}
		
		return mSalary;
	}
	
	/* processing working hours */
	public void processWorkingHour(Time beginTime, Time endTime){
		double beginTimeInHours = beginTime.toHours();
		double endTimeInHours = endTime.toHours();
		
		if(beginTimeInHours > endTimeInHours) endTimeInHours += 24; // over night.
		
		//this.mTotalWorkingHour += endTimeInHours - beginTimeInHours;
		double todayWorkingHour = endTimeInHours - beginTimeInHours;
		
		mSalary += Utils.calculateNormalHourlySalary(todayWorkingHour); // add regular wage + overtime compensation to evening work compensation.
		
		if(Setting.enableEvnWorkComp){
			/* add money to salary if it is in the evening. */
			/// try to find the over lap between the working hour and standard evening time duration.
			double lowFlag = Math.max(beginTimeInHours, ParsedSetting.getBeginTime().toHours());
			double highFlag = Math.min(endTimeInHours, ParsedSetting.getEndTime().toHours());
			
			if(highFlag > lowFlag){
				this.mSalary += (highFlag - lowFlag) * Setting.extraWage; // duration of over night working.
			}
		}
		
	}
	
	public String getName(){
		return this.mName;
	}
}