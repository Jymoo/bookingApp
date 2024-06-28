package com.example.bookapp;

public class Model {
    private String mImageUrl, mdesc,mlocatn,mtitle;

    public Model() {
    }

    public Model(String mImageUrl, String mdesc, String mlocatn,String mtitle) {
        this.mImageUrl = mImageUrl;
        this.mdesc = mdesc;
        this.mlocatn= mlocatn;
        this.mtitle=mtitle;
    }

    public Model(String toString, String toString1) {
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
