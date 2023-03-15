package cn.yiynx.example.service.impl;

import cn.yiynx.example.mapper.IBaseMapper;
import cn.yiynx.example.service.IBaseService;
import cn.yiynx.example.util.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.objenesis.instantiator.util.ClassUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.yiynx.example.mapper.IBaseMapper.FETCH_SIZE;
import static cn.yiynx.example.util.excel.ExcelUtil.EXCEL_SHEET_ROW_MAX_SIZE;

/**
 * 基础业务层接口实现类
 * @author www@yiynx.cn
 * @param <T>
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {
    @Autowired
    private IBaseMapper<T> iBaseMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatchByQueryWrapper(Collection<T> entityList, Function<T, Wrapper<T>> queryWrapperFunction) {
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE);
        return executeBatch(entityList, DEFAULT_BATCH_SIZE, (sqlSession, entity) -> {
            MapperMethod.ParamMap param = new MapperMethod.ParamMap();
            param.put(Constants.ENTITY, entity);
            param.put(Constants.WRAPPER, queryWrapperFunction.apply(entity));
            sqlSession.update(sqlStatement, param);
        });
    }

    @Override
    public void page(long pageSize, Wrapper<T> queryWrapper, Consumer<IPage<T>> pageConsumer) {
        IPage<T> page = null;
        do {
            page = page(page == null ? new Page<>(1, pageSize) : new Page<>(page.getCurrent() + 1, page.getSize(), page.getTotal(), false), queryWrapper);
            pageConsumer.accept(page);
        } while (page.getCurrent() < page.getPages());
    }

    @Override
    public long pageForFetch(Wrapper<T> wrapper, Consumer<IPage<T>> pageConsumer) {
        IPage<T> page = page(new Page<>(1, 0), wrapper);
        page.setSize(FETCH_SIZE);
        AtomicLong pageNo = new AtomicLong();
        List<T> tmpList = new ArrayList<>((int) page.getSize());
        TableInfo tableInfo = TableInfoHelper.getTableInfo(getEntityClass());
        Map<String, String> columnPropertyMap = tableInfo.getFieldList().stream().collect(Collectors.toMap(TableFieldInfo::getColumn, TableFieldInfo::getProperty));
        iBaseMapper.listForFetch(tableInfo.getTableName(), wrapper, resultContext -> {
            columnPropertyMap.forEach((column, property) -> resultContext.getResultObject().put(property, resultContext.getResultObject().remove(column)));
            T bean = ClassUtils.newInstance(getEntityClass());
            BeanMap.create(bean).putAll(resultContext.getResultObject());
            tmpList.add(bean);
            if (resultContext.getResultCount() % page.getSize() == 0) {
                page.setCurrent(pageNo.incrementAndGet());
                pageConsumer.accept(page.setRecords(tmpList));
                tmpList.clear();
            }
        });
        if (!tmpList.isEmpty()) {
            page.setCurrent(pageNo.incrementAndGet());
            pageConsumer.accept(page.setRecords(tmpList));
            tmpList.clear();
        }
        return page.getPages();
    }

    @Override
    public void writeExcel(OutputStream outputStream, long pageSize, Wrapper<T> queryWrapper) {
        IPage<T> page = page(new Page<>(1, 0), queryWrapper).setSize(pageSize);  // 查询总条数，通过设置页大小而获取总页数
        ExcelUtil.write(outputStream, getEntityClass(), page.getPages(), pageNo -> page(new Page<>(pageNo, pageSize, page.getTotal(), false), queryWrapper).getRecords());
    }

    @Override
    public void writeExcelForParallel(OutputStream outputStream, int parallelNum, long pageSize, Wrapper<T> queryWrapper) throws InterruptedException {
        IPage<T> page = page(new Page<>(1, 0), queryWrapper).setSize(pageSize);  // 查询总条数，通过设置页大小而获取总页数
        ExcelUtil.writeForParallel(outputStream, getEntityClass(), parallelNum, page.getPages(), pageNo -> page(new Page<>(pageNo, pageSize, page.getTotal(), false), queryWrapper).getRecords());
    }

    @Override
    public void writeExcelForXParallel(OutputStream outputStream, int parallelNum, int pageSize, Wrapper<T> queryWrapper) throws InterruptedException, ExecutionException {
        System.out.println("开始");
        IPage<T> page = page(new Page<>(1, 0), queryWrapper).setSize(pageSize);  // 查询总条数，通过设置页大小而获取总页数
        ExcelUtil.writeForXParallel(outputStream, getEntityClass(), parallelNum, page.getPages(), pageNo -> page(new Page<>(pageNo, pageSize, page.getTotal(), false), queryWrapper).getRecords());
        System.out.println("结束");
    }

    @Override
    public void writeExcelImportTemplate(OutputStream outputStream) {
        ExcelUtil.writeImportTemplate(outputStream, getEntityClass());
    }

    @Override
    public void writeExcelForFetch(OutputStream outputStream, Wrapper<T> queryWrapper) {
        try (ExcelWriter excelWriter = EasyExcel.write(outputStream, getEntityClass()).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet(1).build();
            long totalPage = pageForFetch(queryWrapper, page -> {
                writeSheet.setSheetNo((int) (page.getCurrent() * page.getSize() / EXCEL_SHEET_ROW_MAX_SIZE + 1));
                writeSheet.setSheetName("Sheet" + writeSheet.getSheetNo());
                excelWriter.write(page.getRecords(), writeSheet); // excel写入数据
            });
            if (totalPage <= 0) { // 如果无待写入的数据则写入标题
                excelWriter.write(Collections.emptyList(), EasyExcel.writerSheet().build());
            }
        }
    }

}
