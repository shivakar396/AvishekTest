package generic;

import java.text.DecimalFormat;

public class InterestCal {
	
	public float interestCalc(float principal_amount, float interest_rate, long numberOfdays) {
		float total_interest=0.0F;
		String drr = "";
		float dr=(interest_rate/365);
		DecimalFormat decfor = new DecimalFormat("0.000");
		
		float intrest_Of_oneday_in_rs=dr/100;
		for(int i=1;i<=numberOfdays;i++)
		{
			float interest_Of_upto_that_days=intrest_Of_oneday_in_rs*principal_amount;
			total_interest=total_interest+interest_Of_upto_that_days;
			//System.out.println(interest_Of_upto_that_days);
			principal_amount=principal_amount+interest_Of_upto_that_days;
			//System.out.println(principal_amount);
		}
		drr = decfor.format(total_interest);
		total_interest=Float.parseFloat(drr);
		int temp=(int)total_interest;
		float diff=total_interest-temp;
		drr = decfor.format(diff);
		diff=Float.parseFloat(drr);
		decfor = new DecimalFormat("0.00");
		if(diff>=0.005) {
			drr = decfor.format(total_interest);
		}
		total_interest=Float.parseFloat(drr);
		
		return total_interest;
	}
	public static void main(String[] args) {
		InterestCal cl=new InterestCal();
		System.out.println(cl.interestCalc(1180F, 36F, 10));
	}
}
