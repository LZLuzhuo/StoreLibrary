/* Copyright 2021 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_file.store;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RestrictTo;
import me.luzhuo.lib_file.FileManager;
import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_file.bean.VideoFileBean;
import me.luzhuo.lib_file.enums.FileType;

/**
 * Description: 传统的文件存储
 * @Author: Luzhuo
 * @Creation Date: 2021/11/13 11:42
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class DefaultFileStore implements FileStore {
    private FileManager fileManager = new FileManager();

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public DefaultFileStore() {}

    @Override
    public boolean saveFile(Context context, String fileName, String folder, String mimeType, InputStream inputStream) {
        return save(context, fileName, folder, mimeType, FileType.Download, inputStream);
    }

    @Override
    public boolean saveImage(Context context, String fileName, String folder, String mimeType, InputStream inputStream) {
        return save(context, fileName, folder, mimeType, FileType.Image, inputStream);
    }

    @Override
    public boolean saveVideo(Context context, String fileName, String folder, String mimeType, InputStream inputStream) {
        return save(context, fileName, folder, mimeType, FileType.Video, inputStream);
    }

    @Override
    public boolean saveAudio(Context context, String fileName, String folder, String mimeType, InputStream inputStream) {
        return save(context, fileName, folder, mimeType, FileType.Audio, inputStream);
    }

    /**
     * 保存任何类型的文件
     * @param fileName 文件名 (可带后缀, 可不带后缀)
     * @param folder 文件夹名
     * @param mimeType 文件mime类型
     * @param fileType 文件类型
     * @param inputStream 文件输入流
     */
    private boolean save(Context context, String fileName, String folder, String mimeType, FileType fileType, InputStream inputStream) {
        final File filePath = new File(Environment.getExternalStorageDirectory() + File.separator + fileType.directory + fileManager.checkFolderName(folder), fileName);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.ImageColumns.MIME_TYPE, mimeType);
        values.put(MediaStore.Downloads.DATA, filePath.getAbsolutePath());

        try {
            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.insert(fileType.uri, values);

            File parent = filePath.getParentFile();
            if (!parent.exists()) parent.mkdirs();

            fileManager.streamIn2Out(inputStream, new FileOutputStream(filePath));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FileBean> queryList(Context context, int type) {
        String selection = getSelection(type);
        return queryList(context, selection);
    }

    @SuppressLint("NewApi")
    public static String getSelection(int type) {
        return ScopedStore.getSelection(type);
    }

    private List<FileBean> queryList(Context context, String selection) {
        // content://media/external/file
        Uri filesUri = MediaStore.Files.getContentUri("external");

        // 字段
        String[] projection = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Downloads.DISPLAY_NAME,
                MediaStore.Downloads.MIME_TYPE,
                MediaStore.Downloads.SIZE,
                MediaStore.Downloads.DATE_ADDED,
                // 相册
                "bucket_id",
                "bucket_display_name",

                MediaStore.MediaColumns.DATA,

                // Video
                MediaStore.Downloads.WIDTH,
                MediaStore.Downloads.HEIGHT,
                "duration",
        };

        // 选择条件的参数
        String[] args = new String[]{};
        // 排序
        String sortOrder = MediaStore.Files.FileColumns._ID + " DESC";

        List<FileBean> files = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(filesUri, projection, selection, args, sortOrder);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Downloads.DISPLAY_NAME));
            String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Downloads.MIME_TYPE));
            Uri pathUri = ContentUris.withAppendedId(filesUri, cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)));
            long addedDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Downloads.DATE_ADDED));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Downloads.SIZE));

            long bucketId = cursor.getLong(cursor.getColumnIndex("bucket_id"));
            String bucketName = cursor.getString(cursor.getColumnIndex("bucket_display_name"));

            int width = cursor.getInt(cursor.getColumnIndex(MediaStore.Downloads.WIDTH));
            int height = cursor.getInt(cursor.getColumnIndex(MediaStore.Downloads.HEIGHT));
            int duration = cursor.getInt(cursor.getColumnIndex("duration"));

            // Log.e(TAG, "id: " + id); // 359538
            // Log.e(TAG, "name: " + name); // music.flac
            // Log.e(TAG, "mimeType: " + mimeType); // audio/flac
            // Log.e(TAG, "pathUri: " + pathUri); // content://media/external/file/359538
            // Log.e(TAG, "addedDate: " + addedDate);
            // Log.e(TAG, "size: " + size);

            // Log.e(TAG, "bucketId: " + bucketId); // 82896267
            // Log.e(TAG, "bucketName: " + bucketName); // Music
            // Log.e(TAG, "width: " + width);
            // Log.e(TAG, "height: " + height);
            // Log.e(TAG, "duration: " + duration);

            String url = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)); // /storage/emulated/0/Music/music.flac
            // Log.e(TAG, "url: " + url);

            if (FileType.getFileType(mimeType) == FileType.Video) {
                VideoFileBean fileBean = new VideoFileBean(id, name, mimeType, pathUri, url, bucketId, bucketName, size, addedDate, width, height, duration);
                files.add(fileBean);
            } else if (FileType.getFileType(mimeType) == FileType.Audio) {
                AudioFileBean fileBean = new AudioFileBean(id, name, mimeType, pathUri, url, bucketId, bucketName, size, addedDate, duration);
                files.add(fileBean);
            } else if (FileType.getFileType(mimeType) == FileType.Image) {
                ImageFileBean fileBean = new ImageFileBean(id, name, mimeType, pathUri, url, bucketId, bucketName, size, addedDate, width, height);
                files.add(fileBean);
            } else {
                FileBean fileBean = new FileBean(id, name, mimeType, pathUri, url, bucketId, bucketName, size, addedDate);
                files.add(fileBean);
            }
        }
        // Log.e(TAG, "getCount: " + cursor.getCount());
        cursor.close();
        // 所有类型: 图片 + 视频 + 音频
        return files;
    }
}
