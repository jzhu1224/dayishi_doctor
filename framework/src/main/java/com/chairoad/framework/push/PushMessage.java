package com.chairoad.framework.push;

import java.io.Serializable;

/**
 * Created by duo.chen on 2015/4/28.
 */
public class PushMessage implements Serializable {
    /**
     * "title" : "消息标题"
     * "message" : "消息内容",
     * "msgType" : "消息类型",
     * "optional" : "附加内容"
     * "uri" : "callback uri"
     */
    String title = "";
    String message = "";
    String msgType = "";
    String optional = "";
    String uri = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PushMessage that = (PushMessage) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (msgType != null ? !msgType.equals(that.msgType) : that.msgType != null) return false;
        if (optional != null ? !optional.equals(that.optional) : that.optional != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (msgType != null ? msgType.hashCode() : 0);
        result = 31 * result + (optional != null ? optional.hashCode() : 0);
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        return "PushMessage{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", msgType='" + msgType + '\'' +
                ", optional='" + optional + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
