package com.mx.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mx.demo.R;
import com.mx.demo.adapter.UserListAdapter;
import com.mx.demo.model.CustomerModel;
import com.mx.demo.ui.cusview.ZqWebView;
import com.mx.demo.ui.user.UserDetailActivity;
import com.mx.demo.util.ExtUtil;

public class MainActivity extends Activity {
	private ZqWebView mLJWebView = null;
	private String url = "";
	private long exitTime = 0;
	private ListView lv_list;
	private UserListAdapter adapter;
	
	private List<CustomerModel>mList = new ArrayList<CustomerModel>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ExtUtil.customeTitle(this, "涓婚〉",false);
		initParam();  //初始化
		refreshUI();
	}
	
	private void refreshUI(){
		adapter = new UserListAdapter(this, mList);
		lv_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				CustomerModel user = mList.get(arg2);
				Intent intent = new Intent(MainActivity.this,UserDetailActivity.class);
				intent.putExtra(UserDetailActivity.PARAM_USER, user);//鎶婄敤鎴蜂紶杈撹繃鍘�
				startActivity(intent);
			}
		});
		
		Button btn_title_right = (Button)this.findViewById(R.id.btn_title_right);//title鐨勫彸杈规寜閽�
		btn_title_right.setVisibility(View.VISIBLE);
		btn_title_right.setText("璁剧疆");
		btn_title_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,SetActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
	}
	
	private void initParam(){
		lv_list = (ListView)findViewById(R.id.lv_list);
		for(int i=0;i<20;i++){
			CustomerModel user = new CustomerModel();
			user.customer_id = i+1;
			user.short_name = "鐢ㄦ埛"+i;
			if(0==i%3){
				user.img = "http://d.hiphotos.baidu.com/image/h%3D200/sign=73290bc332adcbef1e3479069cae2e0e/6d81800a19d8bc3e12be34cd818ba61ea8d34506.jpg";
			}
			else if(1==i%3){
				user.img = "http://g.hiphotos.baidu.com/image/h%3D200/sign=672b02c781025aafcc3279cbcbedab8d/562c11dfa9ec8a13d51f13e7f403918fa0ecc0b4.jpg";
			}
			else{
				user.img = "http://c.hiphotos.baidu.com/image/h%3D200/sign=352ae91241a7d933a0a8e3739d4ad194/1c950a7b02087bf4304884eff1d3572c11dfcf14.jpg";
			}
			mList.add(user);
		}
		
	}
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	/**
	 * 杩斿洖閿紝閫�鍑虹▼搴�
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			//dialog();
			 if((System.currentTimeMillis()-exitTime) > 2000){  
		            Toast.makeText(getApplicationContext(), getResources().getString(R.string.touch_again_exit), Toast.LENGTH_SHORT).show();                                
		            exitTime = System.currentTimeMillis();   
		     } else {
		            finish();
		            System.exit(0);
		     }
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * 鑿滃崟鏍�
	 */
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(MainActivity.this,SetActivity.class);
		MainActivity.this.startActivity(intent);
		return true;
	}

}
