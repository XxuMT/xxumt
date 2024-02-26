package com.example.xxumt.time;

import org.junit.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.Date;

/**
 * JDK1.8 Date、Time新特性
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/8/23 14:22
 * @since 1.0
 */
public class TimeIntroduction {
  @Test
  public void testClock() throws InterruptedException {
    // 时钟提供用于访问某个特定时区的瞬时时间、日期和时间
    // 系统默认UTC时钟 当时瞬时时间 System.currentTimeMillis()
    Clock c1 = Clock.systemUTC();
    System.out.println(c1.millis());
    // 系统默认时区时钟
    Clock c2 = Clock.systemDefaultZone();
    System.out.println(c2.millis());
    // 巴黎时区
    Clock c31 = Clock.system(ZoneId.of("Europe/Paris"));
    System.out.println(c31.millis());
    // 上海时区
    Clock c32 = Clock.system(ZoneId.of("Asia/Shanghai"));
    System.out.println(c32.millis());
    // 固定上海时区时钟
    Clock c4 = Clock.fixed(Instant.now(), ZoneId.of("Asia/Shanghai"));
    System.out.println(c4.millis());
    Thread.sleep(1000);
    System.out.println(c4.millis());
    // 系统默认时钟10s的时钟
    Clock c5 = Clock.offset(c1, Duration.ofSeconds(10));
    System.out.println(c1.millis());
    System.out.println(c5.millis());
  }

  @Test
  public void testInstant() throws InterruptedException {
    // 当时瞬时时间 System.currentTimeMillis()
    Instant i1 = Instant.now();
    // 精确到秒
    System.out.println(i1.getEpochSecond());
    // 精确到毫秒
    System.out.println(i1.toEpochMilli());
    Thread.sleep(1000);
    // 获取系统UTC默认时钟
    Instant i2 = Instant.now(Clock.systemUTC());
    System.out.println(i2.toEpochMilli());
    Thread.sleep(1000);
    // 固定瞬时时间时钟
    Instant i3 = Instant.now(Clock.fixed(i1, ZoneId.systemDefault()));
    System.out.println(i3.toEpochMilli());
    // equals i1
    System.out.println(i1 == i3);
  }

  @Test
  public void testLocalDateTime() {
    // 使用默认时区时钟瞬时时间创建 相当于 Clock.systemDefaultZone() -> ZoneId.systemDefault()
    LocalDateTime now = LocalDateTime.now();
    System.out.println(now);
    // 自定义时区
    LocalDateTime l1 = LocalDateTime.now(ZoneId.of("Europe/Paris"));
    System.out.println(l1);
    // 自定义时钟
    LocalDateTime l2 = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Dhaka")));
    System.out.println(l2);
    // 年月日 时分秒
    LocalDateTime d1 = LocalDateTime.of(2023, 1, 2, 0, 0, 0);
    System.out.println(d1);
    // 瞬时时间 + 时区
    LocalDateTime d2 = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
    System.out.println(d2);
    // 解析String --> LocalDateTime
    // 001毫秒 等价于001000000纳秒
    LocalDateTime d3 = LocalDateTime.parse("2023-01-02T00:00:00.001");
    System.out.println(d3);
    // 使用DateTimeFormatter解析和格式化
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime d4 = LocalDateTime.parse("2023-01-02 00:00:00", formatter);
    System.out.println(formatter.format(d4));
    // 时间获取
    System.out.println(d4.getYear());
    System.out.println(d4.getMonth());
    System.out.println(d4.getDayOfYear());
    System.out.println(d4.getDayOfMonth());
    System.out.println(d4.getDayOfWeek());
    System.out.println(d4.getHour());
    System.out.println(d4.getMinute());
    System.out.println(d4.getSecond());
    System.out.println(d4.getNano());
    // 时间增减
    LocalDateTime d5 = d4.minusDays(1);
    System.out.println(d5);
    LocalDateTime d6 = d4.plus(1, IsoFields.WEEK_BASED_YEARS);
    System.out.println(d6);
    // 判断两个日期是否相等
    System.out.println(d1.equals(d2));
    // MonthDay - 用来检查生日
    LocalDate dateOfBirth = LocalDate.of(1997, Month.AUGUST, 30);
    MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
    MonthDay currentDay = MonthDay.now();
    System.out.println(birthday.equals(currentDay));
    // YearMonth - 用来检查信用卡过期
    YearMonth currentYearMonth = YearMonth.now();
    YearMonth creditCardExpiry = YearMonth.of(2020, Month.AUGUST);
    System.out.println(creditCardExpiry.compareTo(creditCardExpiry));
    // 判断闰年
    System.out.println(dateOfBirth.isLeapYear());
  }

  @Test
  public void testZoneDateTime() {
    ZonedDateTime z1 = ZonedDateTime.now();
    System.out.println(z1);
    ZonedDateTime z2 = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
    System.out.println(z2);
    ZonedDateTime z3 = ZonedDateTime.parse("2023-01-02T00:00:00Z[Asia/Shanghai]");
    System.out.println(z3);
  }

  @Test
  public void testDuration() {
    Duration d1 = Duration.between(Instant.ofEpochMilli(System.currentTimeMillis() - 12323123), Instant.now());
    System.out.println(d1.toDays());
    System.out.println(d1.toHours());
    System.out.println(d1.toMinutes());
    System.out.println(d1.toMillis());
    Duration d2 = Duration.ofDays(1);
    System.out.println(d2.toDays());
  }

  @Test
  public void testChronology() {
    // 提供对Calendar的替换 提供对年历系统的支持
    Chronology c = HijrahChronology.INSTANCE;
    ChronoLocalDateTime d = c.localDateTime(LocalDateTime.now());
    System.out.println(d);
  }

  @Test
  public void testNewOldDateConvert() {
    // 新旧日期转换
    Instant instant = new Date().toInstant();
    System.out.println(instant);
    Date date = Date.from(instant);
    System.out.println(date);
  }
}
