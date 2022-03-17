package me.luzhuo.lib_picture_compress.bean;

import java.util.concurrent.atomic.AtomicInteger;

import me.luzhuo.lib_file.bean.ImageFileBean;

public class ImageCompressBean extends ImageFileBean implements CompressState {

    /**
     * 压缩后文件的本地路径
     */
    public String compressPath;

    /**
     * 当前压缩状态
     */
    public AtomicInteger compressState = new AtomicInteger(CompressStateStart);

    public ImageCompressBean(ImageFileBean imageFileBean) {
        super(imageFileBean.id, imageFileBean.fileName, imageFileBean.mimeType, imageFileBean.uriPath, imageFileBean.urlPath, imageFileBean.bucketId, imageFileBean.bucketName, imageFileBean.size, imageFileBean.addedDate, imageFileBean.width, imageFileBean.height);
    }

    @Override
    public boolean compress() {
        this.compressState.set(CompressState.CompressStateCompressing);
        // TODO 压缩图片
        return true;
    }

    @Override
    public String toString() {
        return "ImageCompressBean{" +
                "compressPath='" + compressPath + '\'' +
                ", compressState=" + compressState +
                ", width=" + width +
                ", height=" + height +
                ", id=" + id +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", uriPath=" + uriPath +
                ", urlPath='" + urlPath + '\'' +
                ", bucketId=" + bucketId +
                ", bucketName='" + bucketName + '\'' +
                ", size=" + size +
                ", addedDate=" + addedDate +
                '}';
    }
}
