package me.luzhuo.lib_picture_upload.bean;

import java.util.concurrent.atomic.AtomicInteger;

import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_picture_compress.bean.AudioCompressBean;

public class AudioUploadBean extends AudioCompressBean implements UploadState {

    /**
     * 上传后文件的网路路径
     */
    public String netUrl;

    /**
     * 当前上传状态
     */
    public AtomicInteger netState = new AtomicInteger(UploadStateStart);

    public AudioUploadBean(AudioFileBean audioFileBean) {
        super(audioFileBean);
    }

    @Override
    public boolean upload() {
        return false;
    }

    @Override
    public String toString() {
        return "AudioUploadBean{" +
                "netUrl='" + netUrl + '\'' +
                ", netState=" + netState +
                ", " + super.toString() +
                '}';
    }
}
