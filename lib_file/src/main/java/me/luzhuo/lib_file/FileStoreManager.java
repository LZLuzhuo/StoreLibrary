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
package me.luzhuo.lib_file;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;
import me.luzhuo.lib_file.bean.FileBean;
import me.luzhuo.lib_file.enums.MIMETypes;
import me.luzhuo.lib_file.store.DefaultFileStore;
import me.luzhuo.lib_file.store.FileStore;
import me.luzhuo.lib_file.store.ScopedStore;

/**
 * Description: 文件存储管理
 * 专门处理文件的 存储 和 读取 的操作
 * @Author: Luzhuo
 * @Creation Date: 2021/11/13 11:44
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class FileStoreManager {
    private FileStore fileStore;
    private Context context;
    private MIMETypes mimeTypes;

    public FileStoreManager() {
        this.context = CoreBaseApplication.appContext;
        this.mimeTypes = new MIMETypes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // >= Android9.0
            fileStore = new ScopedStore();
        } else {
            fileStore = new DefaultFileStore();
        }
    }

    /**
     * 存储文件到 Download
     */
    public boolean saveFile(@NonNull String fileName, @NonNull String folder, @NonNull InputStream inputStream) {
        return fileStore.saveFile(context, fileName, folder, mimeTypes.getMIMEType(fileName), inputStream);
    }

    /**
     * 存储文件到 Picture
     */
    public boolean saveImage(@NonNull String fileName, @NonNull String folder, @NonNull InputStream inputStream) {
        String mimeType = mimeTypes.getMIMEType(fileName);
        if (TextUtils.isEmpty(mimeType)) mimeType = mimeTypes.getMIMEType(".png");

        return fileStore.saveImage(context, fileName, folder, mimeType, inputStream);
    }

    /**
     * 存储文件到 Movies
     */
    public boolean saveVideo(@NonNull String fileName, @NonNull String folder, @NonNull InputStream inputStream) {
        String mimeType = mimeTypes.getMIMEType(fileName);
        if (TextUtils.isEmpty(mimeType)) mimeType = mimeTypes.getMIMEType(".mp4");

        return fileStore.saveVideo(context, fileName, folder, mimeType, inputStream);
    }

    /**
     * 存储文件到 Music
     */
    public boolean saveAudio(@NonNull String fileName, @NonNull String folder, @NonNull InputStream inputStream) {
        String mimeType = mimeTypes.getMIMEType(fileName);
        if (TextUtils.isEmpty(mimeType)) mimeType = mimeTypes.getMIMEType(".mp3");

        return fileStore.saveAudio(context, fileName, folder, mimeType, inputStream);
    }

    /**
     * 根据文件类型, 获取文件列表
     * @param type 文件类型 {@link FileStore.TypeFileStore}
     * @return
     */
    @NonNull
    public List<FileBean> queryList(@FileStore.TypeFileStore int type) {
        return fileStore.queryList(context, type);
    }

}
