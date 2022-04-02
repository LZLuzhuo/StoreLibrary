package me.luzhuo.lib_picture_select.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_picture_select.fragments.PictureSelectPreviewCallback;
import me.luzhuo.lib_picture_select.fragments.PictureSelectPreviewFragment;

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
        if (data instanceof ImageFileBean) return PictureSelectPreviewFragment.instance(data).setOnPictureSelectPreviewCallback(callback);
        return PictureSelectPreviewFragment.instance(data).setOnPictureSelectPreviewCallback(callback);
    }

    @Override
    public int getCount() {
        return fileBeans == null ? 0 : fileBeans.size();
    }
}
