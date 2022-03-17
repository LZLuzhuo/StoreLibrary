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
 * Description: 音频文件
 * @Author: Luzhuo
 * @Creation Date: 2021/11/13 11:38
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class AudioFileBean extends FileBean {

    /**
     * 音频的时长
     */
    public int duration;

    public AudioFileBean(long id, String fileName, String mimeType, Uri uriPath, String urlPath, long bucketId, String bucketName, long size, long addedDate, int duration) {
        super(id, fileName, mimeType, uriPath, urlPath, bucketId, bucketName, size, addedDate);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "AudioFileBean{" +
                "duration=" + duration +
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

    public static final Creator<AudioFileBean> CREATOR = new Creator<AudioFileBean>() {
        @Override
        public AudioFileBean createFromParcel(Parcel source) {
            return new AudioFileBean(source);
        }

        @Override
        public AudioFileBean[] newArray(int size) {
            return new AudioFileBean[size];
        }
    };

    protected AudioFileBean(Parcel in) {
        super(in);
        duration = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(duration);
    }
}
