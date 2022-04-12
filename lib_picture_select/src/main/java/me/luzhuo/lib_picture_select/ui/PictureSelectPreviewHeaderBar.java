package me.luzhuo.lib_picture_select.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import androidx.fragment.app.FragmentActivity;
import me.luzhuo.lib_core.app.color.ColorManager;
import me.luzhuo.lib_core.ui.calculation.UICalculation;
import me.luzhuo.lib_picture_select.R;

/**
 * 相册预览的头部
 */
public class PictureSelectPreviewHeaderBar extends RelativeLayout implements View.OnClickListener {
    public PictureSelectPreviewHeaderBar(Context context) {
        super(context);
    }

    public PictureSelectPreviewHeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureSelectPreviewHeaderBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private View picture_select_close;
    private TextView picture_select_number;
    private TextView picture_select_complete;
    /**
     * 相册被选中的数量, 有相册被选中之后, 才能点击发送按钮
     */
    private int selectCount = 0;
    private int maxCount = 0;
    private final ColorManager color = new ColorManager();

    {
        LayoutInflater.from(getContext()).inflate(R.layout.picture_select_layout_preview_header_bar, this, true);
        View statusBarSpace = findViewById(R.id.picture_select_statusbar_space);
        picture_select_close = findViewById(R.id.picture_select_close);
        picture_select_number = findViewById(R.id.picture_select_number);
        picture_select_complete = findViewById(R.id.picture_select_complete);

        statusBarSpace.getLayoutParams().height = new UICalculation(getContext()).getStatusBarHeight(statusBarSpace);

        picture_select_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == picture_select_close) {
            ((FragmentActivity) getContext()).finish();
        }
    }

    public void setCurrentIndex(int index, int count) {
        picture_select_number.setText(String.format(Locale.CHINESE, "%d/%d", index + 1, count));
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

    public void setCompleteButton(int selectCount, int maxCount) {
        this.selectCount = selectCount;
        this.maxCount = maxCount;
        completeButtonStyle();
    }

    private void completeButtonStyle() {
        if (selectCount > 0) {
            picture_select_complete.setBackgroundResource(R.drawable.picture_select_bg_complete);
            picture_select_complete.setTextColor(color.getColor(R.color.picture_select_complete_text));
            picture_select_complete.setText(String.format(Locale.CHINESE, "完成(%d/%d)", selectCount, maxCount));
        } else {
            picture_select_complete.setBackgroundResource(R.drawable.picture_select_bg_complete_default);
            picture_select_complete.setTextColor(color.getColor(R.color.picture_select_complete_text_default));
            picture_select_complete.setText("完成");
        }
    }
}
