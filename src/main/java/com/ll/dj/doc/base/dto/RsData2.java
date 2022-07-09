package com.ll.dj.doc.base.dto;

import lombok.Getter;

@Getter
public final class RsData2<T1, T2> extends RsData {
    private T1 data1;
    private T2 data2;

    private RsData2(String resultCode, String msg, T1 data1, T2 data2) {
        super(resultCode, msg);
        this.data1 = data1;
        this.data2 = data2;
    }

    public static <T1, T2> RsData2 of(String resultCode, String msg, T1 data1, T2 data2) {
        return new RsData2(resultCode, msg, data1, data2);
    }
}
