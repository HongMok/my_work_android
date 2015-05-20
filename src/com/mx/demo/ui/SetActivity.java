package com.mx.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.mx.demo.R;
import com.mx.demo.R.id;
import com.mx.demo.R.layout;
import com.mx.demo.util.DialogUtil;
import com.mx.demo.util.ExtUtil;
import com.mx.demo.util.FuncUtil;
import com.mx.demo.util.SharePreferenceUtil;

public class SetActivity extends Activity {
	private String url = "";
	Button btn_clear;
	Button btn_save;
	EditText et_url;
	SharePreferenceUtil sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		ExtUtil.customeTitle(this, "H5网址设置",true);
		sp = new SharePreferenceUtil(SetActivity.this,"h5web");
		btn_clear = (Button)this.findViewById(R.id.btn_clear);
		btn_save = (Button)this.findViewById(R.id.btn_save);
		et_url = (EditText)this.findViewById(R.id.et_url);
		String url = FuncUtil.isEmpty(sp.getValue(SharePreferenceUtil.SPKEY_URL))?"http://121.8.157.114:17580/ms_app/advertisement.html":sp.getValue(SharePreferenceUtil.SPKEY_URL);
		et_url.setText(url);
		btn_clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				et_url.setText("");
				//sp.setParam(SharePreferenceUtil.SPKEY_URL, "");
			}
		});
		btn_save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ExtUtil.isEmpty(et_url.getText().toString())){
					DialogUtil.showToast(SetActivity.this, "url不能为空");
					return ;
				}
				sp.setParam(SharePreferenceUtil.SPKEY_URL, et_url.getText().toString());
				finish();
			}
		});
		//loadWeb();
	}

}
