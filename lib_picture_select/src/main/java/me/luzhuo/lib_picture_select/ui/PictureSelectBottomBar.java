package me.luzhuo.lib_picture_select.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import me.luzhuo.lib_picture_select.R;

import static android.app.Activity.RESULT_OK;

/**
 * 相册选择的底部
 */
public class PictureSelectBottomBar extends RelativeLayout {
    
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
    }
}
