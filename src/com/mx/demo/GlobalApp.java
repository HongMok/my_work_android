package com.mx.demo;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DaoConfig;
import net.tsz.afinal.FinalHttp;
import android.content.Context;

import com.mx.demo.api.GlobalHttpApi;
import com.mx.demo.model.CustomerModel;
import com.mx.demo.util.DingLog;
import com.mx.demo.util.GlobalCacheHelper;
import com.situo.widget.StApp;

public class GlobalApp extends StApp {
	DingLog log = new DingLog(GlobalApp.class);
	public static boolean IS_DEMO_MODE=false;
	static GlobalApp self;
	/**
	 * 图片缓存,管理器
	 */
	private FinalBitmap mFinalBitmap;

	/**
	 * 网络连接,管理器
	 */
	FinalHttp mFinalHttp;
	
	/**
	 * 数据存储，管理器
	 */
	FinalDb mFinalDb;
	private CustomerModel customer;
	GlobalHttpApi mGlobalHttpApi;
	@Override
	public void onCreate() {
		super.onCreate();
		StApp.APP_NAME = "dingmore_pda";
		StApp.APP_VERSION = "20121212";
		
		init(R.class);
		self = this;
	}
	
	public FinalBitmap getFinalBitmap() {
		if (mFinalBitmap == null) {
			mFinalBitmap = FinalBitmap.create(this);
			mFinalBitmap.configDiskCachePath("mxdemo");
			mFinalBitmap.configLoadingImage(R.drawable.loading_small);
			mFinalBitmap.configLoadfailImage(R.drawable.loading_fail);
		}
		return mFinalBitmap;
	}

	public FinalHttp getFinalHttp() {
		if (mFinalHttp == null) {
			mFinalHttp = new FinalHttp();
//			mFinalHttp.addHeader("User-Agent", "mobile_android");
//			mFinalHttp.configTimeout(30000);
//			try {
//				KeyStore trustStore;
//				trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//		        trustStore.load(null, null);
//		        SSLSocketFactory ssl_sf = new MySSLSocketFactory(trustStore);
//		        ssl_sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//		        mFinalHttp.configSSLSocketFactory(ssl_sf);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
//		mFinalHttp.addHeader("App-Id",ExtUtil.gtMeta("DM_ID"));
//		mFinalHttp.addHeader("App-Key", ExtUtil.getAppKey(ExtUtil.gtMeta("DM_KEY")+"a"));
//
//		if (GlobalApp.getInstanceNoLoad().customer!= null) {
//			mFinalHttp.addHeader("Customer-Id", GlobalApp.getInstanceNoLoad().customer.customer_id + "");
//			mFinalHttp.addHeader("Auth-Code", ExtUtil.getAuthCode(GlobalApp.getInstanceNoLoad().customer.mobile_session_id));
//			mFinalHttp.addHeader("Auth-Type", "mobile_session");
//		}
//		else{
//			mFinalHttp.addHeader("Customer-Id", "");
//			mFinalHttp.addHeader("Auth-Code", "");
//			mFinalHttp.addHeader("Auth-Type", "");
//		}
		
		return mFinalHttp;
	}

	public FinalDb getFinalDb() {
		if (mFinalDb == null) {
			DaoConfig config = new DaoConfig();
			config.setContext(this);
			config.setDbVersion(1);
			config.setDbName("mxdemo.db");

			mFinalDb = FinalDb.create(config);
		}
		return mFinalDb;
	}
	public static GlobalApp getInstanceNoLoad() {
		return self;
	}
	public static GlobalApp getInstance() {
		return self;
	}


	public GlobalHttpApi getApi() {
		if(mGlobalHttpApi==null)
			mGlobalHttpApi=new GlobalHttpApi();
		return mGlobalHttpApi;
	}
	public CustomerModel getLoginInfo(){
		if(customer==null){
			customer = (CustomerModel)GlobalCacheHelper.getCacheObj(this, GlobalCacheHelper.TYPE_USER, "1");
		}
		return customer;
	}
	
	public void setLoginInfo(CustomerModel info){
		GlobalCacheHelper.setCacheObj(this, GlobalCacheHelper.TYPE_USER, "1", info);
		customer = info;
	}
	/**
	 * 加载Cache
	 * @param activity
	 * @throws Exception
	 */
	public void loadCache(Context activity) throws Exception {

		//loadCity(activity);

	}
}
