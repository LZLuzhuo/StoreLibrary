package me.luzhuo.lib_picture_select;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import me.luzhuo.lib_file.bean.AudioFileBean;
import me.luzhuo.lib_file.enums.FileType;

public class PictureSelectUtils {

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
