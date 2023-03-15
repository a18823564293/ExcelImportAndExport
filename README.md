# example

#### 介绍
 * ExcelUtil（基于EasyExcel）使用示例
 * MyBatis-Plus自定义BaseService&BaseServiceImpl 使用示例
 * ParallelUtil(并发工具类，并发生产数据，有序串行消费数据) 使用示例
 * SlidingWindow(并发工具类，并发生产数据，有序串行消费数据) 使用示例
 * Xif 策略模式工具包(通过注解使用策略模式) 使用示例
 * Google @AutoService注解实现的策略模式示例
 * Manifold @Extension扩展方法注解示例

#### 软件架构
Java17、Spring Boot、MySQL|PostgreSQL、EasyExcel、Maven、Hutool
 * 需安装的插件：Lombok、Manifold


#### 安装教程
 * 配置application.yml中的数据库用户名和密码和schema
 * 执行项目resources/db目录下mysql.sql或pgsql.sql创建demo表，生成测试数据
 * 启动项目(ExampleApplication)

#### 使用说明
##### 1、测试Excel导入与导出
  * 使用浏览器访问(同步查询版)：http://localhost:8080/exportExcel
  * 使用浏览器访问(并发查询版1)：http://localhost:8080/writeExcelForParallel
  * 使用浏览器访问(并发查询版2)：http://localhost:8080/writeExcelForXParallel
  * 使用浏览器访问(流式查询版)：http://localhost:8080/writeExcelForFetch
  * 下载导入模板：http://localhost:8080/downloadImportTemplate
  * 导入Excel：POST http://localhost:8080/importExcel   form-data key:file -> value:excelFile
#### 2、测试Xif
  * http://localhost:8080/xif?type=1  或 type=2, 或其它

#### 主要变更日志
 * 2023-03-05 引入Manifold。
 * 2023-03-05 Java8->Java17、Spring boot 2.x -> 3.x

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

