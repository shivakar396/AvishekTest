package utility;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

import generic.FileCredential;
import generic.FileLib;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import xuriti.JsonResCls;

public class XuritiAdmin 
{
	/**
	 * 
	 * @param company_name
	 * @return
	 * @throws IOException
	 */
	public Response searchCompany(String company_name) throws IOException 
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("AdminToken");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/entity/entities";

		RequestSpecification  httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+token);
		Response response=httpRequest.request(Method.GET);

		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(response.getStatusCode(), 200);
		
		int i=0;
		String msg="";String cid=""; String cmpnygst="";
		while(msg!=null)
		{
			msg=response.body().jsonPath().get("companies["+i+"].company_name");
			//System.out.println("Company Name= "+msg);
			if(msg.equals(company_name))
			{
				cid=response.body().jsonPath().get("companies["+i+"]._id");
				cmpnygst=response.body().jsonPath().get("companies["+i+"].gstin");
				break;
			}
			i++;
		}
		
		String companyname=company_name.replace(" ", "");
		fl.setPropertyData(companyname+"ID", cid);
		fl.setPropertyData(companyname+"GST", cmpnygst);
		
		Reporter.log("SearchCompany",true);
		//System.out.println(response.prettyPrint());
		Assert.assertEquals(msg, company_name);
		
		return response;
	}
	/**
	 * 
	 * @param companyname
	 * @param gstin_number
	 * @param district
	 * @param state
	 * @param pincode
	 * @param industry_type
	 * @param cin
	 * @param tan
	 * @param pan
	 * @param annual_turnover
	 * @param xuriti_score
	 * @param address
	 * @param admin_email
	 * @param admin_mobile
	 * @param creditLimit
	 * @param interest
	 * @param status
	 * @param eNachMandate
	 * @param seller_flag
	 * @param company_email
	 * @param company_mobileNumber
	 * @param admin_name
	 * @param adminid
	 * @param comment
	 * @return
	 * @throws IOException
	 */
	public Response ApproveCompany(String companyname, String gstin_number, String district, String state, String pincode, 
			String industry_type, String cin, String tan, String pan, String annual_turnover, String xuriti_score, 
			String address, String admin_email, String admin_mobile, String creditLimit, String interest, String status,
			boolean eNachMandate, boolean seller_flag, String company_email, String company_mobileNumber, String admin_name,
			String adminid, String comment) throws IOException
	{
		FileLib fl=new FileLib();
		String name=companyname.replace(" ", "");
		String compnyid=fl.getPropertyData(name+"ID");
		System.out.println(compnyid);
		String token=fl.getPropertyData("AdminToken");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");
		RestAssured.baseURI=xuritibaseurl+"/entity/update-entity/"+compnyid;

		RequestSpecification  httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+token);
		Response response = (Response) RestAssured.given()
				.contentType("multipart/form-data")
				.multiPart("company_name", companyname)
				.multiPart("gstin", gstin_number)
				.multiPart("district", district)
				.multiPart("state", state)
				.multiPart("pinCode", pincode)
				.multiPart("industry_type", industry_type)//"PARTNERSHIP"
				.multiPart("cin", cin)//""
				.multiPart("tan", tan)//""
				.multiPart("pan", pan)
				.multiPart("annual_turnover", annual_turnover)
				.multiPart("xuriti_score", xuriti_score)
				.multiPart("address", address)
				.multiPart("admin_email", admin_email)
				.multiPart("admin_mobile", admin_mobile)
				.multiPart("creditLimit", creditLimit)
				.multiPart("interest", interest)
				.multiPart("status", status)
				.multiPart("eNachMandate", eNachMandate)
				.multiPart("seller_flag", seller_flag)
				.multiPart("company_email", company_email)
				.multiPart("company_mobileNumber", company_mobileNumber)
				.multiPart("admin_name", admin_name)
				.multiPart("updated_by", adminid)
				.multiPart("userId", adminid)
				.multiPart("comment", comment)
				.headers("Authorization","Bearer "+token).when().patch("https://uat.xuriti.app/api/entity/update-entity/"+compnyid).thenReturn();

		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(response.getStatusCode(), 200);
		
		return response;
	}
	
	public Response createSellerCreditPlan(String seller_company_id, String credit_plan_name, String admin_id) throws IOException
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("AdminToken");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");
		RestAssured.baseURI=xuritibaseurl+"/entity/"+seller_company_id+"/credit-plans";
		RequestSpecification  httpRequest=RestAssured.given();
		String s="{\r\n"
				+ "    \"plan_name\": \""+credit_plan_name+"\",\r\n"
				+ "    \"default_plan\": true,\r\n"
				+ "    \"credit_period\": \"6\",\r\n"
				+ "    \"payment_interval\": \"3\",\r\n"
				+ "    \"discount_slabs\": [\r\n"
				+ "        {\r\n"
				+ "            \"from\": \"0\",\r\n"
				+ "            \"to\": \"2\",\r\n"
				+ "            \"discount\": \"5\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"from\": 3,\r\n"
				+ "            \"to\": \"4\",\r\n"
				+ "            \"discount\": \"3\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"from\": 5,\r\n"
				+ "            \"to\": \"6\",\r\n"
				+ "            \"discount\": \"1\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"createdBy\": \""+admin_id+"\",\r\n"
				+ "    \"user\": \""+admin_id+"\"\r\n"
				+ "}";
		httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");
		httpRequest.body(s);
		Response response=httpRequest.request(Method.POST);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 201 Created");
		Assert.assertEquals(stscd, 201);
		
		Reporter.log("CreateSellerCreditPlan",true);

		//System.out.println(response.prettyPrint());
		
		return response;
	}
	
	public Response getSellerCreditPlan(String seller_compnay_id) throws IOException
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("AdminToken");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/entity/"+seller_compnay_id+"/credit-plans";
		
		RequestSpecification  httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();
		httpRequest.header("Authorization","Bearer "+token);
		Response response=httpRequest.request(Method.GET);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		
		boolean sts=false;
		try {
			sts=response.body().jsonPath().get("status");
		}
		catch(JsonPathException e) {
			e.printStackTrace();
		}

		Reporter.log("GetCreditPlan",true);
				//Printing Response Body
		//System.out.println(response.prettyPrint());

		Assert.assertEquals(sts, true);
		
		return response;
	}
	
	public Response mapBuyerAndSellerInCreditPlan(String seller_company_id, String seller_credit_plan_id, String buyer_company_id, String admin_id) throws IOException
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("AdminToken");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/entity/"+seller_company_id+"/credit-plans-map";
		RequestSpecification  httpRequest=RestAssured.given();

		String s="{\r\n"
				+ "    \"credit_planid\": \""+seller_credit_plan_id+"\",\r\n"
				+ "    \"seller_id\": \""+seller_company_id+"\",\r\n"
				+ "    \"buyers\": [\r\n"
				+ "        {\r\n"
				+ "            \"buyer_id\": \""+buyer_company_id+"\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"user\": \""+admin_id+"\"\r\n"
				+ "}";
		
		httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");
		httpRequest.body(s);
		Response response=httpRequest.request(Method.POST);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Reporter.log("AddBuyerInCreditPlan",true);

				//Printing Response Body
		//System.out.println(response.prettyPrint());
		
		return response;
	}
	
	public Response unmapped_NBFC(String nbfc_id, String buyer_company_id, String seller_company_id, String admin_id) throws IOException, InterruptedException
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("AdminToken");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");
		
		//https://uat.xuriti.app/api/nbfcs/unmap-nbfc/6409802d72e310559953ebc7
		
		RestAssured.baseURI=xuritibaseurl+"/nbfcs/unmap-nbfc/"+nbfc_id;
		RequestSpecification  httpRequest=RestAssured.given();
		
		String s="{\r\n"
				+ "    \"companies\": [\r\n"
				+ "        {\r\n"
				+ "            \"buyer_id\": \""+buyer_company_id+"\",\r\n"
				+ "            \"seller_id\": \""+seller_company_id+"\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"user\": \""+admin_id+"\"\r\n"
				+ "}";

		httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");
		httpRequest.body(s);
		Response response=httpRequest.request(Method.POST);

		Reporter.log("NBFC_BuyerAndSellerUnMapping",true);
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();

		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
			//Printing Response Body
		//System.out.println(response.prettyPrint());
		String msg="",unmsg="";
		JsonResCls js=new JsonResCls();
		
		msg=js.getStringJsonValue(response, "messges");
		Assert.assertEquals(msg, "Company un-mapped successfully");
		
		return response;
	}
	
	public Response searchNBFC() throws IOException
	{
		
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("AdminToken");
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/nbfcs/get-nbfc";
		RequestSpecification  httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+token);
		Response response=httpRequest.request(Method.GET);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
		Reporter.log("SearchNBFC",true);
				//Printing Response Body
		//System.out.println(response.prettyPrint());
		
		return  response;
	}
	
	public Response mapBuyarAndSellerinNBFC(String nbfc_id,String buyer_company_id, String seller_compnay_id, String payout_fees, String admin_id) throws IOException
	{
		
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("AdminToken");
		FileCredential fc=new FileCredential();
		
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/nbfcs/add-buyer/"+nbfc_id+"/"+admin_id;
		RequestSpecification  httpRequest=RestAssured.given();
		
		String s="{\r\n"
				+ "    \"companies\": [\r\n"
				+ "        {\r\n"
				+ "            \"buyer_id\": \""+buyer_company_id+"\",\r\n"
				+ "            \"seller_id\": \""+seller_compnay_id+"\",\r\n"
				+ "            \"payout_fees\": \""+payout_fees+"\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"user\": \""+admin_id+"\"\r\n"
				+ "}";

		httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");
		httpRequest.body(s);
		Response response=httpRequest.request(Method.POST);

		Reporter.log("NBFC_BuyerAndSellerMapping",true);
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();

		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
			//Printing Response Body
		//System.out.println(response.prettyPrint());
		String msg="",unmsg="";
		JsonResCls js=new JsonResCls();
		
		msg=js.getStringJsonValue(response, "message");
		unmsg=js.getStringJsonValue(response, "message");
		return  response;
	}
	public Response getAdminPaymentHistory() throws IOException
	{
		//https://uat.xuriti.app/api/payment/transctionshistory?invoice_number=LNV000007
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String admintoken=fl.getPropertyData("AdminToken");
		
		RestAssured.baseURI=xuritibaseurl+"/payment/transctionshistory";
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+admintoken);
		Response response=httpRequest.request(Method.GET);
		
		return response;
	}
	public Response getAdminPaymentHistory(String invoice_number) throws IOException
	{
		//https://uat.xuriti.app/api/payment/transctionshistory?invoice_number=LNV000007
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String admintoken=fl.getPropertyData("AdminToken");
		
		String endpnt="?invoice_number="+invoice_number;
		RestAssured.baseURI=xuritibaseurl+"/payment/transctionshistory";
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+admintoken);
		Response response=httpRequest.request(Method.GET,endpnt);
		
		return response;
	}
}
