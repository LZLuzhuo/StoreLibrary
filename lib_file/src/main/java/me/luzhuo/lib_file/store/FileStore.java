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

import android.content.Context;

import java.io.InputStream;
import java.util.List;

import me.luzhuo.lib_file.bean.FileBean;

/**
 * Description: 文件存储;
 *   如果 api >= 29, 则使用沙盒存储.
 *   如果 api <= 28, 则使用File存储.
 * @Author: Luzhuo
 * @Creation Date: 2021/11/13 11:42
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public interface FileStore {

    /**
     * 存储文件到 Download
     *
     * 案例代码:<p>
     * InputStream inputStream = getAssets().open("image.jpg");
     * saveFile("images.jpg", "David", "image/jpeg", inputStream);
     *
     * @param fileName 文件名 (文件后缀, 可带可不带)
     * @param folder 文件夹名, 可为null
     * @param mimeType 文件类型
     * @param inputStream 源文件输入流
     */
    public boolean saveFile(Context context, String fileName, String folder, String mimeType, InputStream inputStream);

    /**
     * 存储文件到 Picture
     */
    public boolean saveImage(Context context, String fileName, String folder, String mimeType, InputStream inputStream);

    /**
     * 存储文件到 Movies
     */
    public boolean saveVideo(Context context, String fileName, String folder, String mimeType, InputStream inputStream);

    /**
     * 存储文件到 Music
     */
    public boolean saveAudio(Context context, String fileName, String folder, String mimeType, InputStream inputStream);

    public final int TypeImage =       1 << 1;
    public final int TypeGif =         1 << 2;
    public final int TypeVideo =       1 << 3;
    public final int TypeAudio =       1 << 4;

    /**
     * 根据type检索文件
     * @param type 类型
     */
    public List<FileBean> queryList(Context context, int type);

}
