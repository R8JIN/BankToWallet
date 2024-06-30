package com.rest.template.controller;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public Object buildResponse(Object o){
        Map<String, Object> data = new HashMap<>();
        if(o == null){
            data.put("success", false);
            data.put("message", "Transaction not completed");
            return data;
        }
        data.put("data", o);
        data.put("success", true);
        data.put("message", "Transaction completed");
        return data;
    }

    public Object buildResponse(Object o, String message){
        Map<String, Object> data = new HashMap<>();
        if(o == null){
            data.put("success", false);
            data.put("message", message);
            return data;
        }
        data.put("data", o);
        data.put("success", true);
        data.put("message", message);
        return data;
    }
}
