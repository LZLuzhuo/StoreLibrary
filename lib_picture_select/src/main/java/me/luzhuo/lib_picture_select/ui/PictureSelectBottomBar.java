package me.luzhuo.lib_picture_select.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.color.ColorManager;
import me.luzhuo.lib_picture_select.R;

/**
 * 相册选择的底部
 */
public class PictureSelectBottomBar extends RelativeLayout implements View.OnClickListener {
    private TextView picture_select_preview;
    private CheckBox picture_select_origin;
    private final ColorManager color = new ColorManager();
    @Nullable
    private PictureSelectBottomListener listener;
    /**
     * 相册被选中的数量, 有相册被选中之后, 才能进行预览操作
     */
    private int selectCount = 0;
    
    public PictureSelectBottomBar(Context context) {
        super(context);
    }

    public PictureSelectBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureSelectBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        LayoutInflater.from(getContext()).inflate(R.layout.picture_select_layout_bottom_bar, this, true);
        picture_select_preview = findViewById(R.id.picture_select_preview);
        picture_select_origin = findViewById(R.id.picture_select_origin);

        picture_select_preview.setOnClickListener(this);

        previewButtonStyle();
    }

    private void previewButtonStyle() {
        if (selectCount > 0) {
            picture_select_preview.setTextColor(color.getColor(R.color.picture_select_complete_text));
            picture_select_preview.setText(String.format(Locale.CHINESE, "预览(%d)", selectCount));
        } else {
            picture_select_preview.setTextColor(color.getColor(R.color.picture_select_preview_text_default));
            picture_select_preview.setText("预览");
        }
    }

    /**
     * 设置预览按钮状态
     * @param isSelected true有图片被选中, false没有图片被选中
     */
    public void setPreviewButton(boolean isSelected) {
        if (isSelected) selectCount++;
        else selectCount--;
        previewButtonStyle();
    }

    public boolean isOrigin() {
        return picture_select_origin.isChecked();
    }

    /**
     * 选择原图是否可用
     * @param original true可用, false不可用
     */
    public void setOriginal(boolean original) {
        picture_select_origin.setVisibility(original ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v == picture_select_preview) {
            if (listener != null && selectCount > 0) listener.onPreview();
        }
    }

    public interface PictureSelectBottomListener {
        /**
         * 预览相册
         */
        public void onPreview();
    }

    public void setOnPictureSelectBottomListener(@Nullable PictureSelectBottomListener listener) {
        this.listener = listener;
    }
}
