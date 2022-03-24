package me.luzhuo.lib_picture_compress.bean;

import java.util.concurrent.atomic.AtomicInteger;

import me.luzhuo.lib_file.bean.VideoFileBean;

public class VideoCompressBean extends VideoFileBean implements CompressState {

    /**
     * 压缩后文件的本地路径
     */
    public String compressPath;

    /**
     * 当前压缩状态
     */
    public AtomicInteger compressState = new AtomicInteger(CompressStateStart);

    public VideoCompressBean(VideoFileBean videoFileBean) {
        super(videoFileBean.id, videoFileBean.fileName, videoFileBean.mimeType, videoFileBean.uriPath, videoFileBean.urlPath, videoFileBean.bucketId, videoFileBean.bucketName, videoFileBean.size, videoFileBean.addedDate, videoFileBean.width, videoFileBean.height, videoFileBean.duration);
        super.isOrigin = videoFileBean.isOrigin;
    }

    @Override
    public boolean compress() {
        this.compressState.set(CompressState.CompressStateCompressing);
        // TODO 压缩视频
        return false;
    }

    @Override
    public String toString() {
        return "VideoCompressBean{" +
                "compressPath='" + compressPath + '\'' +
                ", compressState=" + compressState +
                ", width=" + width +
                ", height=" + height +
                ", duration=" + duration +
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
