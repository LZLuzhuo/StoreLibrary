package me.luzhuo.lib_picture_upload.bean;

import androidx.annotation.WorkerThread;

/**
 * 上传文件接口
 */
public interface UploadState {
    /**
     * 未开始 4
     */
    public static final int UploadStateStart = 2 << 1;

    /**
     * 上传中 8
     */
    public static final int UploadStateCompressing = 2 << 2;

    /**
     * 上传成功 16
     */
    public static final int UploadStateEnded = 2 << 3;

    /**
     * 上传失败 32
     */
    public static final int UploadStateError = 2 << 4;

    /**
     * 上传文件
     * @return true上传成功, false上传失败
     */
    @WorkerThread
    public boolean upload();
}
