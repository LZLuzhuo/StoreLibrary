package me.luzhuo.storedemo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_permission.Permission;
import me.luzhuo.lib_permission.PermissionCallback;

import static me.luzhuo.lib_file.store.FileStore.TypeAudio;
import static me.luzhuo.lib_file.store.FileStore.TypeGif;
import static me.luzhuo.lib_file.store.FileStore.TypeImage;
import static me.luzhuo.lib_file.store.FileStore.TypeVideo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startActivity(new Intent(this, SecondActivity.class));

//        for (String volumeName : MediaStore.getExternalVolumeNames(this)) {
//            Uri uri = MediaStore.Audio.Media.getContentUri(volumeName);
//        }

        Permission.request(this, new PermissionCallback() {
            @Override
            public void onGranted() {

            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        /*File file = new File(Environment.getExternalStorageState() + "ss.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    final ActivityResultLauncher<Intent> startActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult it) {
            if (it.getResultCode() == RESULT_OK) {
                if (it.getData() == null) return;
                final ArrayList<FileBean> result = it.getData().getParcelableArrayListExtra("result");
                Log.e(TAG, "" + result);
            }
        }
    });

    public void createFolder(View view) {
        final String path = Environment.DIRECTORY_DOWNLOADS + "/David";

        Uri uri = MediaStore.Files.getContentUri("external");
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.RELATIVE_PATH, path); // 文件夹
        values.put(MediaStore.Downloads.DISPLAY_NAME, path); // 文件名(带后缀)
        values.put(MediaStore.Downloads.TITLE, path); // 文件信息里的名字 (不带后缀)

        Uri resultUri = contentResolver.insert(uri, values);
        if(resultUri != null) Log.e(TAG, "创建成功");
        else Log.e(TAG, "创建失败");
    }

    public void createFile(View view) throws IOException {

//        InputStream inputStream = getAssets().open("music.flac");
//        boolean b = new FileStoreManager(this).saveFile("music.flac", "", inputStream);
//        Log.e(TAG, "" + b);

        // lib_file 的使用
//        List<FileBean> lists = new FileStoreManager().queryList(TypeVideo | TypeAudio | TypeImage | TypeGif);
//        for (FileBean list : lists) {
//            Log.e(TAG, "" + list);
//            if (list.fileName.startsWith("Screenshot_20210901_175518")) Log.e(TAG, "" + new FileManager().exists(list.uriPath));
//        }
//        Log.e(TAG, "" + lists);
        // Log.e(TAG, "" + lists.size());

        // PictureSelectActivity.start(this, /*TypeVideo | TypeGif | TypeAudio | */TypeImage, 3);
    }

    public boolean update(Uri uri, String filename) {
        if (uri == null) return false;
        if (TextUtils.isEmpty(filename)) return false;

        // 修改文件名
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, filename);

        try {
            int update = getContentResolver().update(uri, contentValues, null, null);
            Log.e(TAG, "" + update);
            return update >= 0;
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
            return false;
        }
    }

    public Uri queryUri(String filename) {
        Uri filesUri = MediaStore.Files.getContentUri("external");

        String selection = MediaStore.Downloads.DISPLAY_NAME + "=?";
        String[] args = new String[]{filename};
        String[] projection = new String[]{ MediaStore.Files.FileColumns._ID };
        Cursor cursor = getContentResolver().query(filesUri, projection, selection, args, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Uri queryUri = ContentUris.withAppendedId(filesUri, cursor.getLong(0));
                cursor.close();
                return queryUri;
            } else {
                cursor.close();
                return null;
            }
        } else {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void createFile2(View view) throws IOException {
        // content://media/external/file
        Uri filesUri = MediaStore.Files.getContentUri("external");

        // 字段
        String[] projection = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Downloads.DISPLAY_NAME,
                MediaStore.Downloads.MIME_TYPE,
                MediaStore.Downloads.RELATIVE_PATH,
                // 相册
                MediaStore.Downloads.BUCKET_ID,
                MediaStore.Downloads.BUCKET_DISPLAY_NAME,

                // 文件的真实路径, 用于Android29以下
                MediaStore.MediaColumns.DATA,
        };
        // 选择条件
        String selection = "";
        // 选择条件的参数
        String[] args = new String[]{};
        // 排序
        String sortOrder = MediaStore.Files.FileColumns._ID + " DESC";

        Cursor cursor = getContentResolver().query(filesUri, projection, selection, args, sortOrder);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Downloads.DISPLAY_NAME));
            String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Downloads.MIME_TYPE));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Downloads.RELATIVE_PATH));
            Uri pathUri = ContentUris.withAppendedId(filesUri, cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)));

            long bucketId = cursor.getLong(cursor.getColumnIndex(MediaStore.Downloads.BUCKET_ID));
            String bucketName = cursor.getString(cursor.getColumnIndex(MediaStore.Downloads.BUCKET_DISPLAY_NAME));

            Log.e(TAG, "id: " + id); // 2733
            Log.e(TAG, "name: " + name); // IMG_20210709_151637(2).jpg
            Log.e(TAG, "mimeType: " + mimeType); // image/jpeg
            Log.e(TAG, "path: " + path); // DCIM/Camera/
            Log.e(TAG, "pathUri: " + pathUri); // content://media/external/file/2734

            Log.e(TAG, "bucketId: " + bucketId); // -1313584517
            Log.e(TAG, "bucketName: " + bucketName); // Screenshots

            String url = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)); // /storage/emulated/0/DCIM/Screenshots/Screenshot_20210419_105320.jpg
            Log.e(TAG, "url: " + url);
        }
        Log.e(TAG, "getCount: " + cursor.getCount()); // 368
        // 所有类型: 图片 + 视频 + 音频
    }

    public void createImage(View view) {
        final String fileName = "images.jpg";
        final String filePath = Environment.DIRECTORY_PICTURES + "/David/"; // 文件夹会被自动创建

        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Downloads.RELATIVE_PATH, filePath);

        Uri imageUrl = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try{
            OutputStream outputStream = contentResolver.openOutputStream(imageUrl);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void queryImage(View view){
        final String fileName = "images.jpg";
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=?";
        String[] args = new String[]{fileName};
        String[] projection = new String[]{MediaStore.Images.Media._ID};
        Cursor cursor = getContentResolver().query(external, projection, selection, args, null);

        if(cursor != null && cursor.moveToFirst()){
            // 将 id 转成 URI
            Uri queryUri = ContentUris.withAppendedId(external, cursor.getLong(0));
            Log.e(TAG, "" + queryUri);
            cursor.close();
        }
    }

    public void updateImage(View view) {
        // 查询出来的图片Uri
        final Uri imageUri = Uri.parse("content://media/external/images/media/45");

        // 修改文件名
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, "这是新名字.jpg");

        getContentResolver().update(imageUri, contentValues, null, null);
    }

    public void deleteImage(View view) {
        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=?";
        String[] args = new String[]{"这是新名字3.jpg"};
//        int delete = getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, args);
        int delete = getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, args);
        Log.e(TAG, "" + delete);
    }
}