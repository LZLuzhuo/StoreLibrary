package me.luzhuo.lib_picture_compress.bean;

import java.util.concurrent.atomic.AtomicInteger;

import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_image_compress.ImageCompress;
import me.luzhuo.lib_picture_select.PictureSelectUtils;

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
        super.isOrigin = imageFileBean.isOrigin;
    }

    @Override
    public boolean compress() {
        if (isOrigin) return false;
        this.compressState.set(CompressState.CompressStateCompressing);

        compressPath = new ImageCompress().compress(this);
        if (compressPath != null) {
            this.compressState.set(CompressState.CompressStateEnded);
            return true;
        } else {
            this.compressState.set(CompressState.CompressStateError);
            return false;
        }
    }

    @Override
    public boolean checkCopyFile() {
        return PictureSelectUtils.checkCopyFile(this);
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
                ", isOrigin=" + isOrigin +
                '}';
    }
}
