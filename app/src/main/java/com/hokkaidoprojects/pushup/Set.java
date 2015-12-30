package com.hokkaidoprojects.pushup;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by samfr on 21/11/2015.
 */
public class Set implements Serializable, Parcelable {

    private int mLevel;
    private int mWeek;
    private int mDay;
    private int mRound;
    private int mPushups;

    public Set() {
        mLevel = 0;
        mWeek = 0;
        mDay = 0;
        mRound = 0;
        mPushups = 0;
    }

    public Set(int level, int week, int day, int round,int pushups) {
        mLevel = level;
        mWeek = week;
        mDay = day;
        mRound = round;
        mPushups = pushups;
    }

    protected Set(Parcel in) {
        mLevel = in.readInt();
        mWeek = in.readInt();
        mDay = in.readInt();
        mRound = in.readInt();
        mPushups = in.readInt();
    }

    public static final Creator<Set> CREATOR = new Creator<Set>() {
        @Override
        public Set createFromParcel(Parcel in) {
            return new Set(in);
        }

        @Override
        public Set[] newArray(int size) {
            return new Set[size];
        }
    };

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    public int getPushups() {
        return mPushups;
    }

    public void setPushups(int mPushups) {
        this.mPushups = mPushups;
    }

    public int getWeek() {
        return mWeek;
    }

    public void setWeek(int mWeek) {
        this.mWeek = mWeek;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int mDay) {
        this.mDay = mDay;
    }

    public int getRound() {
        return mRound;
    }

    public void setRound(int mRound) {
        this.mRound = mRound;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mLevel);
        dest.writeInt(mWeek);
        dest.writeInt(mDay);
        dest.writeInt(mRound);
        dest.writeInt(mPushups);
    }
}
