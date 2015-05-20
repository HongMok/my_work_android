package com.mx.demo.model;

import java.io.Serializable;

import com.situo.types.Group;
import com.situo.types.StBaseType;

public class RspResultModel implements StBaseType ,Serializable{
	private static final long serialVersionUID = 19394L;
	
	public String retcode;
	public String retmsg;
	public CustomerModel user;
	public Group<CustomerModel> customerList;
}
