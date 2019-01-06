# spring-cloud-study

#### 介绍
基于spring-cloud finchley版本的学习框架

#### 软件架构
软件架构说明

1. 此项目是后台的架构，前端请使用https://gitee.com/fa-0512/muntjak-view。
2. 使用基于spring-cloud finchley版本开发的微服务框架,已经集成了eureka、zuul、config、hystrix、ribbon、feign等组件。
3. 安装redis，设置相应的安全密码并修改子项目中与redis相关的配置。

#### 安装教程

1. 创建mysql数据库运行database/muntjak.sql初始化数据库。
2. 安装mongdb创建logs数据库，如果使用另外的数据库请注意修改相应子项目中mongdb的配置。
3. 使用mysql作为业务数据存储，mongdb作为日志数据存储，redis作为业务缓存。

#### 使用说明

1. 启动finchley-eureka
2. 启动finchley-zuul
3. 启动adminService