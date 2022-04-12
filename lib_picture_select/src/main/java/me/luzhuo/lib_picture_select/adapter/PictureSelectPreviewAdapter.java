package me.luzhuo.lib_picture_select.adapter;

import android.util.Log;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_file.bean.VideoFileBean;
import me.luzhuo.lib_picture_select.fragments.PictureSelectPreviewAudioFragment;
import me.luzhuo.lib_picture_select.fragments.PictureSelectPreviewCallback;
import me.luzhuo.lib_picture_select.fragments.PictureSelectPreviewFileFragment;
import me.luzhuo.lib_picture_select.fragments.PictureSelectPreviewGifFragment;
import me.luzhuo.lib_picture_select.fragments.PictureSelectPreviewImageFragment;
import me.luzhuo.lib_picture_select.fragments.PictureSelectPreviewVideoFragment;

public class PictureSelectPreviewAdapter extends FragmentPagerAdapter {
    private List<FileBean> fileBeans;
    private PictureSelectPreviewCallback callback;

    public PictureSelectPreviewAdapter(FragmentActivity activity, List<FileBean> fileBeans) {
        super(activity.getSupportFragmentManager());
        this.fileBeans = fileBeans;
    }

    public void setOnPictureSelectPreviewCallback(PictureSelectPreviewCallback callback) {
        this.callback = callback;
    }

    @Override
    public Fragment getItem(int position) {
        FileBean data = fileBeans.get(position);
        if (data instanceof ImageFileBean && !data.mimeType.equalsIgnoreCase("image/gif")) return PictureSelectPreviewImageFragment.instance(data).setOnPictureSelectPreviewCallback(callback);
        else if (data instanceof ImageFileBean && data.mimeType.equalsIgnoreCase("image/gif")) return PictureSelectPreviewGifFragment.instance(data).setOnPictureSelectPreviewCallback(callback);
        else if (data instanceof VideoFileBean) return PictureSelectPreviewVideoFragment.instance(data).setOnPictureSelectPreviewCallback(callback);
        else if (data instanceof AudioFileBean) return PictureSelectPreviewAudioFragment.instance(data).setOnPictureSelectPreviewCallback(callback);
        else return PictureSelectPreviewFileFragment.instance(data).setOnPictureSelectPreviewCallback(callback);
    }

    @Override
    public int getCount() {
        return fileBeans == null ? 0 : fileBeans.size();
    }
}
