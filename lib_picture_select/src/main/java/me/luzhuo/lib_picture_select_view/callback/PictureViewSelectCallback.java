package me.luzhuo.lib_picture_select_view.callback;

import java.util.List;

import me.luzhuo.lib_file.bean.CheckableFileBean;

/**
 * 相册选择的回调
 */
public interface PictureViewSelectCallback extends PictureViewShowCallback {
    /**
     * 选择了文件
     * @param fileBeans 选择的文件
     */
    public void onSelect(List<? extends CheckableFileBean> fileBeans);

    /**
     * 删除了文件
     * @param fileBean 删除的文件
     */
    public void onDelete(CheckableFileBean fileBean);
}
