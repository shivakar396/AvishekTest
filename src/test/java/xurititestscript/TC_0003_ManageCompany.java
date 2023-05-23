package xurititestscript;

import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.Test;

import generic.FileLib;
import io.restassured.response.Response;
import utility.XuritiAdmin;

public class TC_0003_ManageCompany {

	@Test(priority = 1)
	public void TM001_ApproveBuyerCompny() throws IOException
	{
		
		FileLib fl=new FileLib();
		String adminid=fl.getPropertyData("AdminID");
		XuritiAdmin xuriti_admin=new XuritiAdmin();
		
		Response response=xuriti_admin.searchCompany("ANUPAM BUYER");
		//System.out.println(response.prettyPrint());
		response= xuriti_admin.ApproveCompany("ANUPAM BUYER", "27AAAFA2958B1ZV", "MUMBAI", "MAHARASHTRA,MH", "400009", "PARTNERSHIP", "", "", "AAAFA2958B", "500000", "0",  "AMIRALI MANSION 109,2ND FLANK ROAD,DONGRI,CHINCH BUNDER", "retailer.uat@proton.me", "9643034369", "1000000", "0", "Approved", false, false, "retailer.uat@proton.me", "9643034369", "Avishek Singh", adminid, "Buyer");
		
		System.out.println(response.prettyPrint());
		Reporter.log("ApproveBuyerCompny",true);
	}
	
	@Test(priority = 2)
	public void TM001_ApproveSellerCompny() throws IOException
	{
		FileLib fl=new FileLib();
		String adminid=fl.getPropertyData("AdminID");
		XuritiAdmin xuriti_admin=new XuritiAdmin();
		Response response=xuriti_admin.searchCompany("ANUPAM SELLER");
		response= xuriti_admin.ApproveCompany("ANUPAM SELLER", "08BLHPG4119D1ZG", "BARMER", "RAJASTHAN,RJ", "344022", "PROPRIETORSHIP", "", "", "BLHPG4119D", "5000000", "0",  "SHASTRI CHOWK,KACHARI ROAD,BALOTRA", "retailer.uat@proton.me", "9643034369", "0", "36", "Approved", false, true, "retailer.uat@proton.me", "9643034369", "Avishek Singh", adminid, "Seller");
		System.out.println(response.prettyPrint());
		Reporter.log("ApproveSellerCompny",true);
	}
}
