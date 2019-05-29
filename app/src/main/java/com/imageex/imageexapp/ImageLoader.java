package com.imageex.imageexapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageLoader {
    private static final String TAG = "ImageLoader";
    private static ImageLoader instance;

    private ImageLoader() {

    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public void loadImageAvatar(final Activity activity, final String url, final ImageView imageView) {

        try {
            Glide.with(activity)
                    .load(url)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.5f)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            ImageHelper.logExceptionCrashlytics(e);
        }
    }

    public void loadImageInHome(final Activity activity, final String url, final ImageView imageView) {

        try {
            Glide.with(activity)
                    .load(url)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.ic_launcher_background)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            ImageHelper.logExceptionCrashlytics(e);
        }
    }

    public void loadImageAvatar(final Context context, final String url, final ImageView imageView) {
        try {
            Glide.with(context)
                    .load(url)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.ic_launcher_background)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            ImageHelper.logExceptionCrashlytics(e);
        }
    }

    public void loadImageAvatar(final Activity activity, final Uri uri, final ImageView imageView) {

        try {
            Glide.with(activity)
                    .load(uri)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.ic_launcher_background)
                    .dontAnimate()
                    .into(imageView);
        } catch (Exception e) {
            ImageHelper.logExceptionCrashlytics(e);
        }
    }


}
