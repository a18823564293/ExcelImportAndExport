package cn.yiynx.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 基础业务层接口
 * @author www@yiynx.cn
 * @param <T>
 */
public interface IBaseService<T> extends IService<T> {

    @Transactional(rollbackFor = Exception.class)
    boolean updateBatchByQueryWrapper(Collection<T> entityList, Function<T, Wrapper<T>> queryWrapperFunction);

    /**
     * 分页查询并消费全部数据
     * @param pageSize 页大小
     * @param queryWrapper 查询条件
     * @param pageConsumer 消费者
     */
    void page(long pageSize, Wrapper<T> queryWrapper, Consumer<IPage<T>> pageConsumer);

    /**
     * 流式分页查询并消费全部数据<br>
     * 注意：使用原生MyBatis查询，MyBatis-Plus相关特性不支持(如逻辑删除字段等)
     * @see cn.yiynx.example.mapper.IBaseMapper#listForFetch(String, Wrapper, ResultHandler) 
     * @param queryWrapper 查询条件
     * @param pageConsumer 消费者
     */
    long pageForFetch(Wrapper<T> queryWrapper, Consumer<IPage<T>> pageConsumer);

    /**
     * 写入Excel
     * @param outputStream 输出流
     * @param pageSize 页大小
     * @param queryWrapper 查询条件
     */
    void writeExcel(OutputStream outputStream, long pageSize, Wrapper<T> queryWrapper);

    /**
     * 写入Excel（并发查询数据，有序串行写入数据）
     * @param outputStream 输出流
     * @param parallelNum 并发线程数
     * @param pageSize 页大小
     * @param queryWrapper 查询条件
     * @throws InterruptedException
     */
    void writeExcelForParallel(OutputStream outputStream, int parallelNum, long pageSize, Wrapper<T> queryWrapper) throws InterruptedException;

    /**
     * 写入Excel[写入速度可能更快]（并发查询数据，有序串行写入数据）
     * @param outputStream 输出流
     * @param parallelNum 并发线程数
     * @param pageSize 页大小
     * @param queryWrapper 查询条件
     * @throws InterruptedException
     * @throws ExecutionException
     */
    void writeExcelForXParallel(OutputStream outputStream, int parallelNum, int pageSize, Wrapper<T> queryWrapper) throws InterruptedException, ExecutionException;

    /**
     * 写入Excel导入模板
     * @param outputStream
     */
    void writeExcelImportTemplate(OutputStream outputStream);

    /**
     * 写入Excel（流式查询数据并写入）<br>
     * 注意：使用原生MyBatis查询，MyBatis-Plus相关特性不支持(如逻辑删除字段、下划线自动转驼峰等)
     * @see cn.yiynx.example.mapper.IBaseMapper#listForFetch(String, Wrapper, ResultHandler)
     * @param outputStream 输出流
     * @param queryWrapper 查询条件
     */
    void writeExcelForFetch(OutputStream outputStream, Wrapper<T> queryWrapper);

}
