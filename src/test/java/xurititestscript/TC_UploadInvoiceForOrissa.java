package xurititestscript;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import generic.FileCredential;
import generic.FileLib;
import generic.GenerateDate;
import generic.MongoDBCls;
import io.restassured.response.Response;
import utility.JsonResCls;
import utility.XuritiBuyer;
import utility.XuritiSeller;

public class TC_UploadInvoiceForOrissa {
	
	@Test(priority = 1)
	public void TM0001_uploadInvoice() throws IOException
	{
		FileLib fl=new FileLib();
//		String byrcmpnyid=fl.getPropertyData("ANUPAMBUYERID");
//		XuritiBuyer xuriti_buyer=new XuritiBuyer();
//		Response response= xuriti_buyer.getBuyer(byrcmpnyid);
//		float buyer_avl_limit=xuriti_buyer.getBuyerAvailableCredit(response);
		
		XuritiSeller xuriti_seller=new XuritiSeller();
		GenerateDate dt=new GenerateDate();
		String date=dt.getCurrentDate("yyyy/MM/dd");
		
		Response response = xuriti_seller.setInvoiceUpload("OR_Invoice.xlsx");
		
		System.out.println(response.prettyPrint());
		
		//OR_AM_TEST_001
		String pastdate=dt.getPastDate("yyyy-MM-dd'T'00:00:00.000'Z'", 8);
		System.out.println(pastdate);
		String duedate=dt.getDateFromThePastDay("yyyy-MM-dd'T'00:00:00.000'Z'",16,6);
		System.out.println(duedate);
		
		FileCredential fc=new FileCredential();
		String db_nm=fc.readCredential("DatabaseName");
		MongoDBCls mng=new MongoDBCls();
		MongoClient mongoClient = mng.getAllCollection();
		MongoDatabase database=mongoClient.getDatabase(db_nm);
		MongoCollection collection=database.getCollection("invoices");
		//collection.updateOne(Filters.eq("invoice_number", "LNV000005"), Updates.set("invoice_due_date", duedate));
		
		pastdate=dt.getPastDate("yyyy-MM-dd'T'00:00:00.000'Z'", 34);
		duedate=dt.getDateFromThePastDay("yyyy-MM-dd'T'00:00:00.000'Z'", 34, 90);
		
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_006"), Updates.set("nbfc_flag", true));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_007"), Updates.set("nbfc_flag", true));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_008"), Updates.set("nbfc_flag", true));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_009"), Updates.set("nbfc_flag", true));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_010"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_011"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_011"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_011"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_012"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_012"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_012"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_013"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_013"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_013"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_014"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_014"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_014"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_015"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_015"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_015"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_016"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_016"), Updates.set("invoice_due_date", duedate));

		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_017"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_017"), Updates.set("invoice_due_date", duedate));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_018"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_018"), Updates.set("invoice_due_date", duedate));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_019"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_019"), Updates.set("invoice_due_date", duedate));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_020"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_TEST_020"), Updates.set("invoice_due_date", duedate));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_006"), Updates.set("nbfc_flag", true));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_007"), Updates.set("nbfc_flag", true));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_008"), Updates.set("nbfc_flag", true));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_009"), Updates.set("nbfc_flag", true));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_010"), Updates.set("nbfc_flag", true));
		
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_011"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_011"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_011"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_012"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_012"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_012"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_013"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_013"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_013"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_014"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_014"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_014"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_015"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_015"), Updates.set("invoice_due_date", duedate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_015"), Updates.set("nbfc_flag", true));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_016"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_016"), Updates.set("invoice_due_date", duedate));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_017"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_017"), Updates.set("invoice_due_date", duedate));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_018"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_018"), Updates.set("invoice_due_date", duedate));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_019"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_019"), Updates.set("invoice_due_date", duedate));
		
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_020"), Updates.set("invoice_date", pastdate));
		collection.updateOne(Filters.eq("invoice_number", "OR_AM_TEST_020"), Updates.set("invoice_due_date", duedate));
		
		mongoClient.close();	
	}
	@Test(priority = 2)
	public void confirm() throws IOException, InterruptedException 
	{
		XuritiBuyer xuriti_byr = new XuritiBuyer();
		Response response = xuriti_byr.getBuyerInvoices("6450a862c7dc91348d7ab232");
		
		//System.out.println(response.prettyPrint());
		String invoicenumber="", invoioceid="", invoicetype="", invoicests="";
		ArrayList<String> all_invoice_number=new ArrayList<String>();
		ArrayList<String> all_invoioce_id=new ArrayList<String>();
		JsonResCls js = new JsonResCls();
		int i=0;
		while(invoicenumber!=null)
		{
			invoicenumber=js.getStringJsonValue(response, "invoice["+i+"].invoice_number");
			invoicests=js.getStringJsonValue(response, "invoice["+i+"].invoice_status");
			invoicetype=js.getStringJsonValue(response, "invoice["+i+"].invoice_type");
			invoioceid=js.getStringJsonValue(response, "invoice["+i+"]._id");
			if(invoicenumber!=null && invoicests.equals("Pending") && invoicetype.equals("IN"))
			{
				all_invoice_number.add(invoicenumber);
				all_invoioce_id.add(invoioceid);
			}
			i++;
		}
		System.out.println(all_invoice_number);
		System.out.println(all_invoioce_id);
		int count=all_invoice_number.size();
		
		for(int j=count-1;j>=20;j--)
		{
			xuriti_byr.setInvoicecComment("6450a62c0058ff42da9f20b8", all_invoioce_id.get(j), "OK I WILL PAY");
			Thread.sleep(1000);
			xuriti_byr.confirmInvoice(all_invoice_number.get(j), all_invoioce_id.get(j), "NBFC", "Orissa Depo SBD", "OK I WILL PAY");
			System.out.println(j);
		}
		
		for(int j=19;j>=0;j--)
		{
			xuriti_byr.setInvoicecComment("6450a62c0058ff42da9f20b8", all_invoioce_id.get(j), "OK I WILL PAY");
			Thread.sleep(1000);
			xuriti_byr.confirmInvoice(all_invoice_number.get(j), all_invoioce_id.get(j), "NBFC", "Assam  Depo SBD", "OK I WILL PAY");
			System.out.println(j);
		}
	}
}
