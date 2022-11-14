package me.luzhuo.lib_picture_compress.bean;

import java.util.concurrent.atomic.AtomicInteger;

import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_picture_select.PictureSelectUtils;

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
        if (isOrigin) return false;
        this.compressState.set(CompressState.CompressStateCompressing);

        // TODO 压缩音频
        this.compressState.set(CompressState.CompressStateError);
        return false;
    }

    @Override
    public boolean checkCopyFile() {
        return PictureSelectUtils.checkCopyFile(this);
    }

    @Override
    public String toString() {
        return "AudioCompressBean{" +
                "compressPath='" + compressPath + '\'' +
                ", compressState=" + compressState +
                ", " + super.toString() +
                '}';
    }
}
