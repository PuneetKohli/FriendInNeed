package com.csegsahack.friendinneed.util;

import java.util.Date;

/**
 * Created by charlesmarino on 12/8/16.
 */

public class TimeHelper {
    static public long getMinsLeft(Double lastUpdateTime) {
        Double currentTime = Double.valueOf((new Date()).getTime()/1000);
        return (long) (24*60 - ((currentTime - lastUpdateTime)/60));
    }
}
