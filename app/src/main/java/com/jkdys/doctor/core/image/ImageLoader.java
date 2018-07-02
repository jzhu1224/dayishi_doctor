package com.jkdys.doctor.core.image;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by yanxin on 17/11/7.
 */

public class ImageLoader {

    private Context mContext;

    private static ImageLoader singleton = null;

    private ILoader loader;

    private static LoaderBuilder loaderBuilder;

    private ImageLoader(Context context) {
        this.mContext = context.getApplicationContext();
        loader = new PicassoLoaderImpl(context);
    }

    public static void init(Context context) {
        with(context);
    }

    public static ImageLoader with(Context context) {
        if(singleton == null) {
            synchronized (ImageLoader.class) {
                if (singleton == null) {
                    singleton = new ImageLoader(context);
                }
            }
        }

        loaderBuilder = new LoaderBuilder();
        loaderBuilder.setContext(singleton.mContext);
        return singleton;
    }

    public ImageLoader load(String url) {
        loaderBuilder.setUrl(url);
        return singleton;
    }

    public ImageLoader load(Uri uri) {
        loaderBuilder.setUri(uri);
        return singleton;
    }

    public ImageLoader load(File file) {
        loaderBuilder.setFile(file);
        return singleton;
    }

    public ImageLoader load(@DrawableRes int resId) {
        loaderBuilder.setResId(resId);
        return singleton;
    }


    public ImageLoader placeholder(@DrawableRes int resId) {
        loaderBuilder.setPlaceholder(resId);
        return singleton;
    }

    public ImageLoader config() {
        return singleton;
    }

    public void into(ImageView imageView) {
        loader.load(loaderBuilder,imageView);
    }

}
