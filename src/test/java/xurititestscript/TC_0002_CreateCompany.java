package xurititestscript;

import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import utility.XuritiUser;

public class TC_0002_CreateCompany {
	
	@Test(priority=1)
	public void TM001_searchGst()throws IOException
	{
		XuritiUser xuriti_user=new XuritiUser();
		Response response= xuriti_user.searchGst("07CTFPA9819P1Z3");
		
		System.out.println(response.prettyPrint());
	}

	@Test(priority=2)
	public void TM002_createCompanyAsBuyer()throws IOException
	{
		XuritiUser xuriti_user=new XuritiUser();
		Response response= xuriti_user.createCompany("27AAAFA2958B1ZV", false, "", "ANUPAM BUYER", "AMIRALI MANSION 109,2ND FLANK ROAD,DONGRI,CHINCH BUNDER", "MUMBAI", "MAHARASHTRA,MH", "400009", "AAAFA2958B", "", "", "PARTNERSHIP", "retailer.uat@proton.me", "9643034369", true);
		System.out.println(response.prettyPrint());
		
		Reporter.log("createCompanyAsBuyer",true);
	}
	
	@Test(priority=3)
	public void TM003_createCompanyAsSeller()throws IOException
	{
		XuritiUser xuriti_user=new XuritiUser();
		Response response= xuriti_user.createCompany("08BLHPG4119D1ZG", false, "", "ANUPAM SELLER", "SHASTRI CHOWK,KACHARI ROAD,BALOTRA", "BARMER", "RAJASTHAN,RJ", "344022", "BLHPG4119D", "", "", "PROPRIETORSHIP", "retailer.uat@proton.me", "9643034369", true);
		
		System.out.println(response.prettyPrint());
		
		Reporter.log("createCompanyAsSeller",true);
	}
}
