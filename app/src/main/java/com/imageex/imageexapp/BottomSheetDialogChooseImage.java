package com.imageex.imageexapp;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

public class BottomSheetDialogChooseImage extends BottomSheetDialogFragment implements View.OnClickListener {
    private LinearLayout mLinearDeletePhoto, mLinearUploadPhoto, mLinearTakePhoto;

    public BottomSheetDialogChooseImage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popup_update_image, container, false);
        mLinearDeletePhoto = rootView.findViewById(R.id.llDelete);
        mLinearUploadPhoto = rootView.findViewById(R.id.ll_upload_photo);
        mLinearTakePhoto = rootView.findViewById(R.id.ll_take_photo);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLinearTakePhoto.setOnClickListener(this);
        mLinearUploadPhoto.setOnClickListener(this);
        mLinearDeletePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llDelete: {
                EventBus.getDefault().postSticky(new EventActionImage(EventActionImage.DELETE_IMAGE));
                dismiss();
                break;
            }
            case R.id.ll_upload_photo: {
                EventBus.getDefault().postSticky(new EventActionImage(EventActionImage.UPLOAD_IMAGE));
                dismiss();
                break;
            }
            case R.id.ll_take_photo: {
                EventBus.getDefault().postSticky(new EventActionImage(EventActionImage.TAKE_IMAGE));
                dismiss();
                break;
            }

        }
    }
}
