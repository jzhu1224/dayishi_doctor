package com.jkdys.doctor.core.image;

import android.content.Context;
import android.net.Uri;

import java.io.File;

/**
 * Created by yanxin on 17/11/7.
 */

public class LoaderBuilder {

    private Context context;
    private String url;
    private int placeholder;
    private File file;
    private int resId;
    private Uri uri;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(int placeholder) {
        this.placeholder = placeholder;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}