package com.mx.demo.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mx.demo.model.RspResultModel;
import com.situo.types.StBaseType;

public class JsonParse  {
	
	
	public StBaseType consume(String content) {

		RspResultModel obj=(RspResultModel)static_consume(content);
		 		 
		return obj;
	}
	
	public static StBaseType static_consume(String content) {
		try {
			GsonBuilder builder = new GsonBuilder();
			Gson gson=builder.create();
			RspResultModel obj = gson.fromJson(content, RspResultModel.class);
			return obj;
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
			ex.printStackTrace();
		} finally {
		}
		return null;
	}
	
	
	public static StBaseType static_consume_object(String content,Class c) {
		Gson gson = new Gson();
		try {
			StBaseType obj = gson.fromJson(content, c);
			return obj;
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		} finally {
		}
		return null;
	}
	
	public static String objToJson(Object obj){
		if(obj!=null){
			Gson gs = new Gson();
			return gs.toJson(obj);
		}
		return "";
	}


}
