package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class LessonResult {

    private String mMessage;

    private int mStatus;

    private List<LessonInfo> mLessonInfoList = new ArrayList<>();

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public List<LessonInfo> getmLessonInfoList() {
        return mLessonInfoList;
    }

    public void setmLessonInfoList(List<LessonInfo> mLessonInfoList) {
        this.mLessonInfoList = mLessonInfoList;
    }

}
