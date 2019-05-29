package com.imageex.imageexapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageHelper {
    public static void logExceptionCrashlytics(Exception e) {
//        if (!BuildConfig.ISDEBUG)
//            Crashlytics.logException(e);
//        else{
//            Log.d("Crash", e.getMessage());
//        }
        Log.d("Crash", e.getMessage());
    }

    public static MultipartBody.Part photo(Uri uri, Context context) {
        File fileBeforeCompress = new File(uri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), fileBeforeCompress);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileBeforeCompress.getName(), requestBody);
        return filePart;
    }
}
