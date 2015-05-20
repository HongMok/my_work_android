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

public class UserLoginActivity extends Activity{
	DingLog log = new DingLog(UserDetailActivity.class);
	//Button 
	EditText et_mobile;
	EditText et_password;
	Button btn_submit;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		CommonUI.customeTitle(this, "登录");
		initParam();
		ensureUI();		
	}
	
	private void initParam(){
		et_mobile = (EditText)findViewById(R.id.et_mobile);
		et_password = (EditText)findViewById(R.id.et_password);
		btn_submit = (Button)findViewById(R.id.btn_submit);
	}
	
	private void ensureUI(){
		btn_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doSubit();
			}
		});
	}
	
	private void doSubit(){
		final String mobile = et_mobile.getText().toString();
		final String pwd = et_password.getText().toString();
		if(FuncUtil.isEmpty(mobile)){
			DialogUtil.showToast(UserLoginActivity.this, "请输入帐号");
			return;
		}
		
		if(FuncUtil.isEmpty(pwd)){
			DialogUtil.showToast(UserLoginActivity.this, "请输入密码");
			return;
		}
		
		DialogUtil.showProgressDialog(UserLoginActivity.this);
		SituoHttpAjax.ajax(new SituoAjaxCallBack(){
			@Override
			public StBaseType requestApi() {
				//md5加密码，密码
				return GlobalApp.getInstance().getApi().login(mobile, pwd);
			}

			@Override
			public void callBack(StBaseType baseType) {
				DialogUtil.closeProgress(UserLoginActivity.this);
				RspResultModel result = (RspResultModel)baseType;
				if(CommonUI.checkRsp(UserLoginActivity.this,result)){
//					log.info(result.user.mobile+"-------------------");
//					GlobalApp.getInstance().setLoginInfo(result.user);
//					Intent intent = new Intent(UserLoginActivity.this,MainActivity.class);
//					startActivity(intent);
//					finish();
					DialogUtil.showToast(UserLoginActivity.this, "登陆成功");
				}
			}
			
		});
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
