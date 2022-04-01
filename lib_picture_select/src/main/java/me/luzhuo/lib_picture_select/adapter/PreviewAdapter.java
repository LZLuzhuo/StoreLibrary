package me.luzhuo.lib_picture_select.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_file.bean.VideoFileBean;
import me.luzhuo.lib_picture_select.R;

public class PreviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FileBean> mDatas;
    private static final int TypeImage = 0x001;
    private static final int TypeVideo = 0x002;
    private static final int TypeAudio = 0x003;
    private static final int TypeOther = 0x004;

    // 测试用的数据
    public void setData(List<FileBean> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TypeImage:
                return new PreviewImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_select_item_preview_image, parent, false));
            default:
                return new PreviewOtherHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_select_item_preview_other, parent, false));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        FileBean fileBean = mDatas.get(position);
        if (fileBean instanceof ImageFileBean) return TypeImage;
        else if (fileBean instanceof VideoFileBean) return TypeVideo;
        else if (fileBean instanceof AudioFileBean) return TypeAudio;
        else return TypeOther;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TypeImage:
                ((PreviewImageHolder) holder).bindData((ImageFileBean)mDatas.get(position));
                break;
        }
    }

    public class PreviewImageHolder extends RecyclerView.ViewHolder {
        private PhotoView photoView;

        public PreviewImageHolder(@NonNull View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.picture_select_preview_photoview);
            photoView.setScaleType(ImageView.ScaleType.CENTER);
        }

        public void bindData(ImageFileBean fileBean) {
            photoView.setImageURI(fileBean.uriPath);
        }
    }

    public class PreviewOtherHolder extends RecyclerView.ViewHolder {

        public PreviewOtherHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
