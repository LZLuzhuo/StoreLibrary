package me.luzhuo.lib_picture_select;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;
import androidx.viewpager.widget.ViewPager;
import me.luzhuo.lib_core.app.base.CoreBaseActivity;
import me.luzhuo.lib_core.ui.statusbar.StatusBarManager;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_picture_select.adapter.PictureSelectPreviewAdapter;
import me.luzhuo.lib_picture_select.fragments.PictureSelectPreviewCallback;
import me.luzhuo.lib_picture_select.ui.PictureSelectPreviewBottomBar;
import me.luzhuo.lib_picture_select.ui.PictureSelectPreviewHeaderBar;

/**
 * 相册的预览功能
 */
public class PictureSelectPreviewActivity extends CoreBaseActivity implements PictureSelectPreviewCallback {
    private static final String TAG = PictureSelectPreviewActivity.class.getSimpleName();

    /**
     * 当前预览的相册组
     */
    private static List<FileBean> currentBucketFiles;
    /**
     * 所有的相册文件, 用于展示已选中的文件
     */
    private static List<FileBean> allFiles;
    @Nullable
    private static PictureSelectPreviewListener listener;
    private int index;
    private int selectCount;
    private int maxCount;

    private ViewPager preview_viewpager;
    private PictureSelectPreviewHeaderBar preview_header;
    private PictureSelectPreviewBottomBar preview_bottom;

    private PictureSelectPreviewAdapter pictureSelectPreviewAdapter;

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
        StatusBarManager.getInstance().transparent(this, false);
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

        pictureSelectPreviewAdapter = new PictureSelectPreviewAdapter(this, currentBucketFiles);
        preview_viewpager.setAdapter(pictureSelectPreviewAdapter);
        pictureSelectPreviewAdapter.setOnPictureSelectPreviewCallback(this);

    }

    private void initData() {
        preview_bottom.setDatas(getCurrentSelectFiles(allFiles));
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

    @Override
    public void onSingleClick(FileBean data) {
        TransitionManager.beginDelayedTransition(preview_header, getSlide(Gravity.TOP));
        TransitionManager.beginDelayedTransition(preview_bottom, getSlide(Gravity.BOTTOM));

        if (preview_header.getVisibility() != View.VISIBLE) {
            preview_header.setVisibility(View.VISIBLE);
            preview_bottom.setVisibility(View.VISIBLE);
        } else {
            preview_header.setVisibility(View.INVISIBLE);
            preview_bottom.setVisibility(View.INVISIBLE);
        }
    }

    private Slide getSlide(int slideEdge) {
        Slide slide = new Slide();
        slide.setDuration(300);
        slide.setSlideEdge(slideEdge);
        return slide;
    }

    public interface PictureSelectPreviewListener {

    }
}
