package cn.stkit.wxl.model.message.request;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file VoiceMessage
 * @desc 语音消息
 * @website http://www.stkit.cn/
 * @date 2020/6/11 5:33 下午
 */

public class VoiceMessage extends BaseMessage
{
    //语音消息媒体id，可以调用获取临时素材接口拉取数据。
    private String MediaId;

    //语音格式，如amr，speex等
    private String Format;

    //语音识别结果，UTF8编码
    private String Recognition;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getRecognition() {
        return Recognition;
    }

    public void setRecognition(String recognition) {
        Recognition = recognition;
    }
}
