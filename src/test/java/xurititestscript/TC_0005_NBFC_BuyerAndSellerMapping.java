package xurititestscript;

import java.io.IOException;

import org.testng.annotations.Test;

import generic.FileLib;
import io.restassured.response.Response;
import utility.JsonResCls;
import utility.XuritiAdmin;

public class TC_0005_NBFC_BuyerAndSellerMapping {
	static final String nbfc_name="Avishek Singh";
	static String nbfc_name_id="";
	
	@Test(priority = 1)
	public void TM0001_getNBFCName() throws IOException 
	{
		FileLib fl=new FileLib();
		XuritiAdmin xuriti_admin=new XuritiAdmin();
		Response response=xuriti_admin.searchNBFC();
		
		JsonResCls js=new JsonResCls();
		int i=0;
		
		String nbfcnm="", nbfcid="";
		
		while(nbfcnm!=null)
		{
			nbfcnm=js.getStringJsonValue(response,  "get_nbfc["+i+"].nbfc_name");
			if(nbfcnm.equals(nbfc_name))
			{
				nbfcid=js.getStringJsonValue(response,  "get_nbfc["+i+"]._id");
				break;
			}
			i++;
		}
		
		System.out.println("NBFC NAME: "+nbfcnm);
		System.out.println("NBFC ID: "+nbfcid);

		fl.setNBFCID(nbfc_name, nbfcid);
	}

	@Test(priority = 2)
	public void TM0002_mapBuyerAndSellerInNBFC() throws IOException 
	{
		FileLib fl=new FileLib();
		String selrcmpnyid=fl.getPropertyData("ANUPAMSELLERID");
		String byrcmpnyid=fl.getPropertyData("ANUPAMBUYERID");
		String admnid=fl.getPropertyData("AdminID");
		String nbfcid=fl.getNBFCID(nbfc_name);
		
		XuritiAdmin xuriti_admin=new XuritiAdmin();
		Response response=xuriti_admin.mapBuyarAndSellerinNBFC(nbfcid, byrcmpnyid, selrcmpnyid, "5", admnid);
		
	}
}
