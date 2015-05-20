package com.mx.demo.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.demo.GlobalApp;
import com.mx.demo.R;
import com.mx.demo.model.CustomerModel;
import com.mx.demo.model.RspResultModel;
import com.mx.demo.ui.MainActivity;
import com.mx.demo.util.CommonUI;
import com.mx.demo.util.DialogUtil;
import com.mx.demo.util.DingLog;
import com.mx.demo.util.FuncUtil;
import com.mx.demo.util.SituoHttpAjax;
import com.mx.demo.util.SituoHttpAjax.SituoAjaxCallBack;
import com.situo.types.StBaseType;

public class UserDetailActivity extends Activity{
	DingLog log = new DingLog(UserLoginActivity.class);
	public final static String PARAM_USER = "PARAM_USER";
	
	ImageView iv_logo;
	TextView tv_name;
	
	CustomerModel mUser;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_userdetail);
		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PARAM_USER)) {
			mUser = (CustomerModel) getIntent().getExtras().getSerializable(PARAM_USER);
		}
		CommonUI.customeTitle(this, "用户详情");
		initParam();
		ensureUI();		
	}
	
	private void initParam(){
		iv_logo = (ImageView)findViewById(R.id.iv_logo);
		tv_name = (TextView)findViewById(R.id.tv_name);
	}
	
	private void ensureUI(){
		GlobalApp.getInstance().getFinalBitmap().display(iv_logo, mUser.img);
		tv_name.setText(mUser.short_name);
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}
