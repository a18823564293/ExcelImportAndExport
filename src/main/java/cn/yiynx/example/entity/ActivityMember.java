package cn.yiynx.example.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 活动预约信息表
 * </p>
 *
 * @author admin
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_activity_member")
public class ActivityMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty(value = "id")
    private Long id;

    @ExcelProperty(value = "活动编号")
    private Long activityNo;

    @ExcelProperty(value = "活动日期")
    private String activityTime;

    @ExcelProperty(value = "预约人微信的userId")
    private Long userId;

    @ExcelProperty(value = "预约人ID")
    private Long userNo;

    @ExcelProperty(value = "真实姓名")
    private String userName;

    @ExcelProperty(value = "身份证号")
    private String idCardNo;

    @ExcelProperty(value = "预约编号")
    private String orderNum;

    @ExcelProperty(value = "预约状态（0:已预约 1:已取消 ）")
    private Integer orderStatus;

    @ExcelProperty(value = "预约时间")
    private Date orderTime;

    @ExcelProperty(value = "入场时间")
    private Date entryTime;

    @ExcelProperty(value = "是否已入场（0:已入场 1:未入场 ）")
    private Integer type;

    @ExcelProperty(value = "设备编号")
    private String snId;

    @ExcelProperty(value = "经度")
    private String longitude;

    @ExcelProperty(value = "纬度")
    private String latitude;

}
