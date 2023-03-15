package cn.yiynx.example.entity;

import cn.yiynx.example.util.excel.ExcelImportHeader;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 示例 实体类
 */
@TableName("demo")
@Data
public class Demo {
    @ExcelProperty("编号")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ExcelProperty("标题") @ExcelImportHeader
    private String title;
    @ExcelProperty("内容") @ExcelImportHeader
    private String content;
    @ExcelProperty("创建日期") @ExcelImportHeader
    private LocalDateTime createDate;
    private String c1;
    private String c2;
    private String c3;
    private String c4;
    private String c5;
}
