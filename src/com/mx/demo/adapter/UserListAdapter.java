package com.mx.demo.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.provider.Settings.Global;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.demo.GlobalApp;
import com.mx.demo.R;
import com.mx.demo.model.CustomerModel;
import com.mx.demo.util.ExtUtil;

public class UserListAdapter extends BaseAdapter{
	private static Map<String,View> viewMap = new HashMap<String,View>();
	private List<CustomerModel> data;  
    private Context context;  
    private int menuNameWidth=150;
    
    public UserListAdapter(Context context,List<CustomerModel> data) {  
        this.context = context;  
        this.data = data;
    }  
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return data.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        // TODO Auto-generated method stub  
        return position;  
    }  
  
    @Override  
    public long getItemId(int position) {  
        // TODO Auto-generated method stub  
        return position;  
    } 
	@Override
	public View getView(int position,  View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final CustomerModel item = data.get(position);
		View rowView = viewMap.get(String.valueOf(item.customer_id));//从缓存中读取
        if (rowView == null) {//如果缓存中不存在，则重新进行构建
                rowView = LayoutInflater.from(context).inflate(R.layout.item_user, null);
                viewMap.put(String.valueOf(item.customer_id), rowView);
        }
        ImageView iv_logo = (ImageView)rowView.findViewById(R.id.iv_logo);
        TextView tv_name = (TextView)rowView.findViewById(R.id.tv_name);
        
        tv_name.setText(item.short_name);
        GlobalApp.getInstance().getFinalBitmap().display(iv_logo, item.img);
        
		return rowView;
	}
}
