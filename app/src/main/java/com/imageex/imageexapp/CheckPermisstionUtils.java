package com.imageex.imageexapp;

import android.Manifest;
import android.app.Activity;

import static com.imageex.imageexapp.Constant.CAMERA_PERMISSION_CODE;
import static com.imageex.imageexapp.Constant.LOCATION_PERMISSION_REQ_CODE;
import static com.imageex.imageexapp.Constant.READ_EXTERNAL_STORAGE_PERMISSION_CODE;

public class CheckPermisstionUtils {
    public static void checkLocation(Activity activity,
                                     PermissionUtil.ReqPermissionCallback callback) {
        PermissionUtil.checkPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION,
                LOCATION_PERMISSION_REQ_CODE,
                "We need location permission to locate your position",
                "We can't get your location without location permission",
                callback);
    }

    public static void checkCameraPermisstion(Activity activity,
                                              PermissionUtil.ReqPermissionCallback callback) {
        PermissionUtil.checkPermission(activity,
                Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE,
                "We need camera permission to open your camera",
                "We can't open your camera without camera permission",
                callback);
    }

    public static void checkReadDataPermission(Activity activity,
                                               PermissionUtil.ReqPermissionCallback callback) {
        PermissionUtil.checkPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE_PERMISSION_CODE,
                "We need read external storage permission to get image from category",
                "We can't get image from category without read external storage permission",
                callback);
    }
}
