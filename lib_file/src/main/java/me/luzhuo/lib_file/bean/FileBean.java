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
package me.luzhuo.lib_file.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Description: 供 FileStoreManager 使用
 * @Author: Luzhuo
 * @Creation Date: 2021/11/13 11:39
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class FileBean extends CheckableFileBean implements Parcelable, Serializable {

    /**
     * 数据库的文件id
     */
    public long id;

    /**
     * 文件名
     */
    @NonNull
    public String fileName;

    /**
     * MimeType
     */
    @NonNull
    public String mimeType;

    /**
     * Uri类型的文件路径, 一般用于API >= 29
     */
    @NonNull
    public Uri uriPath;

    /**
     * Url类型的文件路径, 一般用于API < 29
     */
    @NonNull
    public String urlPath;

    /**
     * 相册ID
     */
    @NonNull
    public long bucketId;

    /**
     * 相册名称
     */
    @NonNull
    public String bucketName;

    /**
     * 文件的大小
     */
    public long size;

    /**
     * 添加时间
     */
    public long addedDate;

    public FileBean(long id, @NonNull String fileName, @NonNull String mimeType, @NonNull Uri uriPath, @NonNull String urlPath, long bucketId, @NonNull String bucketName, long size, long addedDate) {
        this.id = id;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.uriPath = uriPath;
        this.urlPath = urlPath;
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.size = size;
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", uriPath=" + uriPath +
                ", urlPath='" + urlPath + '\'' +
                ", bucketId=" + bucketId +
                ", bucketName='" + bucketName + '\'' +
                ", size=" + size +
                ", addedDate=" + addedDate +
                ", isChecked=" + isChecked +
                '}';
    }

    public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
        @Override
        public FileBean createFromParcel(Parcel source) {
            return new FileBean(source);
        }

        @Override
        public FileBean[] newArray(int size) {
            return new FileBean[size];
        }
    };

    protected FileBean(Parcel in) {
        id = in.readLong();
        fileName = in.readString();
        mimeType = in.readString();
        uriPath = Uri.parse(in.readString());
        urlPath = in.readString();
        bucketId = in.readLong();
        bucketName = in.readString();
        size = in.readLong();
        addedDate = in.readLong();
        isOrigin = in.readInt() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(fileName);
        dest.writeString(mimeType);
        dest.writeString(uriPath.toString());
        dest.writeString(urlPath);
        dest.writeLong(bucketId);
        dest.writeString(bucketName);
        dest.writeLong(size);
        dest.writeLong(addedDate);
        dest.writeInt(isOrigin ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
