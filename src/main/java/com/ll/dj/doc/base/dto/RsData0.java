package com.ll.dj.doc.base.dto;

import lombok.Getter;

@Getter
public final class RsData0 extends RsData {
    private RsData0(String resultCode, String message) {
        super(resultCode, message);
    }

    public static RsData0 of(String resultCode, String message) {
        return new RsData0(resultCode, message);
    }
}
