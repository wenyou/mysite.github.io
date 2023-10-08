package cn.stkit.wxl.object;


import cn.stkit.wxl.enums.ResponseStatus;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Collection;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ResponseVO
 * @desc Response value object  controller返回json
 * @website http://www.stkit.cn/
 * @date 2020/6/15 2:49 下午
 */

public class ResponseVO<T>
{
    private Integer status;

    private String message;

    private T data;

    public ResponseVO(Integer status, String message, T data)
    {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseVO(ResponseStatus status, T data)
    {
        this(status.getCode(), status.getMessage(), data);
    }

    public String toJson()
    {
        T t = this.getData();
        if(t instanceof List || t instanceof Collection) {
            return JSONObject.toJSONString(this, SerializerFeature.WriteNullListAsEmpty);
        } else {
            return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue);
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}