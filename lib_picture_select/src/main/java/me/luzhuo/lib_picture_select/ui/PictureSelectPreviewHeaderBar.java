package me.luzhuo.lib_picture_select.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;
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

    private View close;

    {
        LayoutInflater.from(getContext()).inflate(R.layout.picture_select_layout_preview_header_bar, this, true);
        View statusBarSpace = findViewById(R.id.picture_select_statusbar_space);
        close = findViewById(R.id.picture_select_close);

        statusBarSpace.getLayoutParams().height = new UICalculation(getContext()).getStatusBarHeight(statusBarSpace);

        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == close) {
            ((FragmentActivity) getContext()).finish();
        }
    }
}
