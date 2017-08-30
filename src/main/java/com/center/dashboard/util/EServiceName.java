package com.center.dashboard.util;

/**
 * Created by WYKIM on 2017-07-05.
 */
public enum EServiceName {
    SDP
    ,THINQ;

    @Override
    public String toString() {
        switch(this) {
            case SDP: return "SDP";
            case THINQ: return "THINQ";
            default: throw new IllegalArgumentException();
        }
    }
}
