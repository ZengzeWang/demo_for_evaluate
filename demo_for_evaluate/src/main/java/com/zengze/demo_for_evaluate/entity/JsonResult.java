package com.zengze.demo_for_evaluate.entity;

import lombok.Data;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

@Data
public class JsonResult<T> {
    private int error_code;
    private String msg;
    private JSONObject data;

    /**
     * 若没有数据返回，默认错误码为0，提示信息为成功
     */
    public JsonResult(){
        this.error_code=0;
        this.msg="Successful!";
    }

    /**
     * 若没有数据返回，可人为指定错误码和提示信息
     * @param code
     * @param msg
     */
    public JsonResult(int code,String msg){
        this.error_code=code;
        this.msg=msg;
        this.data=null;
    }

    /**
     * 具有返回数据的构造函数
     * @param code
     * @param msg
     * @param data
     */
    public JsonResult(int code, String msg, JSONObject data){
        this.error_code=code;
        this.msg=msg;
        this.data=data;
    }
}
