package cn.yiynx.example.service.impl;

import cn.yiynx.example.entity.Demo;
import cn.yiynx.example.mapper.DemoMapper;
import cn.yiynx.example.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * 示例 数据访问层接口实现类
 */
@Service
public class DemoServiceImpl extends BaseServiceImpl<DemoMapper, Demo> implements DemoService {
}
