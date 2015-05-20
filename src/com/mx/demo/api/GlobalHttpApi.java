package com.mx.demo.api;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;
import android.util.Log;

import com.mx.demo.GlobalApp;
import com.mx.demo.model.CustomerModel;
import com.mx.demo.model.RspResultModel;
import com.mx.demo.util.DES;
import com.mx.demo.util.DingLog;
import com.mx.demo.util.ExtUtil;


/**
 * Api接口
 * @author zhoukaiban
 *
 */
public class GlobalHttpApi  {
	private static DingLog log = new DingLog(GlobalHttpApi.class);
	public static final String APP_API_DOMAIN = "http://192.168.1.107:8080/mxserver";
	public static final String URL_API_USER_LOGIN = "/user/finduser";
	
	public GlobalHttpApi() {


	}
	protected static String getApiBaseUrl()
	{
		return APP_API_DOMAIN;
	}
	private String fullUrl(String url){
		return getApiBaseUrl()+url + ".do";
	}
	
	public RspResultModel login(String mobile,String password){
		AjaxParams p = new AjaxParams();
		p.put("mobile", mobile);
		p.put("password", password);
		p.put("trxcode", "201");
		return doRequest(fullUrl(URL_API_USER_LOGIN),p);
		/**
		RspResultModel rsp = new RspResultModel();
		rsp.retcode = "0";
		CustomerModel user = new CustomerModel();
		user.customer_id = 1;
		user.mobile = "15920335594";
		user.short_name = "测试帐号";
		rsp.user = user;
		return rsp;
		**/
	}
	
	public RspResultModel doRequest(String url,AjaxParams p){
		RspResultModel rsp = null;
		try{
			Object obj =GlobalApp.getInstance().getFinalHttp()
					.postSync(url ,p);//发起请求
			
			rsp = (RspResultModel) JsonParse
					.static_consume((String) obj);//结果解析
			return rsp;
		}
		catch(Exception e){
			log.debug("======");
			log.debug( e.getMessage() );
			System.out.println("============");
			e.printStackTrace();
			rsp = new RspResultModel();
			rsp.retcode = "1";
			rsp.retmsg = "网络不给力哦~";
		}
		return rsp;
	}

	
	
	
	
	
	
	

}
