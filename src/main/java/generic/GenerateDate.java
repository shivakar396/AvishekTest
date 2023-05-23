package generic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GenerateDate {
	
	public String getCurrentDate(String date_format) {
		Calendar cal = Calendar.getInstance();       
		return new SimpleDateFormat(date_format).format(cal.getTime());
	}
	
	public  String getPastDate(String date_format, int number_of_days) {
		
		Calendar cal = Calendar.getInstance();
//      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
		cal.add(Calendar.DATE, -number_of_days);

        return new SimpleDateFormat(date_format).format(cal.getTime());
	}
	public  String getFuturetDate(String date_format, int number_of_days) {
		
		Calendar cal = Calendar.getInstance();
//      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
		cal.add(Calendar.DATE, number_of_days);
		  
        return new SimpleDateFormat(date_format).format(cal.getTime());
	}
	
	public  String getDateFromThePastDay(String date_format, int start_number_of_days, int end_number_of_days) {
		
		Calendar cal=Calendar.getInstance();
		String date=new SimpleDateFormat(date_format).format(cal.getTime());
		cal.add(Calendar.DATE, -start_number_of_days);
		date=new SimpleDateFormat(date_format).format(cal.getTime());
		cal.add(Calendar.DATE, end_number_of_days);
		date=new SimpleDateFormat(date_format).format(cal.getTime());
		
//      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
        return date;
	}
	
	public  long getNumberOfDays(String invoice_date, String invoice_due_date) throws ParseException 
	{
		String date=getCurrentDate("yyyy-MM-dd");
		DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		Date todaydate=dateFormat.parse(date);
		
		Date invcdate=dateFormat.parse(invoice_date);
		Date invcduedate=dateFormat.parse(invoice_due_date);
		long diffInMillies = Math.abs(invcdate.getTime() - todaydate.getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    
//      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
	    
        return diff;
	}
	
	public static void main(String[] args) {
		GenerateDate dt=new GenerateDate();
		String date=dt.getDateFromThePastDay("yyyy-MM-dd'T'00:00:00.000'Z'", 15, 6);
		System.out.println(date);
	}
}
