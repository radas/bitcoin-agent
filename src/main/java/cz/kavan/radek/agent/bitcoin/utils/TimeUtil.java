package cz.kavan.radek.agent.bitcoin.utils;

import org.joda.time.DateTime;

public class TimeUtil {

    public static DateTime convertTimestampLocalDateTime(long timestamp) {
        return new DateTime(timestamp * 1000).plusHours(1);
    }

}
