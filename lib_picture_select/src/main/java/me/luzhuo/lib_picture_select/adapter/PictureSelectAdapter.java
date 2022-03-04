package me.luzhuo.lib_picture_select.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.ColorFilter;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import me.luzhuo.lib_core.ui.dialog.Dialog;
import me.luzhuo.lib_core.ui.toast.ToastManager;
import me.luzhuo.lib_file.FileManager;
import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_file.bean.VideoFileBean;
import me.luzhuo.lib_file.enums.FileType;
import me.luzhuo.lib_file.store.FileStore;
import me.luzhuo.lib_picture_select.PictureSelectListener;
import me.luzhuo.lib_picture_select.R;

public class PictureSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FileBean> mDatas = new ArrayList<>();
    private static final int TYPE_CAMERA = 1, TYPE_PICTURE = 2;
    private int fileType;
    private int maxCount;
    private boolean isShowCamera;
    private boolean isSingleReturn;
    private Context context;
    private FileManager fileManager;
    private PictureSelectListener listener;

    public PictureSelectAdapter(int fileType) {
        this(fileType, Integer.MAX_VALUE, false);
    }
    public PictureSelectAdapter(int fileType, int maxCount, boolean isShowCamera) {
        this.fileType = fileType;
        this.maxCount = maxCount;
        this.isShowCamera = isShowCamera;
        this.isSingleReturn = maxCount <= 1;
        this.fileManager = new FileManager();
    }

    public void setData(List<FileBean> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType == TYPE_CAMERA) return new CameraHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_select_item_camera, parent, false));
        else return new PictureHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_select_item_picture, parent, false));
    }

    @Override
    public int getItemCount() {
        return isShowCamera ? mDatas.size() + 1 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowCamera) {
            if (position == 0) return TYPE_CAMERA;
            else return TYPE_PICTURE;
        } else {
            return TYPE_PICTURE;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_CAMERA) {
            ((CameraHolder) holder).bindData();
        } else {
            int pos = isShowCamera ? position - 1 : position;
            ((PictureHolder) holder).bindData(mDatas.get(pos));
        }
    }

    public class CameraHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View picture_select_parent;
        public ImageView picture_select_camera_text;

        public CameraHolder(View itemView) {
            super(itemView);
            picture_select_parent = itemView.findViewById(R.id.picture_select_parent);
            picture_select_camera_text = itemView.findViewById(R.id.picture_select_camera_text);

            picture_select_parent.setOnClickListener(this);
        }

        public void bindData() {
            if (fileType == FileStore.TypeImage || fileType == FileStore.TypeGif || fileType == FileStore.TypeImage + FileStore.TypeGif) picture_select_camera_text.setImageResource(R.mipmap.picture_select_text_record_image);
            else if (fileType == FileStore.TypeVideo) picture_select_camera_text.setImageResource(R.mipmap.picture_select_text_record_video);
            else if (fileType == FileStore.TypeAudio) picture_select_camera_text.setImageResource(R.mipmap.picture_select_text_record_audio);
            else picture_select_camera_text.setImageResource(R.mipmap.picture_select_text_record_image_or_video);
        }

        @Override
        public void onClick(View v) {
            if (v == picture_select_parent) {
                if (listener != null) listener.onCamera(getSelectedSize() >= maxCount);
            }
        }
    }

    public class PictureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView picture_select_pic;
        public View picture_select_pic_check_zone;
        public TextView picture_select_pic_check;
        public TextView picture_select_pic_gif;
        public TextView picture_select_duration;
        public View picture_select_parent;

        public PictureHolder(View itemView) {
            super(itemView);
            picture_select_pic = itemView.findViewById(R.id.picture_select_pic);
            picture_select_pic_check_zone = itemView.findViewById(R.id.picture_select_pic_check_zone);
            picture_select_pic_check = itemView.findViewById(R.id.picture_select_pic_check);
            picture_select_pic_gif = itemView.findViewById(R.id.picture_select_pic_gif);
            picture_select_duration = itemView.findViewById(R.id.picture_select_duration);
            picture_select_parent = itemView.findViewById(R.id.picture_select_parent);

            picture_select_pic_check_zone.setOnClickListener(this);
            picture_select_parent.setOnClickListener(this);
        }

        public void bindData(FileBean data) {
            // TODO 占位图标需要继续设计
            if (FileType.getFileType(data.mimeType) == FileType.Image) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Glide.with(context).load(data.uriPath).placeholder(R.mipmap.picture_select_icon_image).into(picture_select_pic);
                else Glide.with(context).load(data.urlPath).placeholder(R.mipmap.picture_select_icon_image).into(picture_select_pic);
            } else if (FileType.getFileType(data.mimeType) == FileType.Video) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Glide.with(context).load(data.uriPath).placeholder(R.mipmap.picture_select_icon_video).into(picture_select_pic);
                else Glide.with(context).load(data.urlPath).placeholder(R.mipmap.picture_select_icon_video).into(picture_select_pic);
            } else if (FileType.getFileType(data.mimeType) == FileType.Audio) {
                Glide.with(context).load(R.mipmap.picture_select_icon_audio).into(picture_select_pic);
            } else { // 文档
                Glide.with(context).load(R.mipmap.picture_select_icon_document).into(picture_select_pic);
            }

            if (isSingleReturn) {
                picture_select_pic_check_zone.setVisibility(View.GONE);
                picture_select_pic_check.setVisibility(View.GONE);
            } else {
                picture_select_pic_check_zone.setVisibility(View.VISIBLE);
                picture_select_pic_check.setVisibility(View.VISIBLE);
                selectCheckBox(data.isChecked);
                setSelectableMask(data);
            }

            // gif + webp
            if (data.mimeType.toLowerCase().equals("image/gif") || data.mimeType.toLowerCase().equals("image/webp")) {
                picture_select_pic_gif.setVisibility(View.VISIBLE);
                picture_select_pic_gif.setText(data.mimeType.toLowerCase().equals("image/gif") ? "GIF" : "WEBP");
            } else if (FileType.getFileType(data.mimeType) == FileType.Image && isLongImage(((ImageFileBean) data).width, ((ImageFileBean) data).height)) {
                picture_select_pic_gif.setVisibility(View.VISIBLE);
                picture_select_pic_gif.setText("长图");
            } else {
                picture_select_pic_gif.setVisibility(View.GONE);
            }

            // video + audio
            if (FileType.getFileType(data.mimeType) == FileType.Video || FileType.getFileType(data.mimeType) == FileType.Audio) {
                picture_select_duration.setVisibility(View.VISIBLE);
                if (FileType.getFileType(data.mimeType) == FileType.Video) {
                    long duration = ((VideoFileBean) data).duration;
                    String durationFormat = String.format(Locale.getDefault(), "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
                    picture_select_duration.setText(durationFormat);
                    picture_select_duration.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.picture_select_icon_video_time, 0, 0, 0);
                } else {
                    long duration = ((AudioFileBean) data).duration;
                    String durationFormat = String.format(Locale.getDefault(), "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
                    picture_select_duration.setText(durationFormat);
                    picture_select_duration.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.picture_select_icon_audio_time, 0, 0, 0);
                }
            } else {
                picture_select_duration.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            int pos = isShowCamera ? getLayoutPosition() - 1 : getLayoutPosition();
            if (v == picture_select_pic_check_zone) {
                selectImage(pos);
            } else if (v == picture_select_parent) {
                showImage(pos);
            }
        }

        /**
         * 选中图片事件
         */
        private void selectImage(int position) {
            FileBean data = mDatas.get(position);
            boolean isSelected = data.isChecked;
            if (!data.isCheckable) {
                showMaskErrorDialog();
                return;
            }

            if (!picture_select_pic_check.isSelected() && getSelectedSize() >= maxCount) {
                showMaskErrorDialog();
                return;
            }

            boolean isExists;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) isExists = fileManager.exists(context, data.uriPath);
            else isExists = fileManager.exists(context, data.urlPath);
            if (!isExists) {
                toastFileError();
                return;
            }

            if (isSelected) {
                data.isChecked = false;
                zoom(picture_select_pic, false);
            } else {
                data.isChecked = true;
                zoom(picture_select_pic, true);
                picture_select_pic_check.startAnimation(AnimationUtils.loadAnimation(context, R.anim.picture_select_check_anim));
            }

            if (isSelected) { // true -> false   10 -> 9
                if (getSelectedSize() >= maxCount - 1) notifyDataSetChanged();
            } else { // false -> true   9 -> 10
                if (getSelectedSize() >= maxCount) notifyDataSetChanged();
            }

            selectCheckBox(data.isChecked);

            if (listener != null) listener.onSelect(isSingleReturn, data);
        }

        /**
         * 显示图片
         */
        private void showImage(int position) {
            FileBean data = mDatas.get(position);
            if (isSingleReturn) {
                if (listener != null) listener.onSelect(isSingleReturn, data);
            } else {
                if (listener != null) listener.onShow(data, position, mDatas);
            }
        }

        /**
         * 更新选中图标
         */
        private void selectCheckBox(boolean isChecked) {
            picture_select_pic_check.setSelected(isChecked);
            ColorFilter colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(isChecked ? 0x80000000 : 0x20000000, BlendModeCompat.SRC_ATOP);
            picture_select_pic.setColorFilter(colorFilter);
        }

        /**
         * 不可选图片的遮罩
         */
        private void setSelectableMask(FileBean data) {
            if (getSelectedSize() >= maxCount) {
                boolean isSelected = picture_select_pic_check.isSelected();
                ColorFilter colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(isSelected ? 0x80000000 : 0x99FFFFFF, BlendModeCompat.SRC_ATOP);
                picture_select_pic.setColorFilter(colorFilter);
                data.isCheckable = isSelected;
            } else {
                data.isCheckable = true;
            }
        }

        /**
         * 遮罩图片被点击后的提示
         */
        private void showMaskErrorDialog() {
            StringBuilder content = new StringBuilder()
                    .append("你最多只能选择")
                    .append(getSelectedSize());

            if (fileType == FileStore.TypeImage || fileType == FileStore.TypeGif || fileType == FileStore.TypeImage + FileStore.TypeGif) content.append("张");
            else content.append("个");

            if (fileType == FileStore.TypeImage || fileType == FileStore.TypeGif || fileType == FileStore.TypeImage + FileStore.TypeGif) content.append("照片");
            else if (fileType == FileStore.TypeVideo) content.append("视频");
            else if (fileType == FileStore.TypeAudio) content.append("音频");
            else content.append("文件");

            Dialog.instance().show(context, "注意", content.toString(), "确定", null, true, null, null);
        }

        /**
         * 文件损坏的提示信息
         */
        private void toastFileError() {
            if (fileType == FileStore.TypeImage || fileType == FileStore.TypeGif || fileType == FileStore.TypeImage + FileStore.TypeGif) ToastManager.show(context, "图片已损坏!");
            else if (fileType == FileStore.TypeVideo) ToastManager.show(context, "视频已损坏!");
            else if (fileType == FileStore.TypeAudio) ToastManager.show(context, "音频已损坏!");
            else ToastManager.show(context, "文件已损坏!");
        }

        /**
         * 选中/取消选中 图片时, 图片的放大/缩放效果
         * @param isZoom true放大, false缩小
         */
        private void zoom(View view, boolean isZoom) {
            AnimatorSet set = new AnimatorSet();
            if (isZoom) set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.12f), ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.12f));
            else set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.12f, 1f), ObjectAnimator.ofFloat(view, "scaleY", 1.12f, 1f));
            set.setDuration(450);
            set.start();
        }

        /**
         * 是否是长图
         */
        private boolean isLongImage(int width, int height) {
            if (width <= 0 || height <= 0) return false;
            return height > width * 3;
        }
    }

    /**
     * 获取当前选中图片的数量
     */
    private int getSelectedSize() {
        int selectCount = 0;
        for (FileBean fileBean : mDatas) {
            if (fileBean.isChecked) selectCount += 1;
        }
        return selectCount;
    }

    public void setPictureSelectListener(PictureSelectListener listener) {
        this.listener = listener;
    }
}
