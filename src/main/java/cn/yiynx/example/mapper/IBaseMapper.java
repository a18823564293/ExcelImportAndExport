package cn.yiynx.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

import java.util.Map;

/**
 * 基础数据访问层接口
 * @param <T>
 */
public interface IBaseMapper<T> {
    int FETCH_SIZE = 10000;

    /**
     * 流式查询
     * @param tableName 表名
     * @param queryWrapper 查询条件
     * @param resultHandler 结果处理
     */
    @Select("SELECT ${ew.sqlSelect != null ? ew.sqlSelect : '*'} FROM ${tableName} ${ew.customSqlSegment}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = FETCH_SIZE)
    @ResultType(Map.class)
    void listForFetch(@Param("tableName") String tableName, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper, ResultHandler<Map<String, Object>> resultHandler);
}
