package xurititestscript;

import java.io.IOException;

import org.testng.annotations.Test;

import generic.FileLib;
import io.restassured.response.Response;
import utility.XuritiNBFC;

public class TC_0006_CreditLimitApproval {
	
	@Test
	public void TM0001_approveBuyerLimit() throws IOException
	{
		FileLib fl=new FileLib();
		String manager_id=fl.getPropertyData("ManagerID");
		String byrcompanyid=fl.getPropertyData("ANUPAMBUYERID");
		XuritiNBFC xuriti_nbfc=new XuritiNBFC();
		Response response=xuriti_nbfc.approveCreditLimit(manager_id, byrcompanyid, "OK");
	}
}
