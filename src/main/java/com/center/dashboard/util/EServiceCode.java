package com.center.dashboard.util;

/**
 * Created by WYKIM on 2017-07-05.
 */
public enum EServiceCode {
    SDP
    ,THINQ;

    @Override
    public String toString() {
        switch(this) {
            case SDP: return "SVC301";
            case THINQ: return "SVC201";
            default: throw new IllegalArgumentException();
        }
    }
}
