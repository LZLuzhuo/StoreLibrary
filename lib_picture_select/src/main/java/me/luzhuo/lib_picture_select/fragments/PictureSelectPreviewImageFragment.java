package me.luzhuo.lib_picture_select.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.base.CoreBaseFragment;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_picture_select.R;

public class PictureSelectPreviewImageFragment extends CoreBaseFragment implements View.OnClickListener {
    private FileBean data;
    @Nullable
    private PictureSelectPreviewCallback callback;

    private PictureSelectPreviewImageFragment() { }

    public static PictureSelectPreviewImageFragment instance(FileBean data) {
        PictureSelectPreviewImageFragment fragment = new PictureSelectPreviewImageFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate() {
        if (getArguments() != null) {
            data = getArguments().getParcelable("data");
        }
    }

    @Override
    public View initView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.picture_select_item_preview_image, viewGroup, false);
    }

    @Override
    public void initData(Bundle bundle) {
        PhotoView preview_photoview = getView().findViewById(R.id.picture_select_preview_photoview);
        preview_photoview.setScaleType(ImageView.ScaleType.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) preview_photoview.setImageURI(data.uriPath);
        else preview_photoview.setImageURI(Uri.fromFile(new File(data.urlPath)));
        preview_photoview.getAttacher().setOnClickListener(this);
    }

    public PictureSelectPreviewImageFragment setOnPictureSelectPreviewCallback(PictureSelectPreviewCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (callback != null) callback.onSingleClick(data);
    }
}
