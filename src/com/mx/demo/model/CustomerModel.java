package com.mx.demo.model;

import java.io.Serializable;

import com.situo.types.StBaseType;

public class CustomerModel implements StBaseType ,Serializable{
    public int customer_id;
    public String mobile;
    public String short_name;
    public String mobile_session_id;
    public String img;
}
