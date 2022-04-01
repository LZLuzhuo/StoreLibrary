package me.luzhuo.lib_picture_select.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import me.luzhuo.lib_picture_select.R;

/**
 * 相册预览的头部
 */
public class PictureSelectPreviewHeaderBar extends RelativeLayout {
    public PictureSelectPreviewHeaderBar(Context context) {
        super(context);
    }

    public PictureSelectPreviewHeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureSelectPreviewHeaderBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        LayoutInflater.from(getContext()).inflate(R.layout.picture_select_layout_preview_header_bar, this, true);
    }
}
