# 工程简介
###  项目描述
springboot的学习项目，集成常用的三方组件，发布构建的时候使用有CI/CD流水线和docker方式部署
springboot知识点：
1. 自定义启动事件
2. filter过滤器
3. Spring AOP

### 引入的三方组件
#### 1 swagger
#### 2 druid连接池
#### 3 mybatis-plus
**文档:** https://mp.baomidou.com/guide/

**mybatis-plus和mybatis的不同：** 
* mybatis-plus是对mybatis的增强，未对mybatis做修改，所以mybatis的所有配置和使用方式都保持不变。使用时只需要引入mybatis-plus的maver依赖即可。
* 在不需要自定义sql的情况下mybatis-plus不需要配置xml，只需要定义mapper接口继承BaseMapper 即可实现大部分CRUD操作
* 关于分页，mybatis分页需要使用PageHelper 插件完成分页，而mybatis-plus内置了分页拦截器PaginationInnerInterceptor实现，需要配置在MybatisPlusInterceptor 类




