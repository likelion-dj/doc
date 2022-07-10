package com.ll.dj.doc.base.dto;

import lombok.Getter;

public abstract sealed class RsData permits RsData0, RsData1, RsData2, RsData3 {
    @Getter
    private String resultCode;
    @Getter
    private String message;

    RsData(String resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }
}
