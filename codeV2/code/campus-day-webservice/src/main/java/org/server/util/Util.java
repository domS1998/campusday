package org.server.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Generische Utility Klasse
public class Util {

    public static Timestamp convertStringToTimestamp(String strDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date date = formatter.parse(strDate);
        return new Timestamp(date.getTime());
    }
}
