package me.luzhuo.lib_picture_select.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.color.ColorManager;
import me.luzhuo.lib_picture_select.R;

/**
 * 相册选择的头部
 */
public class PictureSelectHeaderBar extends RelativeLayout implements View.OnClickListener {
    private TextView picture_select_complete;
    private final ColorManager color = new ColorManager();
    @Nullable
    private PictureSelectHeaderListener listener;
    /**
     * 相册被选中的数量, 有相册被选中之后, 才能点击发送按钮
     */
    private int selectCount = 0;
    private int maxCount = 0;

    public PictureSelectHeaderBar(Context context) {
        super(context);
    }

    public PictureSelectHeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureSelectHeaderBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        LayoutInflater.from(getContext()).inflate(R.layout.picture_select_layout_header_bar, this, true);
        picture_select_complete = findViewById(R.id.picture_select_complete);

        picture_select_complete.setOnClickListener(this);

        completeButtonStyle();
    }

    private void completeButtonStyle() {
        if (selectCount > 0) {
            picture_select_complete.setBackgroundResource(R.drawable.picture_select_bg_complete);
            picture_select_complete.setTextColor(color.getColor(R.color.picture_select_complete_text));
            picture_select_complete.setText(String.format("完成(%d/%d)", selectCount, maxCount));
        } else {
            picture_select_complete.setBackgroundResource(R.drawable.picture_select_bg_complete_default);
            picture_select_complete.setTextColor(color.getColor(R.color.picture_select_complete_text_default));
            picture_select_complete.setText("完成");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == picture_select_complete) {
            if (selectCount > 0 && listener != null) listener.onCompleteButton();
        }
    }

    /**
     * 设置发送按钮状态
     * @param isSelected true有图片被选中, false没有图片被选中
     */
    public void setCompleteButton(boolean isSelected, int maxCount) {
        if (isSelected) selectCount++;
        else selectCount--;
        this.maxCount = maxCount;
        completeButtonStyle();
    }

    public interface PictureSelectHeaderListener {
        /**
         * 用户点击了发送按钮
         * 仅在有选中图片的时候才回调
         */
        public void onCompleteButton();
    }

    public void setOnPictureSelectHeaderListener(@Nullable PictureSelectHeaderListener listener) {
        this.listener = listener;
    }
}
