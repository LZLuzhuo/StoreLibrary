package me.luzhuo.lib_picture_select.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.base.CoreBaseFragment;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_picture_select.R;

public class PictureSelectPreviewVideoFragment extends CoreBaseFragment implements View.OnClickListener {
    private FileBean data;
    @Nullable
    private PictureSelectPreviewCallback callback;

    private PictureSelectPreviewVideoFragment() { }

    public static PictureSelectPreviewVideoFragment instance(FileBean data) {
        PictureSelectPreviewVideoFragment fragment = new PictureSelectPreviewVideoFragment();
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
        return  layoutInflater.inflate(R.layout.picture_select_item_preview_video, viewGroup, false);
    }

    @Override
    public void initData(Bundle bundle) {
        getView().setOnClickListener(this);
        // TODO Video detail
    }

    public PictureSelectPreviewVideoFragment setOnPictureSelectPreviewCallback(PictureSelectPreviewCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (callback != null) callback.onSingleClick(data);
    }
}
