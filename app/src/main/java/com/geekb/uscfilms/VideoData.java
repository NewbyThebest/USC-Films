package com.geekb.uscfilms;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoData implements Parcelable {
    public String id;
    public String title;
    public String poster_path;
    public String profile_path;
    public String backdrop_path;
    public String name;
    public String Media_type;
    public String Rating;
    public String release_date;

    public VideoData() {

    }


    public VideoData(Parcel in) {
        id = in.readString();
        title = in.readString();
        poster_path = in.readString();
        profile_path = in.readString();
        backdrop_path = in.readString();
        name = in.readString();
        Media_type = in.readString();
        Rating = in.readString();
        release_date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(profile_path);
        dest.writeString(backdrop_path);
        dest.writeString(name);
        dest.writeString(Media_type);
        dest.writeString(Rating);
        dest.writeString(release_date);
    }

    /**
     * 反序列化
     */
    public static final Creator<VideoData> CREATOR = new Creator<VideoData>() {
        @Override
        public VideoData createFromParcel(Parcel in) {
            return new VideoData(in);
        }

        @Override
        public VideoData[] newArray(int size) {
            return new VideoData[size];
        }
    };
}