package me.luzhuo.lib_picture_select;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.bean.ImageFileBean;
import me.luzhuo.lib_file.bean.VideoFileBean;
import me.luzhuo.lib_file.enums.FileType;

public class PictureSelectUtils {
    private static final String TAG = PictureSelectUtils.class.getSimpleName();

    public static void queryByFilePath(Context context/*, long _id*/) {
        Uri filesUri = MediaStore.Files.getContentUri("external");
        Cursor cursor = context.getContentResolver().query(filesUri, null, "media_type=2 AND _id=8573", null, null);
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

             Log.e(TAG, "id: " + id); // 359538
             Log.e(TAG, "name: " + name); // music.flac
             Log.e(TAG, "mimeType: " + mimeType); // audio/flac
             Log.e(TAG, "pathUri: " + pathUri); // content://media/external/file/359538
             Log.e(TAG, "addedDate: " + addedDate);
             Log.e(TAG, "size: " + size);

             Log.e(TAG, "bucketId: " + bucketId); // 82896267
             Log.e(TAG, "bucketName: " + bucketName); // Music
             Log.e(TAG, "width: " + width);
             Log.e(TAG, "height: " + height);
             Log.e(TAG, "duration: " + duration);

            String url = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)); // /storage/emulated/0/Music/music.flac
             Log.e(TAG, "url: " + url);

            /*if (FileType.getFileType(mimeType) == FileType.Video) {
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
            }*/
        }
        cursor.close();
    }

    /**
     * 从录制的Uri获取音频数据
     * @param context Context
     * @param uri Uri
     * @return AudioFileBean or null
     */
    @Nullable
    public static AudioFileBean queryAudioByUri(@Nullable Context context, @Nullable Uri uri) {
        if (context == null || uri == null) return null;

        Uri filesUri = MediaStore.Files.getContentUri("external");
        long _id = ContentUris.parseId(uri);
        Cursor cursor = context.getContentResolver().query(filesUri, null, "media_type=2 AND _id=?", new String[]{String.valueOf(_id)}, null);
        if (cursor == null) return null;
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

            String url = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)); // /storage/emulated/0/Music/music.flac

            if (FileType.getFileType(mimeType) == FileType.Audio) {
                AudioFileBean fileBean = new AudioFileBean(id, name, mimeType, pathUri, url, bucketId, bucketName, size, addedDate, duration);
                return fileBean;
            } else {
                return null;
            }
        }
        cursor.close();
        return null;
    }
}
