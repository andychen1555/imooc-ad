package com.imooc.ad.utils;

import com.imooc.ad.exception.AdException;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

public class CommonUtils {

  public static String[] parsePatterns = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"};

  /**
   * md5 操作
   */
  public static String md5(String value) {
    return DigestUtils.md5Hex(value).toUpperCase();
  }

  /**
   * 将字符串解析为时间格式
   */
  public static Date parseStringDate(String dateString) throws AdException {
    try {
      return DateUtils.parseDate(dateString, parsePatterns);
    } catch (ParseException e) {
      throw new AdException(e.getMessage());
    }
  }
}
