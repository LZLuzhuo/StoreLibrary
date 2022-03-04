package me.luzhuo.lib_picture_select;

import java.util.List;

import me.luzhuo.lib_file.bean.FileBean;

public interface PictureSelectListener {

    /**
     * 拍照
     * @param isLimit 是否已达上限
     */
    public void onCamera(boolean isLimit);

    /**
     * 选择了文件
     */
    public void onSelect(boolean isSingle, FileBean file);

    /**
     * 预览图片
     * @param file 单个文件
     * @param index 该文件所在的索引
     * @param files 所有文件
     */
    public void onShow(FileBean file, int index, List<FileBean> files);
}
