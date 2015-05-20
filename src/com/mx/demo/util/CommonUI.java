package com.mx.demo.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mx.demo.GlobalApp;
import com.mx.demo.R;
import com.mx.demo.model.RspResultModel;

/**
 * 公共的UI项
 * @author zhoukaiban
 *
 */
public class CommonUI {
	static DingLog log = new DingLog(CommonUI.class);
	public static final int REQ_CODE_FINISH = 88;
	public static Bitmap mLoadingBm = null;
	public static Bitmap mLoadFailBm = null;
	public static void customeTitle(final Activity context,String title)
	 {
		 customeTitle(context,title,true);
	 }
	 
	 public static void customeTitle(final Activity context,String title,boolean isLeft)
	 {
	    	context.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
	    			R.layout.common_title);
			
			TextView tvTitle=(TextView)context.findViewById(R.id.tv_title);
			tvTitle.setText(title);
			if(isLeft){
				context.findViewById(R.id.btn_title_left).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						//关闭上一个activity
						context.finish();
					}
				});
			}
			else{
				context.findViewById(R.id.btn_title_left).setVisibility(View.INVISIBLE);
			}
			
	 }

	/**
	 * 获得版本信息
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context){
		int versionCode = 0;
		try
		{
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo("com.dingmore.mobile", 0).versionCode;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return versionCode;
	}
	
	public static boolean checkRsp(RspResultModel rsp){
		return checkRsp(GlobalApp.getInstance(),rsp,true);
	}
	
	public  static boolean checkRsp(Context context,RspResultModel rsp){
		return checkRsp(context,rsp,true);
	}
	
	public  static boolean checkRsp(Context context,RspResultModel rsp,boolean showTips){
		String msg = "";
		if(rsp==null){
			msg = "网络不给力哦~";
		}
		else {
			if(!"0".equals(rsp.retcode)){
				msg = FuncUtil.isEmpty(rsp.retmsg)?"网络不给力哦~":rsp.retmsg;
			}
		}
		if(FuncUtil.isEmpty(msg)){
			return true;
		}
		else{
			if(showTips)
				DialogUtil.showToast(context, msg);
			return false;
		}
	}
}
