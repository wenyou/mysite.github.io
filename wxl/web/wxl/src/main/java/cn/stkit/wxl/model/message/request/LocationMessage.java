package cn.stkit.wxl.model.message.request;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file LocationMessage
 * @desc 地理位置消息
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:27 下午
 */

public class LocationMessage extends BaseMessage
{
    //地址位置维度
    private String Location_X;

    //地理位置经度
    private String Location_Y;

    //地图缩放大小
    private String Scale;

    //地理位置信息
    private String Label;

    public String getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(String location_X) {
        Location_X = location_X;
    }

    public String getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(String location_Y) {
        Location_Y = location_Y;
    }

    public String getScale() {
        return Scale;
    }

    public void setScale(String scale) {
        Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }
}
