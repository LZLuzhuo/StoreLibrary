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
package me.luzhuo.lib_file.enums;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.Serializable;

/**
 * Description: 文件类型
 * @Author: Luzhuo
 * @Creation Date: 2021/11/13 11:41
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public enum FileType {
    /**
     * 音频文件
     */
    Audio(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            Environment.DIRECTORY_MUSIC
    ),
    /**
     * 视频文件
     */
    Video(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            Environment.DIRECTORY_MOVIES
    ),
    /**
     * 图片文件
     */
    Image(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            Environment.DIRECTORY_PICTURES
    ),
    /**
     * 文档文件
     */
    Document(
            Document_EXTERNAL_CONTENT_URI(),
            Environment_DIRECTORY_DOCUMENTS()
    ),
    /**
     * 下载文件
     */
    Download(
            Downloads_EXTERNAL_CONTENT_URI(),
            Environment_DIRECTORY_DOWNLOADS()
    );

    /**
     * api >= 29 使用Uri
     */
    public Uri uri;
    /**
     * api <= 28 使用文件目录
     */
    public String directory;
    private FileType(Uri uri, String directory) {
        this.uri = uri;
        this.directory = directory;
    }

    private static Uri Document_EXTERNAL_CONTENT_URI() {
        // 系统未提供 Document 的 Uri
        return null;
    }

    private static Uri Downloads_EXTERNAL_CONTENT_URI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        } else return MediaStore.Files.getContentUri("external");
    }

    private static String Environment_DIRECTORY_DOCUMENTS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Environment.DIRECTORY_DOCUMENTS;
        } else return "Documents";
    }

    private static String Environment_DIRECTORY_DOWNLOADS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return Environment.DIRECTORY_DOWNLOADS;
        } else return "Download";
    }

    public static FileType getFileType(String mimeType) {
        if (mimeType == null) return FileType.Download;

        if (mimeType.startsWith("video")) return FileType.Video;
        else if (mimeType.startsWith("audio")) return FileType.Audio;
        else if (mimeType.startsWith("image")) return FileType.Image;
        else return FileType.Download;
    }
}
