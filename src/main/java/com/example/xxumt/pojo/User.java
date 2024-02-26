package com.example.xxumt.pojo;

import com.example.xxumt.annotation.ExcelName;
import lombok.Data;

import java.io.Serializable;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/19 15:04
 * @since 1.0
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 6696479585196016564L;

    @ExcelName(name = "用户ID")
    private Long userId;

    @ExcelName(name = "姓名")
    private String userName;

    @ExcelName(name = "用户编号")
    private String userCode;

    @ExcelName(name = "年龄")
    private Integer age;
}
