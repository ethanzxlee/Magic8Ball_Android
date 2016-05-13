package com.zhexian.magic8ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by ZheXian on 13/05/2016.
 */
public class ProfilePictureLruCache extends LruCache<String, Bitmap>
        implements ImageLoader.ImageCache {

    private final static int PROFILE_PICTURE_HEIGHT_DP = 48;
    private final static int PROFILE_PICTURE_WIDTH_DP = 48;

    public ProfilePictureLruCache(int maxSize) {
        super(maxSize);
    }

    public ProfilePictureLruCache(Context ctx) {
        this(getCacheSize(ctx));
    }

    public static int getCacheSize(Context context) {
        DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
        int singleImageSize = PROFILE_PICTURE_HEIGHT_DP * ((int) Math.ceil(mDisplayMetrics.density)) * PROFILE_PICTURE_WIDTH_DP * ((int) Math.ceil(mDisplayMetrics.density)) * 4;
        return singleImageSize * 10;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}