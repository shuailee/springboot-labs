# 工程简介
###  项目描述
springboot的学习项目，集成常用的三方组件，发布构建的时候使用有CI/CD流水线和docker方式部署
springboot知识点：
1. 自定义启动事件
2. filter过滤器
3. Spring AOP

### 引入的三方组件
#### 1 swagger
https://swagger.io/
https://bbs.huaweicloud.com/blogs/160304
访问地址：http://127.0.0.1:8888/swagger-ui.html

swagger 的UI增强版：https://github.com/xiaoymin/swagger-bootstrap-ui

访问地址： http://127.0.0.1:8888/doc.html
##### Swagger包含的工具集：
* Swagger编辑器： Swagger Editor允许您在浏览器中编辑YAML中的OpenAPI规范并实时预览文档。
* Swagger UI： Swagger UI是HTML，Javascript和CSS资产的集合，可以从符合OAS标准的API动态生成漂亮的文档。
* Swagger Codegen：允许根据OpenAPI规范自动生成API客户端库（SDK生成），服务器存根和文档。
* Swagger Parser：用于解析来自Java的OpenAPI定义的独立库
* Swagger Core：与Java相关的库，用于创建，使用和使用OpenAPI定义
* Swagger Inspector（免费）： API测试工具，可让您验证您的API并从现有API生成OpenAPI定义
* SwaggerHub（免费和商业）： API设计和文档，为使用OpenAPI的团队构建。
##### 常用注解
```java
/**
 @Api：修饰整个类，描述Controller的作用
 @ApiOperation：描述一个类的一个方法，或者说一个接口
 @ApiParam：单个参数描述
 @ApiModel：用对象来接收参数
 @ApiProperty：用对象接收参数时，描述对象的一个字段
 @ApiResponse：HTTP响应其中1个描述
 @ApiResponses：HTTP响应整体描述
 @ApiIgnore：使用该注解忽略这个API
 @ApiError ：发生错误返回的信息
 @ApiImplicitParam：一个请求参数
 @ApiImplicitParams：多个请求参数
 */
public class People {
    @PostMapping("/people")
    @ApiOperation(value = "保存人员信息")
    @ApiResponses({
           @ApiResponse(code = 0, message = "保存成功"),
           @ApiResponse(code = 1, message = "保存失败")
    })
    public FrontResult save(
             @ApiParam(value = "保存参数", example = "")
             @RequestBody People people) {
         people.setBirthday(Timestamp.valueOf(LocalDateTime.now()));
         return new FrontResult(FrontResult.SUCCEED, "保存成功", peopleDao.save(people));
    }
}


@Data
@ApiModel(description = "人员信息保存请求对象")
public class People {
    @ApiModelProperty(value = "人员编号")
    private Long id;
    @ApiModelProperty(value = "姓名", required = true,position = 1)
    private String name;
    @ApiModelProperty(value = "性别", required = true,position = 2)
    private String sex;
    @ApiModelProperty(value = "生日", required = true,position = 3)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp birthday;
}
```

#### 2 druid连接池
#### 3 mybatis-plus
**文档:** https://mp.baomidou.com/guide/

**mybatis-plus和mybatis的不同：** 
* mybatis-plus是对mybatis的增强，未对mybatis做修改，所以mybatis的所有配置和使用方式都保持不变。使用时只需要引入mybatis-plus的maver依赖即可。
* 在不需要自定义sql的情况下mybatis-plus不需要配置xml，只需要定义mapper接口继承BaseMapper 即可实现大部分CRUD操作
* 关于分页，mybatis分页需要使用PageHelper 插件完成分页，而mybatis-plus内置了分页拦截器PaginationInnerInterceptor实现，需要配置在MybatisPlusInterceptor 类


#### 4 登录和权限管理 shiro
http://greycode.github.io/shiro/doc/reference.html
https://github.com/greycode/shiro

