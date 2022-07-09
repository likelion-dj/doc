package com.ll.dj.doc.base.dto;

import lombok.Getter;

@Getter
public final class RsData1<T1> extends RsData {
    private T1 data1;

    private RsData1(String resultCode, String msg, T1 data1) {
        super(resultCode, msg);
        this.data1 = data1;
    }

    public static <T1> RsData1 of(String resultCode, String msg, T1 data1) {
        return new RsData1(resultCode, msg, data1);
    }
}
