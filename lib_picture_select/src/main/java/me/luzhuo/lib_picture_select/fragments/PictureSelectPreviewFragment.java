package me.luzhuo.lib_picture_select.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.base.CoreBaseFragment;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_picture_select.R;

public class PictureSelectPreviewFragment extends CoreBaseFragment implements View.OnClickListener {
    private FileBean data;
    @Nullable
    private PictureSelectPreviewCallback callback;

    private PictureSelectPreviewFragment() { }

    public static PictureSelectPreviewFragment instance(FileBean data) {
        PictureSelectPreviewFragment fragment = new PictureSelectPreviewFragment();
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
        preview_photoview.setImageURI(data.uriPath);
        preview_photoview.getAttacher().setOnClickListener(this);
    }

    public PictureSelectPreviewFragment setOnPictureSelectPreviewCallback(PictureSelectPreviewCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (callback != null) callback.onSingleClick(data);
    }
}
