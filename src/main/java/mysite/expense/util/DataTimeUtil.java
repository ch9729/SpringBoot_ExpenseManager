package mysite.expense.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
public class DataTimeUtil {
    
    // 자바 날짜 date를 문자열 포맷으로 변환
    public static String convertDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    // 문자열 날짜를 Data 날짜
    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = sdf.parse(dateString);
        return new Date(utilDate.getTime());
    }
}
