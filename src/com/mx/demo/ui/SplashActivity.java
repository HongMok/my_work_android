package com.mx.demo.ui;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.mx.demo.GlobalApp;
import com.mx.demo.R;
import com.mx.demo.model.CustomerModel;
import com.mx.demo.model.RspResultModel;
import com.mx.demo.ui.user.UserLoginActivity;
import com.mx.demo.util.DingLog;
import com.situo.widget.StSplashActivity;

/**
 * 启动程序的加载页
 * 
 * @author zhoukaiban
 * 
 */
public class SplashActivity extends StSplashActivity {
	DingLog log = new DingLog(SplashActivity.class);

	AnimationDrawable anim = null;
	public String mVersion = "";
	RspResultModel mFavStoreRsp;//推荐店铺
	boolean isFirstIn = true;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//GlobalApp.getInstance().get
		CustomerModel user = GlobalApp.getInstance().getLoginInfo();
		if(user==null){
			Intent intent = new Intent(SplashActivity.this,UserLoginActivity.class);
			startActivity(intent);
			finish();
		}
		else{
			Intent intent = new Intent(SplashActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
