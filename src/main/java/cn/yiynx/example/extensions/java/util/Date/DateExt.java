package cn.yiynx.example.extensions.java.util.Date;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date方法扩展
 */
@Extension
public class DateExt {


  /**
   * 【扩展方法】获取当前时间
   * @return 当前时间
   */
  @Extension
  public static Date xNow() {
    return new Date();
  }

  /**
   * 【扩展方法】日期格式化
   * @param date 日期
   * @param format 日期格式
   * @return
   */
  @Extension
  public static String xFormat(@This Date date, String format) {
    return new SimpleDateFormat(format).format(date);
  }

}