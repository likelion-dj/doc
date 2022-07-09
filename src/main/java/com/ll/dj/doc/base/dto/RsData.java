package com.ll.dj.doc.base.dto;

public abstract sealed class RsData permits RsData1, RsData2, RsData3 {
    private String resultCode;
    private String msg;

    RsData(String resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }
}
