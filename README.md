## 工程简介

`跨端博客`后端分布式服务，基于Spring Cloud构建，包含服务注册中心、配置中心、网关、认证中心、监控中心、业务服务等。

## 项目实现

- 工程采用spring-cloud-alibaba作为微服务框架，实现了服务的注册、发现、配置管理、熔断、限流等功能。
- 工程采用nacos作为服务注册中心和配置中心，实现了服务的注册、发现、配置管理等功能。数据操作使用的是mybatis-plus，减少sql语句的编写，提升了代码的可阅读性和可维护性
- 用户认证服务模块基于spring-security-oauth2.0实现，实现了用户的登录、注册、授权、资源访问等功能。
重写了spring-security-oauth2.0的授权码模式，实现了自定义的授权码模式，实现了用户的单点登录。
- 资源管理服务使用的七牛云的对象存储服务，实现了文件的上传、下载、删除等功能。
- 内容管理服务实现了用户随笔、博客文章的存储，文章的搜索、分页、排序等功能。
- 网关服务使用的是spring-cloud-gateway，实现了服务的路由、过滤、限流等功能。

## 项目结构

``` 
blog
├─.idea
│  ├─httpRequests
│  ├─inspectionProfiles
│  └─leetcode
├─.mvn
│  └─wrapper
├─service-common
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─star
│  │  │  │          └─servicecommon
│  │  │  │              ├─config
│  │  │  │              ├─domain
│  │  │  │              ├─exception
│  │  │  │              ├─msg
│  │  │  │              └─util
│  │  │  └─resources
│  │  └─test
│  │      └─java
│  │          └─com
│  │              └─star
│  │                  └─servicecommon
│  └─target
│      ├─classes
│      │  └─com
│      │      └─star
│      │          └─servicecommon
│      │              ├─config
│      │              ├─domain
│      │              ├─exception
│      │              ├─msg
│      │              └─util
│      ├─generated-sources
│      │  └─annotations
│      ├─generated-test-sources
│      │  └─test-annotations
│      └─test-classes
│          └─com
│              └─star
│                  └─servicecommon
├─service-content
│  ├─.mvn
│  │  └─wrapper
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─star
│  │  │  │          └─servicecontent
│  │  │  │              ├─entity
│  │  │  │              │  └─dto
│  │  │  │              ├─mapper
│  │  │  │              │  └─xml
│  │  │  │              ├─service
│  │  │  │              │  └─impl
│  │  │  │              └─web
│  │  │  │                  ├─config
│  │  │  │                  ├─controller
│  │  │  │                  └─msg
│  │  │  └─resources
│  │  └─test
│  │      └─java
│  │          └─com
│  │              └─star
│  │                  └─servicecontent
│  └─target
│      ├─classes
│      │  └─com
│      │      └─star
│      │          └─servicecontent
│      │              ├─entity
│      │              │  └─dto
│      │              ├─mapper
│      │              ├─service
│      │              │  └─impl
│      │              └─web
│      │                  ├─config
│      │                  ├─controller
│      │                  └─msg
│      ├─generated-sources
│      │  └─annotations
│      ├─generated-test-sources
│      │  └─test-annotations
│      └─test-classes
│          └─com
│              └─star
│                  └─servicecontent
├─service-gateway
│  ├─src
│  │  └─main
│  │      ├─java
│  │      │  └─com
│  │      │      └─star
│  │      │          └─servicegateway
│  │      │              └─config
│  │      └─resources
│  └─target
│      ├─classes
│      │  └─com
│      │      └─star
│      │          └─servicegateway
│      │              └─config
│      ├─generated-sources
│      │  └─annotations
│      ├─generated-test-sources
│      │  └─test-annotations
│      └─test-classes
├─service-media
│  ├─.mvn
│  │  └─wrapper
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─star
│  │  │  │          └─servicemedia
│  │  │  │              ├─config
│  │  │  │              ├─constant
│  │  │  │              ├─enums
│  │  │  │              ├─utils
│  │  │  │              ├─Vo
│  │  │  │              └─web
│  │  │  │                  ├─controller
│  │  │  │                  └─msg
│  │  │  └─resources
│  │  └─test
│  │      └─java
│  │          └─com
│  │              └─star
│  │                  └─servicemedia
│  └─target
│      ├─classes
│      │  ├─com
│      │  │  └─star
│      │  │      └─servicemedia
│      │  │          ├─config
│      │  │          ├─enums
│      │  │          ├─utils
│      │  │          ├─Vo
│      │  │          └─web
│      │  │              ├─controller
│      │  │              └─msg
│      │  └─generated
│      ├─generated-sources
│      │  └─annotations
│      ├─generated-test-sources
│      │  └─test-annotations
│      └─test-classes
│          ├─com
│          │  └─star
│          │      └─servicemedia
│          └─generated_tests
├─service-openAi
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─star
│  │  │  │          └─serviceOpenAi
│  │  │  │              ├─config
│  │  │  │              ├─domain
│  │  │  │              │  └─dto
│  │  │  │              └─utils
│  │  │  └─resources
│  │  └─test
│  │      └─java
│  │          └─com
│  │              └─star
│  │                  └─serviceOpenAi
│  └─target
│      ├─classes
│      │  ├─com
│      │  │  └─star
│      │  │      └─serviceOpenAi
│      │  │          ├─config
│      │  │          ├─domain
│      │  │          │  └─dto
│      │  │          └─utils
│      │  └─META-INF
│      ├─generated-sources
│      │  └─annotations
│      ├─generated-test-sources
│      │  └─test-annotations
│      └─test-classes
│          └─com
│              └─star
│                  └─serviceOpenAi
└─service-user
    ├─src
    │  ├─main
    │  │  ├─java
    │  │  │  └─com
    │  │  │      └─star
    │  │  │          └─serviceuser
    │  │  │              ├─constant
    │  │  │              ├─domain
    │  │  │              │  ├─dto
    │  │  │              │  ├─entity
    │  │  │              │  └─vo
    │  │  │              ├─feign
    │  │  │              ├─mapper
    │  │  │              │  └─xml
    │  │  │              ├─service
    │  │  │              │  └─impl
    │  │  │              │      └─used
    │  │  │              ├─util
    │  │  │              └─web
    │  │  │                  ├─config
    │  │  │                  ├─controller
    │  │  │                  ├─filter
    │  │  │                  └─msg
    │  │  └─resources
    │  └─test
    │      └─java
    │          └─com
    │              └─star
    │                  └─serviceuser
    └─target
        ├─classes
        │  └─com
        │      └─star
        │          └─serviceuser
        │              ├─constant
        │              ├─domain
        │              │  ├─dto
        │              │  ├─entity
        │              │  └─vo
        │              ├─feign
        │              ├─mapper
        │              ├─service
        │              │  └─impl
        │              │      └─used
        │              ├─util
        │              └─web
        │                  ├─config
        │                  ├─controller
        │                  └─msg
        ├─generated-sources
        │  └─annotations
        ├─generated-test-sources
        │  └─test-annotations
        └─test-classes
            └─com
                └─star
                    └─serviceuser

```



