package me.luzhuo.lib_picture_select.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_picture_select.R;
import me.luzhuo.lib_picture_select.adapter.HeaderBucketPopAdapter;
import me.luzhuo.lib_picture_select.bean.PictureGroup;

/**
 * 相册分组切换弹窗
 */
public class HeaderBucketPopWindow extends PopupWindow implements HeaderBucketPopAdapter.OnBucketPopCallback {

    private HeaderBucketPopAdapter adapter;
    @Nullable
    private View anchorView;
    @Nullable
    private HeaderBucketPopAdapter.OnBucketPopCallback callback;

    public HeaderBucketPopWindow(Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.picture_select_layout_header_bucket_pop, null, false);
        RecyclerView recyclerView = view.findViewById(R.id.header_bucket_pop_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new HeaderBucketPopAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnBucketPopCallback(this);

        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setDatas(Map<Long, PictureGroup> pictureBucket) {
        adapter.setDatas(pictureBucket);
    }

    public void setAnchorView(@NonNull View anchorView) {
        this.anchorView = anchorView;
    }

    public void show(long bucketId) {
        if (this.isShowing()) this.dismiss();
        else {
            if (anchorView == null) return;
            adapter.setCurrentBucket(bucketId);
            this.showAsDropDown(anchorView, 0, 0);
        }
    }

    public void setOnBucketPopCallback(HeaderBucketPopAdapter.OnBucketPopCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onBucketSelect(long bucket) {
        if (callback != null) callback.onBucketSelect(bucket);
        this.dismiss();
    }
}
