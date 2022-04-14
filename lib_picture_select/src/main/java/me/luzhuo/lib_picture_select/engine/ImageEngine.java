package me.luzhuo.lib_picture_select.engine;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

/**
 * 普通大图加载引擎
 */
public interface ImageEngine {
    /**
     * 加载Gif图片
     * @param context Context
     * @param uri Gif的本地Uri地址
     * @param imageView ImageView
     */
    public void loadGif(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView);

    /**
     * 加载Gif图片
     * @param context Context
     * @param url Gif的本地地址
     * @param imageView ImageView
     */
    public void loadGif(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * 加载视频封面
     * @param context Context
     * @param uri 本地视频的Uri地址
     * @param imageView 视频封面ImageView
     */
    public void loadVideoCover(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView);

    /**
     * 加载视频封面
     * @param context Context
     * @param url 
     * @param imageView
     */
    public void loadVideoCover(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);
}
