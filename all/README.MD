# spring-cloud-study

#### 介绍
基于spring-cloud finchley版本的学习框架

#### 软件架构
软件架构说明

1. 此项目是后台的架构，前端请使用https://gitee.com/fa-0512/muntjak-view。
2. 使用基于spring-cloud finchley版本开发的微服务框架,已经集成了eureka、zuul、config、hystrix、ribbon、feign等组件。
3. 使用mysql作为业务数据存储，mongdb作为日志存储，redis作为业务缓存。

#### 安装教程

1. 创建mysql数据库运行database/muntjak.sql初始化数据库。
2. 安装mongdb创建logs数据库，如果使用另外的数据库请注意修改相应子项目中mongdb的配置。
3. 安装redis作为业务缓存系统，设置相应的安全密码并修改子项目中与redis相关的配置。

#### 使用说明

1. 启动finchley-eureka
2. 启动finchley-zuul
3. 启动adminService

4. 打开浏览器输入http://${ip}:${zuul服务端口}/service-admin/extcode，返回

{"msg":"操作成功","extcode":"9048","code":200,"salt":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5MDQ4LjQyNTU3Mjg0Mjg5MyJ9.xD0IKu5dWD0oZMdrEyf1X2ubbeBj42rehTdvlEGVFrM"}
表示本项目可以正常启动了，如果需要界面支持请下muntjak-view。