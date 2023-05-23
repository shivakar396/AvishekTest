package utility;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;

import generic.FileCredential;
import generic.FileLib;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class XuritiNBFC {

	public Response approveCreditLimit(String manager_id, String buyer_company_id, String comment) throws IOException
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("AdminToken");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/entity/credtilimitapproval";
		RequestSpecification  httpRequest=RestAssured.given();

		String s="{\r\n"
				+ "    \"comment\": \""+comment+"\",\r\n"
				+ "    \"status\": \"Approved\",\r\n"
				+ "    \"userid\": \""+manager_id+"\",\r\n"
				+ "    \"companyid\": \""+buyer_company_id+"\",\r\n"
				+ "    \"user\": \""+manager_id+"\"\r\n"
				+ "}";

		httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");
		httpRequest.body(s);
		Response response=httpRequest.request(Method.PATCH);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Reporter.log("ApproveCreditLimit",true);
				//Printing Response Body
		System.out.println(response.prettyPrint());
		return response;
	}
}
