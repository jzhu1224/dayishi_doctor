package com.jkdys.doctor.data.model;

public class ShareData {
    /**
     *         "title": "大医师",//标题
     "content": "大医师APP下载", //内容
     "pic": "http://img.zcool.cn/community/01c8ec578da41c0000018c1bb28225.jpg@1280w_1l_2o_100sh.png",//图片
     "url": "http://www.jkdys.com/test?&id=36" //url
     */

    private String title;
    private String content;
    private String pic;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
