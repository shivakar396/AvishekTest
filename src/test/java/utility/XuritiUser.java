package utility;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

import com.jayway.jsonpath.JsonPathException;

import generic.FileCredential;
import generic.FileLib;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class XuritiUser {
	public Response searchGst(String gst_number) throws IOException
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("UserToken");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/entity/search-gst";
		RequestSpecification  httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();
		requestParams.put("gstin", gst_number);
		httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response=httpRequest.request(Method.POST);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(response.getStatusCode(), 200);
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public Response createCompany(String gstin_number, boolean seller_flag, String associated_seller, String companyName, 
			String address, String district, String state, String pincode, String pan, String cin, String tan, 
			String industry_type, String admin_email, String admin_mobile, boolean consent_gst_defaultFlag) throws IOException
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("UserToken");
		String usrid=fl.getPropertyData("UserID");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/entity/add-entity";
		RequestSpecification  httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();
		
		requestParams.put("userID",usrid);
		requestParams.put("gstin",gstin_number);
		requestParams.put("seller_flag",seller_flag);
		requestParams.put("associated_seller",associated_seller);
		requestParams.put("companyName", companyName);
		requestParams.put("address", address);
		requestParams.put("district", district);
		requestParams.put("state", state);
		requestParams.put("pincode", pincode);
		requestParams.put("pan",pan);
		requestParams.put("cin",cin);
		requestParams.put("tan",tan);
		requestParams.put("industry_type",industry_type);
		requestParams.put("admin_email",admin_email);
		requestParams.put("admin_mobile", admin_mobile);
		requestParams.put ("consent_gst_defaultFlag", consent_gst_defaultFlag);

		httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response=httpRequest.request(Method.POST);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(response.getStatusCode(), 200);
		

		String msg="";
		try {
				msg=response.body().jsonPath().get("message");
		}
		catch(JsonPathException e)
		{
			e.printStackTrace();
		}

		Assert.assertEquals(msg, "Saved Entity");
		
		return response;
	}
}
