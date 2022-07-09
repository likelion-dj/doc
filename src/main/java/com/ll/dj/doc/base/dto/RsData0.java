package com.ll.dj.doc.base.dto;

import lombok.Getter;

@Getter
public final class RsData0 extends RsData {
    private RsData0(String resultCode, String msg) {
        super(resultCode, msg);
    }

    public static RsData0 of(String resultCode, String msg) {
        return new RsData0(resultCode, msg);
    }
}
