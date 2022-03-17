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

/**
 * Description: 图片文件类型
 * @Author: Luzhuo
 * @Creation Date: 2021/11/13 11:40
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ImageFileBean extends FileBean {

    /**
     * 图片的宽度
     */
    public int width;

    /**
     * 图片的高度
     */
    public int height;

    public ImageFileBean(long id, String fileName, String mimeType, Uri uriPath, String urlPath, long bucketId, String bucketName, long size, long addedDate, int width, int height) {
        super(id, fileName, mimeType, uriPath, urlPath, bucketId, bucketName, size, addedDate);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "ImageFileBean{" +
                "width=" + width +
                ", height=" + height +
                ", id=" + id +
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

    public static final Creator<ImageFileBean> CREATOR = new Creator<ImageFileBean>() {
        @Override
        public ImageFileBean createFromParcel(Parcel source) {
            return new ImageFileBean(source);
        }

        @Override
        public ImageFileBean[] newArray(int size) {
            return new ImageFileBean[size];
        }
    };

    protected ImageFileBean(Parcel in) {
        super(in);
        width = in.readInt();
        height = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}
