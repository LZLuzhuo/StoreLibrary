package me.luzhuo.storedemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.ListIterator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.luzhuo.lib_core.ui.toast.ToastManager;
import me.luzhuo.lib_file.bean.CheckableFileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_picture_compress.PictureSelectView;
import me.luzhuo.lib_picture_select_view.PictureSelectOriginView;
import me.luzhuo.lib_picture_select_view.callback.PictureViewSelectCallback;

public class PictureActivity extends AppCompatActivity {
    private static final String TAG = PictureActivity.class.getSimpleName();
    private PictureSelectView picture_select;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        picture_select = findViewById(R.id.picture_select);
        picture_select.setPictureListener(new PictureViewSelectCallback() {
            @Override
            public void onSelect(List<? extends CheckableFileBean> fileBeans) {
                boolean notSupport = notSupport(fileBeans);
                if (notSupport) ToastManager.show2(PictureActivity.this, "存在不支持的文件格式");
                picture_select.addDatas(fileBeans);
            }

            @Override
            public void onDelete(CheckableFileBean fileBean) {
                picture_select.deleteData(fileBean);
            }

            @Override
            public void onImageShowCallback(CheckableFileBean imageFileBean, int index, List<CheckableFileBean> imageFileBeans) {
                Log.e(TAG, "" + imageFileBean);
                Log.e(TAG, "" + index);
                Log.e(TAG, "" + imageFileBeans);
            }

            @Override
            public void onVideoShowCallback(CheckableFileBean videoFileBean, int index, List<CheckableFileBean> videoFileBeans) {
                Log.e(TAG, "" + videoFileBean);
                Log.e(TAG, "" + index);
                Log.e(TAG, "" + videoFileBeans);
            }

            @Override
            public void onAudioShowCallback(CheckableFileBean audioFileBean, int index, List<CheckableFileBean> audioFileBeans) {
                Log.e(TAG, "" + audioFileBean);
                Log.e(TAG, "" + index);
                Log.e(TAG, "" + audioFileBeans);
            }
        });
    }

    private boolean notSupport(List<? extends CheckableFileBean> fileBeans) {
        boolean notSupport = false;
        ListIterator<? extends CheckableFileBean> iterator = fileBeans.listIterator();
        while (iterator.hasNext()) {
            CheckableFileBean fileBean = iterator.next();
            if (fileBean instanceof ImageFileBean) {
                if (((ImageFileBean) fileBean).mimeType.equalsIgnoreCase("image/webp")) {
                    iterator.remove();
                    notSupport = true;
                }
            }
        }
        return notSupport;
    }

    public void ok(View view) {
        List<CheckableFileBean> datas = picture_select.getDatas();
        Log.e(TAG, "size: " + datas.size());
        for (CheckableFileBean data : datas) {
            Log.e(TAG, "data: " + data);
        }
    }
}
