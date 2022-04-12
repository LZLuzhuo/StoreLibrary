package me.luzhuo.lib_picture_select.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_picture_select.R;
import me.luzhuo.lib_picture_select.adapter.PictureSelectPreviewBottomAdapter;

/**
 * 相册预览的底部
 */
public class PictureSelectPreviewBottomBar extends RelativeLayout implements CompoundButton.OnCheckedChangeListener {
    private PictureSelectPreviewBottomAdapter adapter;
    private RecyclerView preview_bottom_list;
    private CheckBox preview_selected;
    private OnPreviewBottomBarCallback callback;

    public PictureSelectPreviewBottomBar(Context context) {
        super(context);
    }

    public PictureSelectPreviewBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureSelectPreviewBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        LayoutInflater.from(getContext()).inflate(R.layout.picture_select_layout_preview_bottom_bar, this, true);
        preview_bottom_list = findViewById(R.id.picture_select_preview_bottom_list);
        preview_selected = findViewById(R.id.picture_select_preview_selected);

        preview_selected.setOnCheckedChangeListener(this);

        preview_bottom_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new PictureSelectPreviewBottomAdapter();
        preview_bottom_list.setAdapter(adapter);
    }

    public void setSelectPreviewDatas(List<FileBean> datas) {
        adapter.setData(datas);
    }

    /**
     * 检查是否选中
     */
    public void checkSelect(boolean checkSelect) {
        preview_selected.setChecked(checkSelect);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!buttonView.isPressed()) return; // 不处理非用户触发的回调
        if (callback != null) callback.onCheckedChanged(isChecked);
    }

    public interface OnPreviewBottomBarCallback {
        /**
         * 用户手动点击选择触发的回调
         */
        public void onCheckedChanged(boolean isChecked);
    }

    public void setOnPreviewBottomBarListener(OnPreviewBottomBarCallback callback) {
        this.callback = callback;
    }
}
