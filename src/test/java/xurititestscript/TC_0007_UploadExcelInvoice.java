package xurititestscript;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.FileLib;
import generic.GenerateDate;
import io.restassured.response.Response;
import utility.JsonResCls;
import utility.XuritiBuyer;
import utility.XuritiSeller;

public class TC_0007_UploadExcelInvoice {

	@Test
	public void TM0001_uploadInvoice() throws IOException
	{
		FileLib fl=new FileLib();
		String byrcmpnyid=fl.getPropertyData("ANUPAMBUYERID");
		XuritiBuyer xuriti_buyer=new XuritiBuyer();
		Response response= xuriti_buyer.getBuyer(byrcmpnyid);
		float buyer_avl_limit=xuriti_buyer.getBuyerAvailableCredit(response);
		
		XuritiSeller xuriti_seller=new XuritiSeller();
		GenerateDate dt=new GenerateDate();
		String date=dt.getCurrentDate("yyyy/MM/dd");
		
		int count=fl.getExcelLastRowCount("Invoice.xls", "Sheet1");
		System.out.println(count);
		for(int i=1;i<=count;i++)
		{
			int j=i-1;
			date=dt.getPastDate("yyyy/MM/dd", j);
			fl.setExcelPropertyData("Invoice.xls", "Sheet1", i, 8, date);
		}
		
		response = xuriti_seller.setInvoiceUpload("Invoice.xls");
		System.out.println(response.prettyPrint());
		
		response= xuriti_buyer.getBuyerInvoices(byrcmpnyid);
		float total_outstanding_amount=0.0F;
		int i=0;
		JsonResCls js=new JsonResCls();
		String invoice_number="", invoice_status="";
		while(invoice_number!=null)
		{
			invoice_number=js.getStringJsonValue(response,"invoice["+i+"].invoice_number");
			invoice_status=js.getStringJsonValue(response,"invoice["+i+"].invoice_status");
			if(invoice_number!=null && invoice_status.equals("Pending"));
			{
				float invoice_outstanding_amount=js.getNumJsonValue(response, "invoice["+i+"].outstanding_amount");
				total_outstanding_amount=total_outstanding_amount+invoice_outstanding_amount;
			}
			i++;
		}
		
		response= xuriti_buyer.getBuyer(byrcmpnyid);
		float fnl_buyer_avl_limit=xuriti_buyer.getBuyerAvailableCredit(response);
		
		Assert.assertEquals(fnl_buyer_avl_limit, buyer_avl_limit-total_outstanding_amount);
	}
}
