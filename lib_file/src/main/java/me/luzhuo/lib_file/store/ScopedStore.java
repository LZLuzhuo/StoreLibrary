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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.luzhuo.lib_file.FileManager;
import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_file.bean.VideoFileBean;
import me.luzhuo.lib_file.enums.FileType;
import me.luzhuo.lib_file.enums.MIMETypes;

/**
 * Description: 沙盘存储
 * @Author: Luzhuo
 * @Creation Date: 2021/11/13 11:43
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
@RequiresApi(api = Build.VERSION_CODES.Q)
public class ScopedStore implements FileStore {
    private FileManager file = new FileManager();

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
        final String filePath = fileType.directory + file.checkFolderName(folder);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, mimeType);
        values.put(MediaStore.Downloads.RELATIVE_PATH, filePath);

        try {

            ContentResolver contentResolver = context.getContentResolver();
            Uri imageUrl = contentResolver.insert(fileType.uri, values);

            file.streamIn2Out(inputStream, contentResolver.openOutputStream(imageUrl));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FileBean> queryList(Context context, int type) {
        // 选择条件
        String selection = getSelection(type);
        return queryList(context, selection);
    }

    public static String getSelection(int type) {
        // 非Gif文件
        String noGif = "mime_type!='image/gif'";
        String isGif = "mime_type='image/gif'";
        // 是媒体文件
        String isMedia = "mime_type!='image/*' AND mime_type!='video/*' AND mime_type!='audio/*'";
        // 是有效的文件
        String isEffectiveFile = "1024 <= _size AND _size <= 9223372036854775807";
        String imageMediaType = "media_type=1";
        String videoMediaType = "media_type=3";
        String audioMediaType = "media_type=2";

        StringBuilder sb = new StringBuilder()
                .append(isMedia)
                .append(" AND ")
                .append(isEffectiveFile);

        switch (type) {
            case TypeImage:
                sb
                        .append(" AND ")
                        .append(noGif)
                        .append(" AND ")
                        .append(imageMediaType);
                break;
            case TypeGif:
                sb
                        .append(" AND ")
                        .append(isGif)
                        .append(" AND ")
                        .append(imageMediaType);
                break;
            case TypeVideo:
                sb
                        .append(" AND ")
                        .append(noGif)
                        .append(" AND ")
                        .append(videoMediaType);
                break;
            case TypeAudio:
                sb
                        .append(" AND ")
                        .append(noGif)
                        .append(" AND ")
                        .append(audioMediaType);
                break;
            case TypeImage | TypeGif:
                sb
                        .append(" AND ")
                        .append(imageMediaType);
                break;
            case TypeImage | TypeVideo:
                sb
                        .append(" AND ")
                        .append(noGif)
                        .append(" AND ")
                        .append(imageMediaType)
                        .append(" OR ")
                        .append(videoMediaType);
                break;
            case TypeImage | TypeAudio:
                sb
                        .append(" AND ")
                        .append(noGif)
                        .append(" AND ")
                        .append(imageMediaType)
                        .append(" OR ")
                        .append(audioMediaType);
                break;
            case TypeGif | TypeVideo:
                sb
                        .append(" AND ")
                        .append(isGif)
                        .append(" AND ")
                        .append(imageMediaType)
                        .append(" OR ")
                        .append(videoMediaType);
                break;
            case TypeGif | TypeAudio:
                sb
                        .append(" AND ")
                        .append(isGif)
                        .append(" AND ")
                        .append(imageMediaType)
                        .append(" OR ")
                        .append(audioMediaType);
                break;
            case TypeVideo | TypeAudio:
                sb
                        .append(" AND ")
                        .append(noGif)
                        .append(" AND ")
                        .append(videoMediaType)
                        .append(" OR ")
                        .append(audioMediaType);
                break;
            case TypeImage | TypeGif | TypeVideo:
                sb
                        .append(" AND ")
                        .append(imageMediaType)
                        .append(" OR ")
                        .append(videoMediaType);
                break;
            case TypeImage | TypeGif | TypeAudio:
                sb
                        .append(" AND ")
                        .append(imageMediaType)
                        .append(" OR ")
                        .append(audioMediaType);
                break;
            case TypeImage | TypeVideo | TypeAudio:
                sb
                        .append(" AND ")
                        .append(noGif)
                        .append(" AND ")
                        .append(imageMediaType)
                        .append(" OR ")
                        .append(videoMediaType)
                        .append(" OR ")
                        .append(audioMediaType);
                break;
            case TypeGif | TypeVideo | TypeAudio:
                sb
                        .append(" AND ")
                        .append(isGif)
                        .append(" AND ")
                        .append(imageMediaType)
                        .append(" OR ")
                        .append(videoMediaType)
                        .append(" OR ")
                        .append(audioMediaType);
                break;
            case TypeImage | TypeGif | TypeVideo | TypeAudio:
                sb
                        .append(" AND ")
                        .append(imageMediaType)
                        .append(" OR ")
                        .append(videoMediaType)
                        .append(" OR ")
                        .append(audioMediaType);
                break;
        }

        return sb.toString();
    }

    private List<FileBean> queryList(Context context, String selection) {
        // content://media/external/file
        Uri filesUri = MediaStore.Files.getContentUri("external");

        // 字段
        String[] projection = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Downloads.DISPLAY_NAME,
                MediaStore.Downloads.MIME_TYPE,
                MediaStore.Downloads.RELATIVE_PATH, // api >= 29
                MediaStore.Downloads.SIZE,
                MediaStore.Downloads.DATE_ADDED,
                // 相册
                MediaStore.Downloads.BUCKET_ID, // api >= 29
                MediaStore.Downloads.BUCKET_DISPLAY_NAME, // api >= 29

                // 文件的真实路径, 用于Android29以下
                MediaStore.MediaColumns.DATA,

                // Video
                MediaStore.Downloads.WIDTH,
                MediaStore.Downloads.HEIGHT,
                MediaStore.Downloads.DURATION,
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
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Downloads.RELATIVE_PATH));
            Uri pathUri = ContentUris.withAppendedId(filesUri, cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)));
            long addedDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Downloads.DATE_ADDED));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Downloads.SIZE));

            long bucketId = cursor.getLong(cursor.getColumnIndex(MediaStore.Downloads.BUCKET_ID));
            String bucketName = cursor.getString(cursor.getColumnIndex(MediaStore.Downloads.BUCKET_DISPLAY_NAME));

            int width = cursor.getInt(cursor.getColumnIndex(MediaStore.Downloads.WIDTH));
            int height = cursor.getInt(cursor.getColumnIndex(MediaStore.Downloads.HEIGHT));
            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Downloads.DURATION));

            // Log.e(TAG, "id: " + id); // 2733
            // Log.e(TAG, "name: " + name); // IMG_20210709_151637(2).jpg
            // Log.e(TAG, "mimeType: " + mimeType); // image/jpeg
            // Log.e(TAG, "path: " + path); // DCIM/Camera/
            // Log.e(TAG, "pathUri: " + pathUri); // content://media/external/file/2734
            // Log.e(TAG, "addedDate: " + addedDate); // 1618888365
            // Log.e(TAG, "size: " + size); // 2249822

            // Log.e(TAG, "bucketId: " + bucketId); // -1313584517
            // Log.e(TAG, "bucketName: " + bucketName); // Screenshots
            // Log.e(TAG, "width: " + width); // 720
            // Log.e(TAG, "height: " + height); // 1606
            // Log.e(TAG, "duration: " + duration); // 39911

            String url = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)); // /storage/emulated/0/DCIM/Screenshots/Screenshot_20210419_105320.jpg
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
        // Log.e(TAG, "getCount: " + cursor.getCount()); // 368
        cursor.close();
        // 所有类型: 图片 + 视频 + 音频
        return files;
    }
}
