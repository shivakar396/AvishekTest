package xurititestscript;

import java.io.IOException;

import org.testng.annotations.Test;

import generic.FileLib;
import io.restassured.response.Response;
import utility.JsonResCls;
import utility.XuritiAdmin;

public class TC_0004_CreateCreditPlan {
	final String  seller_plan_name="ANUPAM_SELLER_CREDIT_PLAN";
	static String plan_id="";
	static String  sellercmpnyid="";
	static String  byrcmpnyid="";
	static String  admin_id="";
	
	@Test(priority = 1)
	public void TM0001testCreateSellerCreditPlan() throws IOException
	{
		FileLib fl=new FileLib();
		sellercmpnyid=fl.getPropertyData("ANUPAMSELLERID");
		admin_id=fl.getPropertyData("AdminID");
		XuritiAdmin xuriti_admin=new XuritiAdmin();
		Response response=xuriti_admin.createSellerCreditPlan(sellercmpnyid, seller_plan_name, admin_id);
	}
	
	@Test(priority = 2)
	public void TM0002testAddBuyerInSellerCreditPlan() throws IOException
	{
		FileLib fl=new FileLib();
		sellercmpnyid=fl.getPropertyData("ANUPAMSELLERID");
		XuritiAdmin xuriti_admin=new XuritiAdmin();
		Response response=xuriti_admin.getSellerCreditPlan(sellercmpnyid);
		JsonResCls js=new JsonResCls();
		
		int i=0;
		String plnm=""; String plnid="";
		
		while(plnm!=null)
		{
			plnm=js.getStringJsonValue(response, "plan_Details["+i+"].plan_name");
			if(plnm.equals(seller_plan_name))
			{
				plnid=js.getStringJsonValue(response, "plan_Details["+i+"]._id");
				break;
			}
			i++;
			System.out.println(i);
		}
		
		//fl.setPropertyData("CREDITPLANNAME", plnm);
		fl.setPropertyData(seller_plan_name+"ID", plnid);
		
		byrcmpnyid=fl.getPropertyData("ANUPAMBUYERID");
		admin_id=fl.getPropertyData("AdminID");
		response=xuriti_admin.mapBuyerAndSellerInCreditPlan(sellercmpnyid, plnid, byrcmpnyid, admin_id);
	}
}
