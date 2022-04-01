package me.luzhuo.lib_picture_select.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_file.bean.VideoFileBean;
import me.luzhuo.lib_picture_select.R;
import me.luzhuo.lib_picture_select.engine.GlideImageEngine;
import me.luzhuo.lib_picture_select.engine.ImageEngine;

public class PictureSelectPreviewBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FileBean> mDatas = new ArrayList<>();
    private Context context;
    private ImageEngine imageEngine = GlideImageEngine.getInstance();

    public void setData(List<FileBean> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new RecyclerHolder(android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_select_item_preview_bottom_list, parent, false));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecyclerHolder) holder).bindData(mDatas.get(position));
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        public ImageView preview_list_cover;

        public RecyclerHolder(View itemView) {
            super(itemView);
            preview_list_cover = itemView.findViewById(R.id.picture_select_preview_list_cover);
        }

        public void bindData(FileBean data) {
            if (data instanceof ImageFileBean) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) imageEngine.loadGridImage(context, data.uriPath, preview_list_cover);
                else imageEngine.loadGridImage(context, data.urlPath, preview_list_cover);
            } else if (data instanceof VideoFileBean) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) imageEngine.loadGridVideoCover(context, data.uriPath, preview_list_cover);
                else imageEngine.loadGridImage(context, data.urlPath, preview_list_cover);
            } else if (data instanceof AudioFileBean) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) imageEngine.loadGridAudio(context, data.uriPath, preview_list_cover);
                else imageEngine.loadGridAudio(context, data.urlPath, preview_list_cover);
            } else { // 文档
                imageEngine.loadGridOther(context, R.mipmap.picture_select_icon_document, preview_list_cover);
            }
        }
    }
}

