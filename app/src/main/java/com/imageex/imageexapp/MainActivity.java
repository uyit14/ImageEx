package com.imageex.imageexapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ivPicture)
    ImageView ivPicture;

    private static final int REQUEST_CODE_READ = 012;
    private static final int REQUEST_CODE_WRITE = 103;
    String mCurrentPhotoPath;
    private Uri uri;
    private boolean isUpLoadImage;

    private static final int REQUEST_CODE = 1002;
    private static final String[] PERMISSIONS_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyPermission();
        ButterKnife.bind(this);
        EventBus.getDefault().postSticky(new ControlProfile(true));
    }

    private boolean verifyPermission() {
        int camera = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_LIST, REQUEST_CODE
            );

            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventActionImage(EventActionImage eventActionImage) {
        switch (eventActionImage.getFlag()) {
            case EventActionImage.DELETE_IMAGE: {
                //cal api
                break;
            }
            case EventActionImage.TAKE_IMAGE: {
                CheckPermisstionUtils.checkReadDataPermission(this, success -> {
                    if (success) {
                        CheckPermisstionUtils.checkCameraPermisstion(MainActivity.this, success1 -> {
                            if (success1) {
                                dispatchTakePictureIntent();
                                //takenPhoto();
                            } else {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        });

                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ);
                    }
                });

                break;
            }
            case EventActionImage.UPLOAD_IMAGE: {
                CheckPermisstionUtils.checkReadDataPermission(this, success -> {
                    if (success) {
                        openGallery();

                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ);
                    }
                });

                break;
            }
        }
        EventBus.getDefault().removeStickyEvent(EventActionImage.class);
    }

    private void openGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, Constant.REQUEST_WRITE_EXTERNAL_STORAGE_CODE);

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        this.getPackageName() + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                this.startActivityForResult(takePictureIntent, Constant.REQUEST_TAKE_PICTURE_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @OnClick(R.id.btShowOption)
    void updateImage() {
        BottomSheetDialogChooseImage bottomSheetDialogChooseAvatar = new BottomSheetDialogChooseImage();
        bottomSheetDialogChooseAvatar.show(getSupportFragmentManager(),
                bottomSheetDialogChooseAvatar.getTag());

    }

    @OnClick(R.id.btSave)
    void saveToServer(){
        if(isUpLoadImage){
            //api.saveImage(ImageHelper.photo(uri, getContext()))
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_WRITE_EXTERNAL_STORAGE_CODE: {
                    try {
                        ImageLoader.getInstance().loadImageAvatar(this, data.getData(), ivPicture);
                        uri = Uri.parse(FilePath.getPath(this, data.getData()));
                        isUpLoadImage = true;
                    } catch (Exception e) {
                        ImageHelper.logExceptionCrashlytics(e);
                    }
                    break;
                }
                case Constant.REQUEST_TAKE_PICTURE_CODE: {
                    try {

                        Glide.with(this).load(mCurrentPhotoPath).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivPicture);
                        uri = Uri.parse(mCurrentPhotoPath);
                        isUpLoadImage = true;
                    } catch (Exception e) {
                        ImageHelper.logExceptionCrashlytics(e);
                    }

                    break;
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
