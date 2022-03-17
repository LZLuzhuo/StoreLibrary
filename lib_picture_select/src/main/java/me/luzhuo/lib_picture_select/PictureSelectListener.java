package me.luzhuo.lib_picture_select;

import java.util.List;

import me.luzhuo.lib_file.bean.FileBean;

public interface PictureSelectListener {
    public void onPictureSelect(List<FileBean> selectFiles);
}
