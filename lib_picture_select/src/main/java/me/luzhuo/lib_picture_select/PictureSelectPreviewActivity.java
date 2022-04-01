package me.luzhuo.lib_picture_select;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;
import me.luzhuo.lib_core.app.base.CoreBaseActivity;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_picture_select.adapter.PreviewAdapter;
import me.luzhuo.lib_picture_select.ui.PictureSelectPreviewBottomBar;
import me.luzhuo.lib_picture_select.ui.PictureSelectPreviewHeaderBar;

/**
 * 相册的预览功能
 */
public class PictureSelectPreviewActivity extends CoreBaseActivity {
    private static final String TAG = PictureSelectPreviewActivity.class.getSimpleName();

    /**
     * 当前预览的相册组
     */
    private static List<FileBean> currentBucketFiles;
    private static List<FileBean> allFiles;
    @Nullable
    private static PictureSelectPreviewListener listener;
    private int index;
    private int selectCount;
    private int maxCount;

    private ViewPager2 preview_viewpager;
    private PictureSelectPreviewHeaderBar preview_header;
    private PictureSelectPreviewBottomBar preview_bottom;

    private PreviewAdapter previewAdapter;

    public static void start(Context context, int index, List<FileBean> currentBucketFiles, List<FileBean> allFiles, int selectCount, int maxCount, PictureSelectPreviewListener listener) {
        PictureSelectPreviewActivity.currentBucketFiles = currentBucketFiles;
        PictureSelectPreviewActivity.allFiles = allFiles;
        PictureSelectPreviewActivity.listener = listener;
        Intent intent = new Intent(context, PictureSelectPreviewActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("selectCount", selectCount);
        intent.putExtra("maxCount", maxCount);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.picture_select_activity_preview);
        index = getIntent().getIntExtra("index", 0);
        selectCount = getIntent().getIntExtra("selectCount", 0);
        maxCount = getIntent().getIntExtra("maxCount", 0);

        initView();
        initData();
    }

    private void initView() {
        preview_viewpager = findViewById(R.id.picture_select_preview_viewpager);
        preview_header = findViewById(R.id.picture_select_preview_header);
        preview_bottom = findViewById(R.id.picture_select_preview_bottom);

        previewAdapter = new PreviewAdapter();
        preview_viewpager.setAdapter(previewAdapter);

    }

    private void initData() {
        preview_bottom.setDatas(getCurrentSelectFiles(allFiles));
        previewAdapter.setData(currentBucketFiles);
    }

    private List<FileBean> getCurrentSelectFiles(List<FileBean> fileBeans) {
        List<FileBean> selectFiles = new ArrayList<>();
        for (FileBean fileBean : fileBeans) {
            if (fileBean.isChecked) selectFiles.add(fileBean);
        }
        return selectFiles;
    }

    @Override
    protected void onDestroy() {
        currentBucketFiles = null;
        allFiles = null;
        listener = null;
        super.onDestroy();
    }

    public interface PictureSelectPreviewListener {

    }
}
