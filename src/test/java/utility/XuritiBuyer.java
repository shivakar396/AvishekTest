package utility;

import java.io.IOException;
import java.util.ArrayList;

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

public class XuritiBuyer {
	
	public Response getBuyer(String buyercompanyid) throws IOException
	{
		//https://uat.xuriti.app/api/entity/entity/64536eb2c65a2264547ccab3
		FileLib fl=new FileLib();
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/entity/entity/"+buyercompanyid;
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET);
		
		return response;
	}
	public float getBuyerAvailableCredit(Response response)
	{
		float limit=0.0F;
		try {
			limit=response.body().jsonPath().get("company.avail_credit");
		}
		catch(JsonPathException e) {
			
		}
		catch(ClassCastException e) {
				int x=response.body().jsonPath().get("company.avail_credit");
				limit=x;
		}
		
		return limit;
	}
	
	public float getBuyerCIH(Response response)
	{
		float cih=0.0F;
		try {
			cih=response.body().jsonPath().get("company.cih.amount");
		}
		catch(JsonPathException e) {
			
		}
		catch(ClassCastException e) {
				int x=response.body().jsonPath().get("company.cih.amount");
				cih=x;
		}
		
		return cih;
	}
	/**
	 * 
	 * @param buyer_company_id
	 * @return Response Object
	 * @throws IOException
	 */
	public Response getBuyerInvoices(String buyer_company_id) throws IOException
	{
		//https://uat.xuriti.app/api/invoice/search-invoice/64536eb2c65a2264547ccab3/buyer
		
		FileLib fl=new FileLib();
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/invoice/search-invoice/"+buyer_company_id+"/buyer";
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET);
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public Response setInvoicecComment(String invcid, String comment) throws IOException
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("UserToken");
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrid=fl.getPropertyData("UserID");

		RestAssured.baseURI=xuritibaseurl+"/invoice/add-comments";
		RequestSpecification  httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();
		requestParams.put("comment",comment);
		requestParams.put("userId",usrid);
		requestParams.put("id",invcid);
		httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response=httpRequest.request(Method.POST);
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public Response confirmInvoice(String invoice_number, String invoice_id, String nbfc_name, String Seller_name,String user_comnt ) throws IOException
	{
		FileLib fl=new FileLib();
		String token=fl.getPropertyData("UserToken");
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrid=fl.getPropertyData("UserID");

		RestAssured.baseURI=xuritibaseurl+"/invoice/status";
		RequestSpecification  httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();
		requestParams.put("invoiceID",invoice_id);
		requestParams.put("status","Confirmed");
		requestParams.put("userConsentGiven",true);
		requestParams.put("user_comment",user_comnt);
		requestParams.put("user_consent_message","I agree and approve Xuriti and it's financing partner "+nbfc_name+"  is authorised to disburse funds to the\\nSeller- "+Seller_name+" for invoice- "+invoice_number+" on my behalf.");
		httpRequest.header("Authorization","Bearer "+token).header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response=httpRequest.request(Method.PATCH);
		
		return response;

	}
	
	public Response payNow(String byrcmpnyid) throws IOException
	{
		//https://uat.xuriti.app/api/payment/get_seller?buyer=641af6761eb9818b4b6787e4
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		String endpnt="?buyer="+byrcmpnyid;
		RestAssured.baseURI=xuritibaseurl+"/payment/get_seller";
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET,endpnt);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
		return response;
	}
	
	public Response selectSeller(String buyer_company_id, String seller_company_id) throws IOException
	{
		//https://uat.xuriti.app/api/payment/summary?buyer=64648b52a29032140749cd9a&seller=64648b52a29032140749cdb1
		
		FileLib fl=new FileLib();
		
		String endpnt="?buyer="+buyer_company_id+"&seller="+seller_company_id;
		
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/payment/summary";
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		Response response= httpRequest.request(Method.GET,endpnt);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
		return response; 
	}
	public Response partPay(float amount, String byrcmpnyid, String slrcmpnyid) throws IOException
	{
		//https://uat.xuriti.app/api/payment/summary?buyer=641af6761eb9818b4b6787e4&seller=641af67b1eb9818b4b6787fb&pay_amount=1000
		
		FileLib fl=new FileLib();
		
		String endpnt="?buyer="+byrcmpnyid+"&seller="+slrcmpnyid+"&pay_amount="+amount;
		
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/payment/summary";
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		Response response= httpRequest.request(Method.GET,endpnt);
		
		return response; 
	}
	public Response vouchPartPayment(String byrcmpnyid, String byrcmpnyeml, String slrcmpnymbl, float amount, float settled_amount, float total_outstanding, String slrcmpnyid) throws IOException, InterruptedException
	{
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/payment/vouch_payment";
		RequestSpecification httpRequest=RestAssured.given();
		
		String s="{\r\n"
				+ "    \"customer_details\": {\r\n"
				+ "        \"customer_id\": \""+byrcmpnyid+"\",\r\n"
				+ "        \"customer_email\": \""+byrcmpnyeml+"\",\r\n"
				+ "        \"customer_phone\": \""+slrcmpnymbl+"\"\r\n"
				+ "    },\r\n"
				+ "    \"order_amount\": "+amount+",\r\n"
				+ "    \"settle_amount\": "+settled_amount+",\r\n"
				+ "    \"order_currency\": \"INR\",\r\n"
				+ "    \"outstanding_amount\": "+total_outstanding+",\r\n"
				+ "    \"buyerid\": \""+byrcmpnyid+"\",\r\n"
				+ "    \"sellerid\": \""+slrcmpnyid+"\"\r\n"
				+ "}";
		
		httpRequest.header("Authorization","Bearer "+usrtoken).header("Content-Type","application/json");
		httpRequest.body(s);
		Response response=httpRequest.request(Method.POST);
		
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
		Thread.sleep(30000);
		Reporter.log(response.prettyPrint(),true);
		System.out.println(response.prettyPrint());
		
		String paymentlink="";
		try {
				paymentlink=response.body().jsonPath().get("payment_link");
		}
		catch(JsonPathException e) {
			e.printStackTrace();
		}
		String data="";
		try {
				data=paymentlink.substring(37);
		}
		catch(NullPointerException e){
			Reporter.log("Payment link has not created",true);
		}
		
		RestAssured.baseURI="https://escrow.iamvouched.com/v1/escrow/collect_link_upi_collect";
		httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		response=httpRequest.request(Method.OPTIONS);
		
		stscd=response.getStatusCode();
		stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 204 No Content");
		Assert.assertEquals(stscd, 204);
				
		//System.out.println(response.prettyPrint());
		
		RestAssured.baseURI="https://escrow.iamvouched.com/v1/escrow/collect_link_upi_collect";
		httpRequest=RestAssured.given();
		String pay="{\r\n"
				+ "    \"data\": \""+data+"\",\r\n"
				+ "    \"vpa\": \"xyz@abc\"\r\n"
				+ "}";
		httpRequest.header("Authorization","Bearer "+usrtoken).header("Content-Type","application/json");
		httpRequest.body(pay);
		response=httpRequest.request(Method.POST);
		
		stscd=response.getStatusCode();
		stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		Thread.sleep(150000);			
		return response;
	}
	
	
	public Response vouchFulltPayment(String byrcmpnyid, String byrcmpnyeml, String slrcmpnymbl, float total_payable, float total_invoice_amount, String slrcmpnyid) throws IOException, InterruptedException
	{
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		RestAssured.baseURI=xuritibaseurl+"/payment/vouch_payment";
		RequestSpecification httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();
		
		String s="{\r\n"
				+ "    \"customer_details\": {\r\n"
				+ "        \"customer_id\": \""+byrcmpnyid+"\",\r\n"
				+ "        \"customer_email\": \""+byrcmpnyeml+"\",\r\n"
				+ "        \"customer_phone\": \""+slrcmpnymbl+"\"\r\n"
				+ "    },\r\n"
				+ "    \"order_amount\": "+total_payable+",\r\n"
				+ "    \"settle_amount\": "+total_invoice_amount+",\r\n"
				+ "    \"order_currency\": \"INR\",\r\n"
				+ "    \"outstanding_amount\": "+total_invoice_amount+",\r\n"
				+ "    \"buyerid\": \""+byrcmpnyid+"\",\r\n"
				+ "    \"sellerid\": \""+slrcmpnyid+"\"\r\n"
				+ "}";
		httpRequest.header("Authorization","Bearer "+usrtoken).header("Content-Type","application/json");
		httpRequest.body(s);
		Response response=httpRequest.request(Method.POST);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
		System.out.println(response.prettyPrint());
		
		String paymentlink="";
		try {
				paymentlink=response.body().jsonPath().get("payment_link");
		}
		catch(JsonPathException e) {
			e.printStackTrace();
		}
		String data="";
		try {
				data=paymentlink.substring(37);
		}
		catch(NullPointerException e){
			e.printStackTrace();
		}
		
		RestAssured.baseURI="https://escrow.iamvouched.com/v1/escrow/collect_link_upi_collect";
		httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		response=httpRequest.request(Method.OPTIONS);
		
		stscd=response.getStatusCode();
		stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 204 No Content");
		Assert.assertEquals(stscd, 204);
				
		//System.out.println(response.prettyPrint());
		
		RestAssured.baseURI="https://escrow.iamvouched.com/v1/escrow/collect_link_upi_collect";
		httpRequest=RestAssured.given();
		String pay="{\r\n"
				+ "    \"data\": \""+data+"\",\r\n"
				+ "    \"vpa\": \"xyz@abc\"\r\n"
				+ "}";
		httpRequest.header("Authorization","Bearer "+usrtoken).header("Content-Type","application/json");
		httpRequest.body(pay);
		response=httpRequest.request(Method.POST);
		
		stscd=response.getStatusCode();
		stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		Thread.sleep(150000);			
		return response;
	}
	
	public Response paymentSummary(String byrcmpnyid, String slrcmpnyid) throws IOException
	{
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		String endpoint="?buyer="+byrcmpnyid+"&seller="+slrcmpnyid;
		RestAssured.baseURI=xuritibaseurl+"/payment/summary";

		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);

		Response response=httpRequest.request(Method.GET,endpoint);

		//System.out.println(response.prettyPrint());
	
			return response;
	}
	
	
	public Response getBuyerPaymentHistory(String buyer_company_id) throws IOException
	{
		//https://uat.xuriti.app/api/payment/transctionshistory?buyer=64536eb2c65a2264547ccab3
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/payment/transctionshistory";
		String endpnt="?buyer="+buyer_company_id;
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET,endpnt);
		
		return response;
	}
	
	public Response getBuyerPaymentHistory(String buyer_company_id, String invoice_number) throws IOException
	{
		//https://uat.xuriti.app/api/payment/transctionshistory?buyer=64648b52a29032140749cd9a&invoice_number=LNV000015
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/payment/transctionshistory";
		String endpnt="?buyer="+buyer_company_id+"&invoice_number="+invoice_number;
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET,endpnt);
		
		return response;
	}
	
	public Response getBuyerTransactionLedger(String buyer_company_id) throws IOException
	{
		//https://uat.xuriti.app/api/ledger/statement/buyer/6443794becbdd6367ac838dc?user_id=6406c6e5e325b6a87c111e45
		//https://uat.xuriti.app/api/ledger/statement/buyer/64536eb2c65a2264547ccab3
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		String usrid=fl.getPropertyData("UserID");
		
		RestAssured.baseURI=xuritibaseurl+"/ledger/statement/buyer/"+buyer_company_id;
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization", "Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET);
		
		return response;
	}
	
	public Response getBuyerTransactionalStatement(String buyer_company_id) throws IOException
	{
		//https://uat.xuriti.app/api/ledger/companies/transaction_ledger?buyer=64536eb2c65a2264547ccab3
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/ledger/companies/transaction_ledger";
		String endpnt="?buyer="+buyer_company_id;
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization", "Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET,endpnt);
		
		return response;
	}
	
	@SuppressWarnings("unused")
	public ArrayList<Float> fullPayNowDetails(Response response)
	{
		JsonResCls js=new JsonResCls();
		
		ArrayList<Float> f=new ArrayList<Float>();
		
		float total_invoice_amount=js.getNumJsonValue(response, "total_outstanding");
		f.add(total_invoice_amount);
		
		float total_gst_amount=js.getNumJsonValue(response, "total_gst");
		f.add(total_gst_amount);
		
		float total_discount=js.getNumJsonValue(response, "total_discount");
		f.add(total_discount);
		
		float total_interest=js.getNumJsonValue(response, "total_interest");
		f.add(total_interest);
		
		float total_payable=js.getNumJsonValue(response, "total_payable");
		f.add(total_payable);
		
		return f;
	}
	
	public float getPartPayTotalInterest(Response response)
	{
		JsonResCls js=new JsonResCls();
		float total_interest=js.getNumJsonValue(response, "total_interest");
		
		return total_interest;
	}
	
	public float getPartPayTotalOutstanding(Response response)
	{
		JsonResCls js=new JsonResCls();
		float total_outstanding=js.getNumJsonValue(response, "total_outstanding");
		
		return total_outstanding;
	}
	public float getPartPayTotalDiscouont(Response response)
	{
		JsonResCls js=new JsonResCls();
		float total_discount=js.getNumJsonValue(response, "total_discount");
		
		return total_discount;
	}
	
	public float getPartPayTotalGst(Response response)
	{
		JsonResCls js=new JsonResCls();
		float total_gst=js.getNumJsonValue(response, "total_gst");
		
		return total_gst;
	}
	public float getPartPayTotalPayable(Response response)
	{
		JsonResCls js=new JsonResCls();
		float total_payable=js.getNumJsonValue(response, "total_payable");
		
		return total_payable;
	}
	public float getPartPaySettledAamount(Response response)
	{
		JsonResCls js=new JsonResCls();
		float settled_amount=js.getNumJsonValue(response, "settled_amount");
		
		return settled_amount;
	}
	public String getTransactionIdBySearchInvoiceNumberInPaymentHistory(Response response)
	{
		JsonResCls js=new JsonResCls();
		String order_id=js.getStringJsonValue(response, "trunc_details[0].order_id");
		
		return order_id;
	}
	public String getInvoiceNumberBySearchInvoiceNumberInPaymentHistory(Response response)
	{
		JsonResCls js=new JsonResCls();
		String invoice_number=js.getStringJsonValue(response, "trunc_details[0].invoice_number");
		
		return invoice_number;
	}
	public String getSellerNameBySearchInvoiceNumberInPaymentHistory(Response response)
	{
		JsonResCls js=new JsonResCls();
		String seller_name=js.getStringJsonValue(response, "trunc_details[0].seller_name");
		
		return seller_name;
	}
	public String getPaymentDateBySearchInvoiceNumberInPaymentHistory(Response response)
	{
		JsonResCls js=new JsonResCls();
		String PaymentDate=js.getStringJsonValue(response, "trunc_details[0].payment_date");
		
		return PaymentDate;
	}
	public String getPaymentModeBySearchInvoiceNumberInPaymentHistory(Response response)
	{
		JsonResCls js=new JsonResCls();
		String payment_mode=js.getStringJsonValue(response, "trunc_details[0].payment_mode");
		
		return payment_mode;
	}
	public String getPaidAmountBySearchInvoiceNumberInPaymentHistory(Response response)
	{
		JsonResCls js=new JsonResCls();
		String paid_amount=js.getStringJsonValue(response, "trunc_details[0].order_amount");
		
		return paid_amount;
	}
	public String getPaymentStatusBySearchInvoiceNumberInPaymentHistory(Response response)
	{
		JsonResCls js=new JsonResCls();
		String payment_status=js.getStringJsonValue(response, "trunc_details[0].order_status");
		
		return payment_status;
	}
}
