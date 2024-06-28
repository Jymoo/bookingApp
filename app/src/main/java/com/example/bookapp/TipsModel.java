package com.example.bookapp;

public class TipsModel {
    private String mImageUrl, mdesc, mlocatn, mtitle;

    public TipsModel() {
    }

    public TipsModel(String mLandImageUrl, String mLanddesc, String mLandlocatn, String mLandtitle) {
        this.mImageUrl = mLandImageUrl;
        this.mdesc = mLanddesc;
        this.mlocatn = mLandlocatn;
        this.mtitle = mLandtitle;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getMdesc() {
        return mdesc;
    }

    public void setMdesc(String mdesc) {
        this.mdesc = mdesc;
    }

    public String getMlocatn() {
        return mlocatn;
    }

    public void setMlocatn(String mlocatn) {
        this.mlocatn = mlocatn;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }
}