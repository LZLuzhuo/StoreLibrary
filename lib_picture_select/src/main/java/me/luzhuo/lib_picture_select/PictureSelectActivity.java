package me.luzhuo.lib_picture_select;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import me.luzhuo.lib_core.app.base.CoreBaseActivity;
import me.luzhuo.lib_core.media.camera.CameraManager;
import me.luzhuo.lib_core.media.camera.ICameraCallback;
import me.luzhuo.lib_core.ui.dialog.Dialog;
import me.luzhuo.lib_file.FileManager;
import me.luzhuo.lib_file.FileStoreManager;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_file.store.FileStore;
import me.luzhuo.lib_permission.Permission;
import me.luzhuo.lib_permission.PermissionCallback;
import me.luzhuo.lib_picture_select.adapter.PictureSelectAdapter;

import static me.luzhuo.lib_file.store.FileStore.TypeImage;

/**
 * 图片选择的Activity界面
 */
public class PictureSelectActivity extends CoreBaseActivity implements PictureSelectListener, ICameraCallback {
    private RecyclerView picture_select_rec;
    private PictureSelectAdapter adapter;
    private int fileType;
    private int maxCount;
    private List<FileBean> lists;
    private CameraManager camera;
    private FileManager fileManager = new FileManager();

    public static void start(Context context, int fileType, int maxCount) {
        Intent intent = new Intent(context, PictureSelectActivity.class);
        intent.putExtra("fileType", fileType);
        intent.putExtra("maxCount", maxCount);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.picture_select_activity);
        fileType = getIntent().getIntExtra("fileType", TypeImage);
        maxCount = getIntent().getIntExtra("maxCount", 0);

        picture_select_rec = findViewById(R.id.picture_select_rec);
        camera = new CameraManager(this);
        camera.setCameraCallback(this);

        initView();
        initData();
    }

    private void initView() {
        picture_select_rec.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new PictureSelectAdapter(fileType, maxCount, true);
        picture_select_rec.setAdapter(adapter);
        adapter.setPictureSelectListener(this);
    }

    private void initData() {
        Permission.request(this, new PermissionCallback() {
            @Override
            public void onGranted() {
                getMediaData();
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 获取媒体数据
     */
    private void getMediaData() {
        this.lists = new FileStoreManager().queryList(fileType);
        adapter.setData(this.lists);
    }

    @Override
    public void onCamera(boolean isLimit) {
        String content = "你的选择已达上限, 不能进行";
        if (fileType == FileStore.TypeImage || fileType == FileStore.TypeGif) content += "拍照";
        else if (fileType == FileStore.TypeVideo) content += "录像";
        else if (fileType == FileStore.TypeAudio) content += "录音";
        else content += "拍摄";

        if (isLimit) {
            Dialog.instance().show(this, "标题", content, "确定", null, true, null, null);
            return;
        }

        if (fileType == FileStore.TypeImage || fileType == FileStore.TypeGif || fileType == FileStore.TypeImage + FileStore.TypeGif) {
            Permission.request(this, new PermissionCallback() {
                @Override
                public void onGranted() {
                    camera.show();
                }
            }, Manifest.permission.CAMERA);
        } else if (fileType == FileStore.TypeVideo) {
            // TODO 录像
//            picture_select_camera_text.setImageResource(R.mipmap.picture_select_text_record_video);
        } else if (fileType == FileStore.TypeAudio) {
            // TODO 录音
//            picture_select_camera_text.setImageResource(R.mipmap.picture_select_text_record_audio);
        } else {
            // TODO 拍照或录像
//            picture_select_camera_text.setImageResource(R.mipmap.picture_select_text_record_image_or_video);
        }
    }

    @Override
    public void onSelect(boolean isSingle, FileBean file) {
        Log.e(TAG, "选中的文件: " + isSingle + " : " + file);
    }

    @Override
    public void onShow(FileBean file, int index, List<FileBean> files) {
        Log.e(TAG, "显示的文件: " + file + " : " + index + " : " + files);
    }

    private static final String TAG = PictureSelectActivity.class.getSimpleName();

    @Override
    public void onCameraCallback(String s) {
        File file = new File(s);
        Pair<Integer, Integer> size = fileManager.getImageWidthHeight(s);
        ImageFileBean fileBean = new ImageFileBean(0, file.getName(), "image/jpeg", Uri.fromFile(file), file.getAbsolutePath(), -1, "Camera", file.length(), System.currentTimeMillis() / 1000, size.first, size.second);
        fileBean.isChecked = true;
        lists.add(0, fileBean);
        // TODO 添加拍照数据
        Log.e(TAG, "" + fileBean);
        adapter.setData(this.lists);
        adapter.notifyDataSetChanged();
    }
}
