package me.luzhuo.lib_picture_select_view.bean;

import androidx.annotation.Nullable;
import me.luzhuo.lib_file.bean.CheckableFileBean;

/**
 * 网络的视频文件
 */
public class VideoNetBean extends CheckableFileBean {
    public String netUrl;
    @Nullable
    public String coverUrl;
    @Nullable
    public Object tag;

    public VideoNetBean(String netUrl) {
        this.netUrl = netUrl;
    }

    @Override
    public String toString() {
        return "VideoNetBean{" +
                "netUrl='" + netUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", tag=" + tag +
                '}';
    }
}