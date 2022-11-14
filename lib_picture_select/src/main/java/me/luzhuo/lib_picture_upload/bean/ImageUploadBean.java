package me.luzhuo.lib_picture_upload.bean;

import java.util.concurrent.atomic.AtomicInteger;

import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_picture_compress.bean.ImageCompressBean;

public class ImageUploadBean extends ImageCompressBean implements UploadState {
    /**
     * 上传后文件的网路路径
     */
    public String netUrl;

    /**
     * 当前上传状态
     */
    public AtomicInteger netState = new AtomicInteger(UploadStateStart);

    public ImageUploadBean(ImageFileBean imageFileBean) {
        super(imageFileBean);
    }

    @Override
    public boolean upload() {
        return false;
    }

    @Override
    public String toString() {
        return "ImageUploadBean{" +
                "netUrl='" + netUrl + '\'' +
                ", netState=" + netState +
                ", " + super.toString() +
                '}';
    }
}
