package com.zhexian.magic8ball;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by ZheXian on 13/05/2016.
 */
public class MagicEightBallApplication extends Application {

    private static MagicEightBallApplication mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public synchronized static MagicEightBallApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(getRequestQueue(), new ProfilePictureLruCache(ProfilePictureLruCache.getCacheSize(getApplicationContext())));
        }

        return mImageLoader;
    }
}
