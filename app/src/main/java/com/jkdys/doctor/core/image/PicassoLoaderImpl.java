package com.jkdys.doctor.core.image;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by yanxin on 17/11/7.
 */

public class PicassoLoaderImpl implements ILoader {

    static final int DEFAULT_READ_TIMEOUT_MILLIS = 20 * 1000; // 20s
    static final int DEFAULT_WRITE_TIMEOUT_MILLIS = 20 * 1000; // 20s
    static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s

    public PicassoLoaderImpl(Context context) {

    }

    @Override
    public void load(final LoaderBuilder loaderBuilder, final ImageView imageView) {
        if (!TextUtils.isEmpty(loaderBuilder.getUrl())) {
            Picasso.get().load(loaderBuilder.getUrl()).placeholder(loaderBuilder.getPlaceholder()).into(imageView);
        } else if (loaderBuilder.getUri()!= null) {
            Picasso.get().load(loaderBuilder.getUrl()).placeholder(loaderBuilder.getPlaceholder()).into(imageView);
        }
    }

}
