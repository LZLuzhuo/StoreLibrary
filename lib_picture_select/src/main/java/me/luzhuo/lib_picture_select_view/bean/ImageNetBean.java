package me.luzhuo.lib_picture_select_view.bean;

import androidx.annotation.Nullable;
import me.luzhuo.lib_file.bean.CheckableFileBean;

/**
 * 网络的图片文件
 */
public class ImageNetBean extends CheckableFileBean {
    public String netUrl;
    @Nullable
    public Object tag;

    public ImageNetBean(String netUrl) {
        this.netUrl = netUrl;
    }

    @Override
    public String toString() {
        return "ImageNetBean{" +
                "netUrl='" + netUrl + '\'' +
                ", tag=" + tag +
                '}';
    }
}