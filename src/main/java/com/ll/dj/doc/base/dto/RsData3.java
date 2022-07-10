package com.ll.dj.doc.base.dto;

import lombok.Getter;

@Getter
public final class RsData3<T1, T2, T3> extends RsData {
    private T1 data1;
    private T2 data2;
    private T3 data3;

    private RsData3(String resultCode, String message, T1 data1, T2 data2, T3 data3) {
        super(resultCode, message);
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }

    public static <T1, T2, T3> RsData3 of(String resultCode, String message, T1 data1, T2 data2, T3 data3) {
        return new RsData3(resultCode, message, data1, data2, data3);
    }
}
