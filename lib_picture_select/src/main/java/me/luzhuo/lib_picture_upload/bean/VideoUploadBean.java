package me.luzhuo.lib_picture_upload.bean;

import java.util.concurrent.atomic.AtomicInteger;

import me.luzhuo.lib_file.bean.VideoFileBean;
import me.luzhuo.lib_picture_compress.bean.VideoCompressBean;

public class VideoUploadBean extends VideoCompressBean implements UploadState {
    /**
     * 上传后文件的网路路径
     */
    public String netUrl;

    /**
     * 当前上传状态
     */
    public AtomicInteger netState = new AtomicInteger(UploadStateStart);

    public VideoUploadBean(VideoFileBean videoFileBean) {
        super(videoFileBean);
    }

    @Override
    public boolean upload() {
        return false;
    }

    @Override
    public String toString() {
        return "VideoUploadBean{" +
                "netUrl='" + netUrl + '\'' +
                ", netState=" + netState +
                ", " + super.toString() +
                '}';
    }
}
