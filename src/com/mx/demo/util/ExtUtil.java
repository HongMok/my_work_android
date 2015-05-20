package com.mx.demo.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Layout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mx.demo.GlobalApp;
import com.mx.demo.R;
import com.mx.demo.model.RspResultModel;
import com.situo.location.LocationUtils;
import com.situo.location.LocationUtils.Location;
import com.situo.location.StCellLocationUtil;
import com.situo.location.StLocationInfo;
import com.situo.location.StLocationManager;

public class ExtUtil {
	static DingLog log = new DingLog(ExtUtil.class);
	final public static SimpleDateFormat Y4_M2_D2=new SimpleDateFormat("yyyy-MM-dd");
	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str) || "null".equals(str));
	}
	
	public static boolean isEmpty(List list) {
		return list==null||list.size()==0;
	}

	public static void customeTitle(final Activity context,String title)
	 {
		 customeTitle(context,title,false);
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


	public static String format_money(int money) {
		int a = money / 100;
		int b = money % 100;

		if (b < 10) {
			return a + ".0" + b;
		} else
			return a + "." + b;
	}

	public static String getWeekString(int w) {
		String[] strs = new String[] { "日", "一", "二", "三", "四", "五", "六" };

		return "星期" + strs[w];
	}

	/**
	 * 一条模线
	 * 
	 * @return
	 */
	public static View createLine(Context context) {
		LayoutInflater mInflater = LayoutInflater.from(context);
		View view = mInflater.inflate(R.layout.situo_line_2, null);
		return view;
	}

	public static View createLine2(Context context) {
		LayoutInflater mInflater = LayoutInflater.from(context);
		View view = mInflater.inflate(R.layout.situo_line_2, null);
		return view;
	}
	
	public static View createVertical(Context context) {
		LayoutInflater mInflater = LayoutInflater.from(context);
		View view = mInflater.inflate(R.layout.situo_line_vertical, null);
		return view;
	}

	public static String formatTime_M_D(String str) {
		if (str == null)
			return "";
		String[] strs = str.split("\\.");
		if (strs.length < 2)
			return "";
		str = strs[1] + "月" + strs[2] + "日";
		return str;
	}

	public static String formatTime_Y_M_D(String str) {
		if (str == null)
			return "";
		String[] strs = str.split("\\.");
		if (strs.length < 2)
			return "";
		str = strs[0] + "年" + strs[1] + "月" + strs[2] + "日";
		return str;
	}

	public static String formatTime_M_D_2(String str) {
		if (str == null)
			return "";
		String[] strs = str.split("\\-");
		if (strs.length < 2)
			return "";
		str = strs[1] + "月" + strs[2] + "日";
		return str;
	}

	public static StLocationInfo getLocationInfo(Activity activity) throws Exception {
		StLocationInfo info = StCellLocationUtil.getInfo(activity);
		if (info != null) {
			String formatted_address = info.getStreet() + info.getStreetNumber() + "," + info.getCity();

			info.setAddress(formatted_address);
		} else {
			info = new StLocationInfo();
			try {
				android.location.Location loc = StLocationManager.getInstance().getMyLocation();
				Location mLoc = LocationUtils.createFoursquareLocation(loc);

				if (mLoc != null) {
					/*
					 * info.setLatitude(mLoc.geolat);
					 * info.setLongitude(mLoc.geolong); AddressObj obj =
					 * GlobalApp.getInstance().getApi() .getAddress(mLoc.geolat,
					 * mLoc.geolong); if (obj != null)
					 * info.setAddress(obj.formatted_address);
					 */

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new Exception(activity.getResources().getString(R.string.location_fail));
			}

		}

		if (info == null)
			throw new Exception(activity.getResources().getString(R.string.location_fail));

		return info;

	}

	/**
	 * 检查手机
	 * 
	 * @param tel
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		if (isEmpty(mobile)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^(1[3|4|5|8|9])\\d{9}$");
		Matcher matcher = pattern.matcher(mobile);
		return matcher.matches();
	}

	public static String[] parseQrstr(String qrStr) {
		if (qrStr == null || !qrStr.contains("=")) {
			return null;
		}
		return qrStr.split("=");
	}

	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}
	public final static String getWXMD5MessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getAppKey(String key) {
		Date nowTime = new Date();
		System.out.println(nowTime);
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		String time_str = time.format(nowTime);
		key = time_str + md5(time_str + key);

		return key;
	}

	public static String getAuthCode(String authCode) {

		Date nowTime = new Date();
		System.out.println(nowTime);
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		String time_str = time.format(nowTime);
		authCode = time_str + md5(time_str + authCode);

		return authCode;
	}

	public static String formatQuantity(int quantity) {
		DecimalFormat df = null;
		if ((quantity % 100) == 0)
			df = new DecimalFormat("");
		else if ((quantity % 10) == 0)
			df = new DecimalFormat("0.0");
		else
			df = new DecimalFormat("0.00");

		double q = (quantity * 1.0) / 100;

		String qString = df.format(q);

		return qString;
	}

	public static boolean checkRsp(final Activity c, RspResultModel rsp) {
		if (rsp == null) {
			Toast.makeText(c, c.getResources().getString(R.string.s_rsp_err), Toast.LENGTH_LONG).show();
			return false;
		}
		if ("0".equals(rsp.retcode)) {
			return true;
		} else if("401".equals(rsp.retcode)){
			DialogUtil.showConfirmAlertDialog(c, c.getResources().getString(R.string.s_user_err), c.getResources().getString(R.string.tips), 
			new DialogUtil.OnAlertSureOnclick() {
				
				@Override
				public void onAlertSureOnclick() {
					// TODO Auto-generated method stub
					//Intent intent = new Intent(c,LoginActivity.class);
					//c.startActivityForResult(intent, LoginActivity.REQUEST_CODE_LOGIN_ERR);
					//REQUEST_CODE_LOGIN_ERR
					//c.startActivity(new Intent(c,LoginActivity.class));
				}
			});
			return false;
		}
		else{
			Toast.makeText(c, rsp.retmsg, Toast.LENGTH_LONG).show();
			return false;
		}
	}
	
	public static boolean checkRsp(final Activity c, RspResultModel rsp,boolean showToast) {
		if (rsp == null) {
			if(showToast){
				Toast.makeText(c, c.getResources().getString(R.string.s_rsp_err), Toast.LENGTH_LONG).show();
			}
			return false;
		}
		if ("0".equals(rsp.retcode)) {
			return true;
		} else if("401".equals(rsp.retcode)){
			DialogUtil.showConfirmAlertDialog(c, c.getResources().getString(R.string.s_user_err), c.getResources().getString(R.string.tips), 
			new DialogUtil.OnAlertSureOnclick() {
				
				@Override
				public void onAlertSureOnclick() {
					// TODO Auto-generated method stub
					//Intent intent = new Intent(c,LoginActivity.class);
					//c.startActivityForResult(intent, LoginActivity.REQUEST_CODE_LOGIN_ERR);
					//REQUEST_CODE_LOGIN_ERR
					//c.startActivity(new Intent(c,LoginActivity.class));
				}
			});
			return false;
		}
		else{
			if(showToast){
				Toast.makeText(c, rsp.retmsg, Toast.LENGTH_LONG).show();
			}
			return false;
		}
	}
	

	public static boolean isEmpty(Context c, Object obj,boolean isAlert) {
		if (obj == null) {
			//Toast.makeText(c, "返回异常，请确认网络状态是否正常，是否有效的登录状态", Toast.LENGTH_LONG).show();
			if(isAlert)
			{
				alertMsg(c,c.getResources().getString(R.string.s_datanull));
			}
			return false;
		}else
			return true;

	}
	
	
	public static void alertMsg(Context c,String msg)
	{
		final AlertDialog dlg = new AlertDialog.Builder(c).create();
		 dlg.setView(LayoutInflater.from(c).inflate(R.layout.dialog_common_alert, null));
		 final Window window = dlg.getWindow();
		 dlg.show();
		 window.setContentView(R.layout.dialog_common_alert);
		 Button sure = (Button) window.findViewById(R.id.btn_sure);
		 final TextView tv_msg = (TextView)window.findViewById(R.id.tv_msg);
		 final TextView tv_title = (TextView)window.findViewById(R.id.tv_title);
		 tv_msg.setText(msg);
		 tv_title.setText(c.getResources().getString(R.string.tips));
		 sure.setVisibility(View.VISIBLE);
	     Button cancel = (Button) window.findViewById(R.id.btn_close);
	     cancel.setVisibility(View.GONE);
	     sure.setOnClickListener(new View.OnClickListener() {
				   public void onClick(View v) {
					   dlg.cancel();
				 }
	     });

	}
	
	
	public static String getRegValue(String content,String key){
		
		Pattern p=Pattern.compile("(?:"+key+")([0-9]+)");
		  String u=content;
		  Matcher m=p.matcher(u);
		  if(m.find())
		  {
			 
			  String value=m.group(0);
			  value=value.replace(key, "");
			  return value;
		  }
		  return null;
	}
	
	public static String getTokenValue(String content,String key){
		
		Pattern p=Pattern.compile("(?:"+key+")([0-9,a-z]+)");
		  String u=content;
		  Matcher m=p.matcher(u);
		  if(m.find())
		  {
			 
			  String value=m.group(0);
			  value=value.replace(key, "");
			  return value;
		  }
		  return null;
	}
	

	
	public static String getUrlValue(String content,String key){
		
		String[] params= content.split("&");
		if(params.length>0)
		{
			for(int i=0;i<params.length;i++)
			{
				String[] kv=params[i].split("=");
				if(kv.length>0&&kv[0].contains(key))
				{
					return kv[1];
				}
			}
		}
		  return null;
	}
	
	
	public   static Bitmap  encodeQR(String contents,  int  width,  int  height) { 
	{
		 
	        Hashtable<Object, Object> hints = new  Hashtable<Object, Object>();  
	        // 指定纠错等级   
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  
	        // 指定编码格式   
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8" );  
	        /**
	        try  {  
	            BitMatrix bitMatrix = new  MultiFormatWriter().encode(contents,  
	                    BarcodeFormat.QR_CODE, width, height, hints);  
	            width = bitMatrix.getWidth();
	            height = bitMatrix.getHeight();
	            int[] pixels = new int[width * height];
	            for (int y = 0; y < height; y++)
	            {
	                int offset = y * width;
	                for (int x = 0; x < width; x++)
	                {
	                    pixels[offset + x] = bitMatrix.get(x, y) ? 0xff000000 : 0xffffffff;
	                }
	            }

	            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	            return bitmap;
	  
	        } catch  (Exception e) {  
	            e.printStackTrace();  
	        } 
	        **/ 
	        return null;
	    }  
	}
	public static String formatReqDateTime(String date)
	{
		if(null==date)
			return "";
		date = date.replace("-", "");
		date = date.replace(" ", "");
		date = date.replace(":", "");
		return date;
	}
	/**
	 * 获取时间偏移后时间量
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date getTimeOffset(Date date,long offset){
		if(date==null)
			return null;
		return new Date(date.getTime()+offset);
	}
	
	/**
	 * 转化时间
	 * @param datestr
	 * @param formatter
	 * @return
	 * @throws AppException
	 * @author: Vinci
	 * @time: 2012-5-3 下午03:30:03   
	 * @modifylog:
	 */
	public static Date parseTime(String datestr,String formatter){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(formatter);
			return sdf.parse(datestr);
		}
		catch(Exception ex){
			Log.e("ding", "时间解析错误");
			return null;
		}
	}
	
	
	public static String gtMeta(String key)
	{
		String msg="";
		ApplicationInfo appInfo;
		try {
			appInfo = GlobalApp.getInstance().getPackageManager()
			        .getApplicationInfo(GlobalApp.getInstance().getPackageName(), 
			PackageManager.GET_META_DATA);
			 msg=appInfo.metaData.getString(key);
				return msg; 
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 获取随机数组
	 * @author vinci
	 * @date 2013-7-29 下午08:06:00 
	 * @modifylog   
	 * @param num
	 * @return
	 */
	public static int[] getRandomArray(int num){
		 int[] result = new int[num];
		 for (int i = 0; i < num; i++) {
			 result[i]=i;
		 }
		 for(int i=0;i<num;i++){
			 int random = (int) (num * Math.random());
			 //System.out.println(random);
			 int temp = result[i];
			 result[i] = result[random];
			 result[random] = temp;
		 }
		 return result;
	}
	
	   /**
     * 获取屏幕高度
     * @author vinci
     * @date 2013-9-17 上午10:38:26 
     * @modifylog   
     * @param context
     * @return
     */
	public static int getWindowWidth(Context context){
		DisplayMetrics dm = new android.util.DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
	}
	
	   /**
     * 获取屏幕高度
     * @author vinci
     * @date 2013-9-17 上午10:38:26 
     * @modifylog   
     * @param context
     * @return
     */
	public static int getWindowHeight(Context context){
		DisplayMetrics dm = new android.util.DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
	}
	
	/**
	 * 计算包含字符串个数
	 * @author vinci
	 * @date 2013-9-22 下午06:17:56 
	 * @modifylog   
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int countIndex(String str1, String str2) {   
		int x = str1.length()-str1.replaceAll(str2, "").length();
		if(x>0){
			return x/str2.length();
		}
		else{
			return 0;
		}
    } 
	
	/**
	 * 格式化时间
	 * @param date
	 * @param formatter
	 * @return
	 * @author: Vinci
	 * @time: 2012-5-3 下午03:29:43   
	 * @modifylog:
	 */
	public static String formatTime(Date date,String formatter){
		SimpleDateFormat sdf  = new SimpleDateFormat(formatter);
		return sdf.format(date);
	}
	
	/**
	 * 判断是否网络连接
	 * @author vinci
	 * @date 2013-10-6 下午07:45:31 
	 * @modifylog   
	 * @param context
	 * @return
	 */
	public static boolean checkNetworkConnection(Context context)    
   {    
       final ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);    
   
       final android.net.NetworkInfo wifi =connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);    
       final android.net.NetworkInfo mobile =connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);    
   
       if(wifi.isAvailable()||mobile.isAvailable())    
           return true;    
       else   
           return false;    
   } 
	 //获取手机状态栏高度
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        } 
        return statusBarHeight;
    }
    
    /**
     * 判断textView 是否出现自动省略
     * @author vinci
     * @date 2014-3-14 上午11:40:28 
     * @modifylog   
     * @param context
     * @param tv
     * @return
     */
    public static boolean isTextEllipis(TextView tv){
    	log.info(tv.getText().toString());
    	Layout l = tv.getLayout();
        if ( l != null){
            int lines = l.getLineCount();
            if ( lines > 0)
                if ( l.getEllipsisCount(lines-1) > 0)
                    return true;
        }   
        else{
        	log.info("layout is null");
        }
        return false;
    }
    
    public static boolean isWifiActive(Context icontext){
    	Context context = icontext.getApplicationContext();    
    	ConnectivityManager connectivity = (ConnectivityManager) context    
    	                  .getSystemService(Context.CONNECTIVITY_SERVICE);
	      NetworkInfo[] info;
	      if (connectivity != null) {    
	          info = connectivity.getAllNetworkInfo();    
	          if (info != null) {    
	             for (int i = 0; i < info.length; i++) {    
	                 if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {    
	                     return true;    
	                 }    
	             }    
	         }    
	     }    
	     return false;   
    }
    
    /**
	 * 判断是否是数字
	 * @param str
	 * @return
	 * @author: Vinci
	 * @time: 2012-5-3 下午03:31:45   
	 * @modifylog:
	 */
	public static boolean isNum(String str) {
		boolean flag = false;
		Pattern pat = Pattern.compile("^[0-9]*$");
		Matcher mat = null;
		mat = pat.matcher(str);
		flag = mat.matches();
		return flag;
	}
	
	/**
	 * 判断是否为数字，包括小数
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		if(isEmpty(str))
			return false;
		boolean flag = false;
		Pattern pat = Pattern.compile("^[0-9|.]+$");
		Matcher mat = null;
		mat = pat.matcher(str);
		flag = mat.matches();
		return flag;
	}
	
	public static float getTextViewLength(TextView textView,String text){  
		TextPaint paint = textView.getPaint();  
		// 得到使用该paint写上text的时候,像素为多少  
		float textLength = paint.measureText(text);  
		return textLength;  
	}
	
}
