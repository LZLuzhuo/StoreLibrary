package me.luzhuo.lib_picture_compress.bean;

import java.util.concurrent.atomic.AtomicInteger;

import me.luzhuo.lib_file.bean.AudioFileBean;

public class AudioCompressBean extends AudioFileBean implements CompressState{

    /**
     * 压缩后文件的本地路径
     */
    public String compressPath;

    /**
     * 当前压缩状态
     */
    public AtomicInteger compressState = new AtomicInteger(CompressStateStart);

    public AudioCompressBean(AudioFileBean audioFileBean) {
        super(audioFileBean.id, audioFileBean.fileName, audioFileBean.mimeType, audioFileBean.uriPath, audioFileBean.urlPath, audioFileBean.bucketId, audioFileBean.bucketName, audioFileBean.size, audioFileBean.addedDate, audioFileBean.duration);
        super.isOrigin = audioFileBean.isOrigin;
    }

    @Override
    public boolean compress() {
        this.compressState.set(CompressState.CompressStateCompressing);
        // TODO 压缩音频
        return false;
    }

    @Override
    public String toString() {
        return "AudioCompressBean{" +
                "compressPath='" + compressPath + '\'' +
                ", compressState=" + compressState +
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
