package me.luzhuo.lib_picture_compress.bean;

import androidx.annotation.WorkerThread;

/**
 * 压缩状态标识
 */
public interface CompressState {

    /**
     * 未开始
     */
    public static final int CompressStateStart = 1 << 1;

    /**
     * 压缩中
     */
    public static final int CompressStateCompressing = 1 << 2;

    /**
     * 已结束
     */
    public static final int CompressStateEnded = 1 << 3;

    /**
     * 压缩
     * @return true压缩成功, false压缩失败
     */
    @WorkerThread
    public boolean compress();
}
