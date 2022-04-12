package me.luzhuo.lib_picture_select.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.base.CoreBaseFragment;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_picture_select.R;
import me.luzhuo.lib_picture_select.engine.GlideImageEngine;
import me.luzhuo.lib_picture_select.engine.ImageEngine;

public class PictureSelectPreviewGifFragment extends CoreBaseFragment implements View.OnClickListener {
    private FileBean data;
    @Nullable
    private PictureSelectPreviewCallback callback;
    private ImageEngine imageEngine = GlideImageEngine.getInstance();

    private PictureSelectPreviewGifFragment() { }

    public static PictureSelectPreviewGifFragment instance(FileBean data) {
        PictureSelectPreviewGifFragment fragment = new PictureSelectPreviewGifFragment();
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
        return layoutInflater.inflate(R.layout.picture_select_item_preview_gif, viewGroup, false);
    }

    @Override
    public void initData(Bundle bundle) {
        ImageView preview_image = getView().findViewById(R.id.picture_select_preview_image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) imageEngine.loadGif(context, ((ImageFileBean) data).uriPath, preview_image);
        else imageEngine.loadGif(context, ((ImageFileBean) data).urlPath, preview_image);

        preview_image.setOnClickListener(this);
    }

    public PictureSelectPreviewGifFragment setOnPictureSelectPreviewCallback(PictureSelectPreviewCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (callback != null) callback.onSingleClick(data);
    }
}
