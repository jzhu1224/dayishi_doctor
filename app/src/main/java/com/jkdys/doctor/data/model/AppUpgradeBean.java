package com.jkdys.doctor.data.model;

public class AppUpgradeBean {
    /**
     * "version": "",//版本号
     "upgrade": false,//是否需要更新
     "forcedupgrade": false,//是否需要强制更新
     "title": "",//更新名称
     "content": "",//更新内容
     "downloadurl": ""//下载地址
     */

    private String version;
    private boolean upgrade;
    private boolean forcedupgrade;
    private String title;
    private String content;
    private String downloadurl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }

    public boolean isForcedupgrade() {
        return forcedupgrade;
    }

    public void setForcedupgrade(boolean forcedupgrade) {
        this.forcedupgrade = forcedupgrade;
    }

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

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }
}
