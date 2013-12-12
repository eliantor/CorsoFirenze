package me.aktor.course.droid.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eto on 11/12/13.
 */
public class Note implements Parcelable {
    private String mTitle;
    private String mContent;
    private long mTimeStamp;
    private long mId;

    public Note(){}

    Note(Parcel source) {
        mTitle = source.readString();
        mContent= source.readString();
        mTimeStamp = source.readLong();
        mId = source.readLong();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mContent);
        dest.writeLong(mTimeStamp);
        dest.writeLong(mId);
    }

    public static final Creator<Note> CREATOR =
            new Creator<Note>(){
                @Override
                public Note createFromParcel(Parcel source) {
                    return new Note(source);
                }

                @Override
                public Note[] newArray(int size) {
                    return new Note[size];
                }
            };
}
