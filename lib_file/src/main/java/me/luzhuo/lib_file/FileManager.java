/* Copyright 2020 Luzhuo. All rights reserved.
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Pair;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.UUID;

import me.luzhuo.lib_core.app.base.CoreBaseApplication;
import me.luzhuo.lib_core.data.DataCheck;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Description: 文件管理
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/7/29 22:42
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class FileManager {
    private DecimalFormat format = new DecimalFormat("0.##");
    private Context context;

    {
        format.setRoundingMode(RoundingMode.HALF_UP);
    }

    public FileManager(Context context) {
        this.context = context;
    }

    public FileManager() {
        this(CoreBaseApplication.appContext);
    }

    /**
     * 从 输入流 写入到文件
     * @param stream inputStream
     * @param filePath  local file path
     * @throws IOException
     */
    public void Stream2File(InputStream stream, String filePath) throws IOException {

        File localFile = new File(filePath);
        if(!localFile.getParentFile().exists()) localFile.getParentFile().mkdirs();
        if(!localFile.exists()) localFile.createNewFile();

        BufferedInputStream bis = new BufferedInputStream(stream);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(localFile));

        byte[] bys = new byte[1024 * 10];
        int len = 0;
        while((len = bis.read(bys)) != -1){
            bos.write(bys,0, len);
            bos.flush();
        }
        bis.close();
        bos.close();
    }

    /**
     * 该方式将图片存到本地, 大小将是原来图片的十几倍
     * @param bitmap Bitmap
     * @param filePath save file path
     * @throws IOException
     */
    public void Bitmap2PNGFile(Bitmap bitmap, String filePath) throws IOException {
        File localFile = new File(filePath);
        if (!localFile.getParentFile().exists()) localFile.getParentFile().mkdirs();
        if (!localFile.exists()) localFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(localFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 该方式将图片存到本地, 大小与原来图片差不多大
     * @param bitmap Bitmap
     * @param filePath save file path
     * @throws IOException
     */
    public void Bitmap2JPGFile(Bitmap bitmap, String filePath) throws IOException {
        File localFile = new File(filePath);
        if (!localFile.getParentFile().exists()) {
            localFile.getParentFile().mkdirs();
        }

        if (!localFile.exists()) {
            localFile.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(localFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 检查文件地址, 用于适配 Android10+ 的分区存储.
     * 注意: 该方法是同步的.
     * 1. 如果是普通的文件路径, 则则返回文件路径
     * 2. 如果是content类型的uri路径, 则先缓存到外置cache, 然后再返回cache路径
     * @param path 文件路径, 可以为filepath, 也可以为content
     * @return 检测后的文件路径
     */
    public String checkFilePath(String path) {
        if(TextUtils.isEmpty(path)) return null;

        if (isUriForFile(path)) {
            try {
                Uri pathUri = Uri.parse(path);
                String savePath = context.getExternalCacheDir() + File.separator + UUID.randomUUID().toString().replace("-", "");
                InputStream inputStream = context.getContentResolver().openInputStream(pathUri);

                Stream2File(inputStream, savePath);

                return savePath;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return path;
        }
    }

    /**
     * 获取缓存目录, 该目录的文件可以被用户清除
     * 读写该目录的文件不需要权限
     * @return 返回外部Cache目录, 或者内部的Cache目录
     */
    public File getCacheDirectory() {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) appCacheDir = context.getExternalCacheDir();
        if (appCacheDir == null) appCacheDir = context.getCacheDir();
        return appCacheDir;
    }

    /**
     * 获取File目录, 该目录的文件不可被用户清除
     * 读写该目录的文件不需要权限
     * @return 返回外部File目录, 或者内部的File目录
     */
    public File getFileDirectory() {
        File appFileDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) appFileDir = context.getExternalFilesDir("exFiles");
        if (appFileDir == null) appFileDir = context.getFilesDir();
        return appFileDir;
    }

    /**
     * 将 Uri 转成 Bitmap
     * @param uri content://com.android.contacts/display_photo/1
     * @return Bitmap / null
     */
    public Bitmap uri2Bitmap(String uri) {
        if (context == null || TextUtils.isEmpty(uri)) return null;

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(uri));
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取缓存文件的大小
     */
    public long getCacheSize() {
        long folderSize = getFolderSize(context.getCacheDir());
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            folderSize += getFolderSize(context.getExternalCacheDir());
        }
        return folderSize;
    }

    /**
     * 清理缓存
     */
    public void clearCache() {
        clearFolder(context.getCacheDir());
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            clearFolder(context.getExternalCacheDir());
        }
    }

    /**
     * 获取文件夹内所有文件的大小, 遇到文件夹则递归
     * @param folder 文件夹
     * @return 文件夹内文件的大小
     */
    public long getFolderSize(File folder) {
        long size = 0;
        try {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) size += getFolderSize(file);
                else size += file.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 清空文件夹
     * @param folder 文件夹
     */
    public boolean clearFolder(File folder) {
        if (folder == null && !folder.exists()) return false;

        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                boolean isDelete = clearFolder(file);
                if (!isDelete) return false;
            }
        }
        return folder.delete();
    }

    /**
     * 获取格式化之后的文件大小
     */
    public String getFormatSize(double size) {
        double KB = size / 1024;
        if (KB < 1) return String.format("%dB", (int) size);
        double MB = KB / 1024;
        if (MB < 1) return String.format("%sKB", format.format(KB));
        double GB = MB / 1024;
        if (GB < 1) return String.format("%sMB", format.format(MB));
        double TB = GB / 1024;
        if (TB < 1) return String.format("%sGB", format.format(GB));
        double PB = TB / 1024;
        if (PB < 1) return String.format("%sTB", format.format(TB));
        double EB = PB / 1024;
        if (EB < 1) return String.format("%sPB", format.format(PB));
        double ZB = EB / 1024;
        if (ZB < 1) return String.format("%sEB", format.format(EB));
        double YB = ZB / 1024;
        if (YB < 1) return String.format("%sZB", format.format(ZB));
        double BB = YB / 1024;
        if (BB < 1) return String.format("%sYB", format.format(YB));
        double NB = BB / 1024;
        if (NB < 1) return String.format("%sBB", format.format(BB));
        double DB = NB / 1024;
        if (DB < 1) return String.format("%sNB", format.format(NB));
        double CB = DB / 1024;
        if (CB < 1) return String.format("%sDB", format.format(DB));
        return String.format("%sCB", format.format(CB));
    }

    /**
     * 检查文件夹名
     *
     * 效果: <p>
     * "Folder" -> "/Folder"
     * "/Folder" -> "/Folder"
     * "Folder/" -> "/Folder"
     * "/Folder/" -> "/Folder"
     */
    public String checkFolderName(String folder) {
        if (folder == null) return "";
        if (TextUtils.isEmpty(folder)) return folder;

        if (!folder.startsWith("/")) folder = "/" + folder;
        if (folder.endsWith("/")) folder = folder.substring(0, folder.length() - 1);
        return folder;
    }

    /**
     * 将 InputStream 输入流的数据输出到 OutputStream 输出流
     *
     * 案例代码: <p>
     * InputStream inputStream = getAssets().open("image.jpg");
     * File file = new File(getExternalCacheDir(), "asdf.jpg");
     * OutputStream outputStream = new FileOutputStream(file);
     * StreamIn2Out(inputStream, outputStream);
     */
    public void streamIn2Out(InputStream is, OutputStream os) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);

            byte[] bytes = new byte[1024 * 10];
            int len = 0;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                bos.flush();
            }

        } finally {
            if (bos != null) bos.close();
            if (bis != null) bis.close();
        }
    }

    public boolean exists(String filePath) {
        if (TextUtils.isEmpty(filePath)) return false;
        try {
            if (isUriForFile(filePath)) return exists(Uri.parse(filePath));
            else return exists(new File(filePath));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean exists(File file) {
        if (file == null) return false;
        return file.exists();
    }

    public boolean exists(Uri file) {
        if (file == null) return false;
        try {
            context.getContentResolver().openInputStream(file).close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取图片的宽高
     * @param url Uri 或者 file path
     * @return Pair<Width, Height>
     */
    @SuppressLint("NewApi")
    public Pair<Integer, Integer> getImageWidthHeight(String url) {
        try {
            InputStream inputStream;
            ExifInterface exifInterface;

            if (isUriForFile(url)) {
                inputStream = context.getContentResolver().openInputStream(Uri.parse(url));
                exifInterface = new ExifInterface(inputStream);
            } else {
                exifInterface = new ExifInterface(url);
            }
            return new Pair<Integer, Integer>(
                    exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL),
                    exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL)
            );
        } catch (Exception e) {
            return new Pair<Integer, Integer>(0, 0);
        }
    }

    public Pair<Integer, Integer> getImageWidthHeight(Uri uri) {
        return this.getImageWidthHeight(uri.toString());
    }

    /**
     * 获取视频文件的宽高
     * @param url Uri 或者 file path
     * @return Pair<Pair<Width, Height>, duration>
     */
    public Pair<Pair<Integer, Integer>, Long> getVideoWidthHeight(String url) {
        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (isUriForFile(url)) retriever.setDataSource(context, Uri.parse(url));
            else retriever.setDataSource(url);

            String orientation;
            int width, height;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) orientation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            else orientation = "0";
            if ("90".equals(orientation) || "270".equals(orientation)) {
                height = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                width = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            } else {
                width = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                height = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            }
            long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            return new Pair<Pair<Integer, Integer>, Long>(new Pair<Integer, Integer>(width, height), duration);
        } catch (Exception e) {
            return new Pair<Pair<Integer, Integer>, Long>(new Pair<Integer, Integer>(0, 0), 0L);
        } finally {
            retriever.release();
        }
    }

    public Pair<Pair<Integer, Integer>, Long> getVideoWidthHeight(Uri uri) {
        return this.getVideoWidthHeight(uri.toString());
    }

    /**
     * 获取音频文件的时长, 单位毫秒
     * @param url Uri 或者 file path
     * @return 播放时长
     */
    public long getAudioDuration(String url) {
        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (isUriForFile(url)) retriever.setDataSource(context, Uri.parse(url));
            else retriever.setDataSource(url);

            return Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            return 0L;
        } finally {
            retriever.release();
        }
    }

    public long getAudioDuration(Uri uri) {
        return this.getAudioDuration(uri.toString());
    }

    /**
     * 判断一个文件路径, 是否是Uri路径
     * @param url 文件路径
     * @return true 是Uri路径; false, 普通路径 或者 url为空
     */
    public boolean isUriForFile(String url) {
        if (TextUtils.isEmpty(url)) return false;
        return url.startsWith("content://");
    }
}
