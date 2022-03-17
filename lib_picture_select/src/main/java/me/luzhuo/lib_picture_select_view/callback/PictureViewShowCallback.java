package me.luzhuo.lib_picture_select_view.callback;

import java.util.List;

import me.luzhuo.lib_file.bean.CheckableFileBean;

/**
 * 相册显示回调
 */
public interface PictureViewShowCallback {

    /**
     * 图片显示回调
     * @param imageFileBean 点击查看的单个图片文件
     * @param index 在新集合中的索引
     * @param imageFileBeans 所有的图片文件新集合
     */
    public void onImageShowCallback(CheckableFileBean imageFileBean, int index, List<CheckableFileBean> imageFileBeans);

    /**
     * 视频显示回调
     * @param videoFileBean 点击查看的单个视频文件
     * @param index 在新集合中的索引
     * @param videoFileBeans 所有的视频文件新集合
     */
    public void onVideoShowCallback(CheckableFileBean videoFileBean, int index, List<CheckableFileBean> videoFileBeans);

    /**
     * 音频显示回调
     * @param audioFileBean 点击查看的单个音频文件
     * @param index 在新集合中的索引
     * @param audioFileBeans 所有的音频文件新集合
     */
    public void onAudioShowCallback(CheckableFileBean audioFileBean, int index, List<CheckableFileBean> audioFileBeans);
}
