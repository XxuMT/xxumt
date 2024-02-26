package com.example.xxumt.util;

import com.example.xxumt.annotation.ExcelName;
import com.example.xxumt.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * csv表格生成工具类
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/18 16:45
 * @since 1.0
 */
@SuppressWarnings("PlaceholderCountMatchesArgumentCount, ResultOfMethodCallIgnored")
@Slf4j
public class CsvFileUtil {
  public static final String FILE_SUFFIX = ".csv";
  public static final String CSV_COMMA = ",";
  public static final String CSV_TAIL = "\r\n";
  public static final String DATA_STR_FILE_NAME = "yyyyMMddHHmmss";
  public static final String UID = "serialVersionUID";

  /**
   * 将字符串转成csv文件
   *
   * @param savaPath 保存地址
   * @param context 上下文
   */
  public static void createCsvFile(String savaPath, String context) {
    File file = new File(savaPath);
    try {
      file.createNewFile();
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.write(context.getBytes(StandardCharsets.UTF_8));
      fileOutputStream.flush();
      fileOutputStream.close();
    } catch (IOException e) {
      log.error("create file exception:{}", e);
    }
  }

  /**
   * 写文件
   *
   * @param fileName 文件名
   * @param content 内容
   */
  public static void writeFile(String fileName, String content) {
    FileOutputStream fos = null;
    OutputStreamWriter osWriter = null;
    try {
      fos = new FileOutputStream(fileName, true);
      osWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
      osWriter.write(content);
      osWriter.flush();
    } catch (Exception e) {
      log.error("writer file exception:{}", e);
    } finally {
      if (fos != null) {
        IOUtils.closeQuietly(fos);
      }
      if (osWriter != null) {
        IOUtils.closeQuietly(osWriter);
      }
    }
  }

  /**
   * 构建csv文件名称
   *
   * @param object 转换对象
   * @return str
   */
  public static String buildCsvFileName(Object object) {
    return object.getClass().getSimpleName() + new SimpleDateFormat(DATA_STR_FILE_NAME).format(new Date()) + FILE_SUFFIX;
  }

  /**
   * 构建csv表头 将字段名称list转换成csv格式表格名称
   *
   * @param fieldNameList 字段名称list
   * @return str
   */
  public static String buildCsvTableNames(List<String> fieldNameList) {
    StringBuilder tableNames = new StringBuilder();
    for (String name : fieldNameList) {
      tableNames.append(name).append(CSV_COMMA);
    }
    return tableNames.append(CSV_TAIL).toString();
  }

  /**
   * 将实体类字段名称转换为字段名称list，方便后续转换成csv格式表格名称
   *
   * @param entity 实体类
   * @param <T> 类型
   * @return List<String>
   */
  public static <T> List<String> resolveTableName(T entity) {
    Class<?> beam = entity.getClass();
    Field[] fields = beam.getDeclaredFields();
    List<String> list = new ArrayList<>(fields.length);
    for (Field field : fields) {
      String tableName = field.getName();
      try {
        if (!UID.equals(tableName)) {
          ExcelName annotation = field.getAnnotation(ExcelName.class);
          String annName = annotation.name();
          if (StringUtils.hasLength(annName)) {
            tableName = annName;
          }
          list.add(tableName);
        }
      } catch (Exception e) {
        log.error("");
      }
    }
    return list;
  }

  /**
   * 类转map
   *
   * @param entity 要转换的实体类
   * @param <T> 类型
   * @return map
   */
  public static <T> Map<String, Object> toMap(T entity) {
    Class<?> bean = entity.getClass();
    Field[] fields = bean.getDeclaredFields();
    Map<String, Object> map = new HashMap<>(fields.length);
    for (Field field : fields) {
      try {
        if (!UID.equals(field.getName())) {
          String methodName =
              "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
          Method method = bean.getDeclaredMethod(methodName);
          Object fieldValue = method.invoke(entity);
          map.put(field.getName(), fieldValue);
        }
      } catch (Exception e) {
        log.error("toMap Exception={}", e.getMessage());
      }
    }
    return map;
  }

  /**
   * 构建csv表格内容 将实体类list转换成csv类型
   *
   * @param dataLists 实体类list
   * @param <T> 类型
   * @return str
   */
  public static <T> String buildCsvFileBody(List<T> dataLists) {
    // 将list对象转换成list map
    List<Map<String, Object>> maps = new ArrayList<>(dataLists.size());
    for (Object o : dataLists) {
      maps.add(toMap(o));
    }
    // 将数据拼接成csv格式
    StringBuilder sb = new StringBuilder();
    for (Map<String, Object> map : maps) {
      for (String key : map.keySet()) {
        Object value = map.get(key);
        if (Objects.nonNull(value)) {
          sb.append(value).append(CSV_COMMA);
        } else {
          sb.append("--").append(CSV_COMMA);
        }
      }
      sb.append(CSV_TAIL);
    }
    return sb.toString();
  }

  public static <T> void listConvertCsvFile(List<T> list) {
    if (CollectionUtils.isEmpty(list)) {
      return;
    }
    String fileName = "/Users/xxumt/mengting/document/" + buildCsvFileName(list.get(0));
    String tableNames = buildCsvTableNames(resolveTableName(list.get(0)));
    createCsvFile(fileName, tableNames);
    String contentBody = buildCsvFileBody(list);
    writeFile(fileName, contentBody);
  }

  public static void main(String[] args) {
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setUserId(1L);
    user.setUserCode("20142018");
    user.setUserName("xxumt");
    user.setAge(26);
    users.add(user);
    listConvertCsvFile(users);
  }
}
