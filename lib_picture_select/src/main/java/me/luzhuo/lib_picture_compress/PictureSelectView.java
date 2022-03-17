package me.luzhuo.lib_picture_compress;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_file.bean.CheckableFileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_file.bean.VideoFileBean;
import me.luzhuo.lib_picture_compress.bean.AudioCompressBean;
import me.luzhuo.lib_picture_compress.bean.CompressState;
import me.luzhuo.lib_picture_compress.bean.ImageCompressBean;
import me.luzhuo.lib_picture_compress.bean.VideoCompressBean;
import me.luzhuo.lib_picture_select_view.PictureSelectOriginView;
import me.luzhuo.lib_picture_select_view.adapter.PictureViewSelectAdapter;
import me.luzhuo.lib_picture_select_view.adapter.PictureViewShowAdapter;

public class PictureSelectView extends PictureSelectOriginView {
    private static final String TAG = PictureSelectView.class.getSimpleName();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final static Handler mainThread = new Handler(Looper.getMainLooper());

    public PictureSelectView(@NonNull Context context) {
        super(context);
    }

    public PictureSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addDatas(List<? extends CheckableFileBean> files) {
        if (adapter instanceof PictureViewShowAdapter) {
            ((PictureViewShowAdapter) adapter).addDatas(files);
        } else if (adapter instanceof PictureViewSelectAdapter) {

            // local file cover compress file.
            List<CheckableFileBean> compressFiles = new ArrayList<>();
            ListIterator<? extends CheckableFileBean> listIterator = files.listIterator();
            while(listIterator.hasNext()) {
                CheckableFileBean data = listIterator.next();
                listIterator.remove();
                if (data instanceof ImageFileBean) compressFiles.add(new ImageCompressBean((ImageFileBean) data));
                else if (data instanceof VideoFileBean) compressFiles.add(new VideoCompressBean((VideoFileBean) data));
                else if (data instanceof AudioFileBean) compressFiles.add(new AudioCompressBean((AudioFileBean) data));
                else compressFiles.add(data);
            }
            ((PictureViewSelectAdapter) adapter).addDatas(compressFiles);

            // compress files
            for (CheckableFileBean compressFile : compressFiles) {
                if (compressFile instanceof CompressState) threadPool.execute(new CompressRunnable((CompressState) compressFile));
            }
        }
    }

    protected static class CompressRunnable implements Runnable {
        private CompressState compressFile;
        public CompressRunnable(CompressState compressFile) {
            this.compressFile = compressFile;
        }
        @Override
        public void run() {
            boolean compress = compressFile.compress();
            if (compress) Log.e(TAG, "压缩成功: " + compressFile);
            else Log.e(TAG, "压缩失败: " + compressFile);
        }
    }

    /**
     * 获取数据, 包括网络文件, 压缩文件, 原始文件
     */
    public List<CheckableFileBean> getDatas() {
        return getOriginalDatas();
    }

}
