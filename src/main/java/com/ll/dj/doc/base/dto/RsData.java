package com.ll.dj.doc.base.dto;

import lombok.Getter;
import lombok.Setter;

public abstract sealed class RsData permits RsData0, RsData1, RsData2, RsData3 {
    @Getter
    @Setter
    private String resultCode;
    @Getter
    @Setter
    private String message;

    RsData(String resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }
}
