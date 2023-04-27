
# cross-end blog------back-end architecture


## getting start

- redis 启动（需提前进入redis根目录,并配置相关redis环境）
```bash
redis-server.exe redis.windows.conf
```
- nacos 启动（需提前进入nacos根目录，并配置相关nacos环境）
```bash
startup.cmd -m standalone
```
- 所有服务启动
```bash
mvn spring-boot:run
```

## 工程简介

`跨端博客`后端微服务体系，基于Spring Cloud构建，包含服务注册中心、配置中心、网关、认证中心、业务服务等。

## 项目架构实现

- 工程采用spring-cloud-alibaba作为微服务框架，实现了分布式的微服务体系。
    1. 使用了最新发布的spring-cloud-alibaba2.2.10-RC1版本,此版本也是支持spring-boot2.x.x的最后版本
  ```
   <!--spring-cloud-alibaba依赖管理-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!--springboot依赖管理-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!--因为要用到spring-cloud的组件，所以添加这个依赖管理-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
  ```
    2. 本项目基本使用且实现了spring-cloud-alibaba的所有组件
       包含了Sentinel、Nacos、OpenFeign、Gateway、security等组件
       ![img.png](assets/img.png)
- 工程采用nacos作为服务注册中心和配置中心，实现了服务的注册、发现、配置管理等功能。
  1、依赖注入与配置
    ```
  依赖注入：
  
    <!--nacos配置中心-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
   <!--nacos服务发现-->
      <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
  
  bootstrap.yml配置：
  
  spring:
  application:
  name: service-content
  cloud:
  nacos:
  server-addr: localhost:8848
  discovery:
  namespace: star-blog
  group: star-blog
   ```
  2. nacos配置中心
    ![img_1.png](assets/img_1.png)

- 数据操作使用的是mybatis-plus，减少sql语句的编写，提升了代码的可阅读性和可维护性，同时使用druid作为数据库连接池。
  1. 依赖注入与配置
    ```
  依赖注入：
     <!--druid-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>

        <!--mybatis-plus: 包含jdbc和mybatis-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
        </dependency>
  bootstrap.yml配置：
    datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/content
      password: ""
      username: root
    mybatis-plus:
      mapper-locations: classpath:mapper/*.xml
      global-config:
        db-config:
          id-type: auto
          logic-delete-value: 1
          logic-not-delete-value: 0
        # 自动填充配置
        meta-object-handler: com.star.servicecommon.config.MyMetaObjectHandler
      configuration:
        map-underscore-to-camel-case: true
        cache-enabled: false
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    ```
  2. mybatis-plus在SpringMVC架构中的具体使用具体使用
  ![img.png](assets/img_2.png)
    `将项目操作分为了mapper层、service层、controller层，分别对应了数据库操作、业务逻辑、接口调用。`
  
- 用户认证服务模块基于spring-security-oauth2.0实现，实现了用户的登录、注册、授权、资源访问等功能。

  `认证`

用户认证就是判断一个用户的身份是否合法的过程，用户去访问系统资源时系统要求验证用户的身份信 息，身份合法方可继续访问，不合法则拒绝访问。常见的用户身份认证方式有：用户名密码登录，二维 码登录，手机短信登录，指纹认证等方式。
认证是为了保护系统的隐私数据与资源，用户的身份合法方可访问该系统的资源。
   `授权`
授权是用户认证通过后，根据用户的权限来控制用户访问资源的过程，拥有资源的访问权限则正常访 问，没有权限则拒绝访问。
认证是为了保证用户身份的合法性，授权则是为了更细粒度的对隐私数据进行划分，授权是在认证通过 后发生的， 控制不同的用户能够访问不同的资源。

`RBAC模型`
主体 -》 角色 -》 资源 -》行为

![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f2606eb94497441fb84648424a748d5e~tplv-k3u1fbpfcp-watermark.image?)
`spring-cloud-starter-oauth2+security使用`

`相关依赖引入
spring-cloud-starter-oauth2+security是一套完整的用户登录授权系统，在认证服务上主要有三个依赖：spring-cloud-starter-security、spring-cloud-starter-oauth2、spring-security-jwt`
```

        <!--spring security相关依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>

  <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-jwt</artifactId>
            <version>1.1.1.RELEASE</version>
        </dependency>

```
`在其他需要权限访问的服务上主要包含两个依赖：`

```
  <!--spring security相关依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>

```
`认证服务搭建`

这里主要做security和oauth2的相关配置   主要由以下四个配置文件


![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/64fdb305f8934f128f141085735d9945~tplv-k3u1fbpfcp-watermark.image?)

`WebSecurityConfig主要做springsecurity相关的配置`
```
@EnableWebSecurity //开启security服务
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //允许在方法上加的注解来配置权限
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DaoAuthenticationProviderCustom daoAuthenticationProviderCustom;

    //使用自己定义DaoAuthenticationProviderCustom来代替框架的DaoAuthenticationProvider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProviderCustom);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //密码为明文方式
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }

    //配置安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/pow/**").authenticated()//访问/r开始的请求需要认证通过
                .anyRequest().permitAll()//其它请求全部放行
                .and()
                .formLogin().successForwardUrl("/login-success");//登录成功跳转到/login-success
        http.logout().logoutUrl("/logout");//退出地址
    }

}

```
`TokenConfig在认证服务中主要作token的生成配置`

```
@Configuration
public class TokenConfig {

    private String SIGNING_KEY = "030321liuxinyu";//和其他服务一致来解析token

    @Autowired
    TokenStore tokenStore;

//    @Bean
//    public TokenStore tokenStore() {
//        //使用内存存储令牌（普通令牌）
//        return new InMemoryTokenStore();
//    }

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    //令牌管理服务
    @Bean(name="authorizationServerTokenServicesCustom")
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service=new DefaultTokenServices();
        service.setSupportRefreshToken(true);//支持刷新令牌
        service.setTokenStore(tokenStore);//令牌存储策略

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }}
```

`AuthorizationServer主要是授权方面也就是spring-cloud-starter-oauth2相关的配置`

```
@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {


    @Resource(name = "authorizationServerTokenServicesCustom")
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    //客户端详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {

        clients.inMemory()// 使用in-memory存储
                .withClient("starBlog")// client_id
                .secret("030321liuxinyu")//客户端密钥
//                .secret(new BCryptPasswordEncoder().encode("XcWebApp"))//客户端密钥
                .resourceIds("starBlog")//资源列表

                .authorizedGrantTypes("authorization_code", "client_credentials", "implicit", "refresh_token", "password")// 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .scopes("all")// 允许的授权范围
                .autoApprove(false)//false跳转到授权页面
                //客户端接收授权码的重定向地址
                .redirectUris("http://www.xuecheng-plus.com")
        ;
    }


    //令牌端点的访问配置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)//认证管理器
                .tokenServices(authorizationServerTokenServices)//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    //令牌端点的安全配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()")                    //oauth/token_key是公开
                .checkTokenAccess("permitAll()")                  //oauth/check_token公开
                .allowFormAuthenticationForClients()                //表单认证（申请令牌）
        ;
    }

}
```
`DaoAuthenticationProviderCustom是一个很重要的配置，通过这个修改的springsecuritu原先提供的登录方案（账号密码登录），但当前环境肯定涉及到多种登录方式，我们通过重写`additionalAuthenticationChecks和setUserDetailsService`方法来自定义登录方式

```
@Slf4j
@Component
public class DaoAuthenticationProviderCustom extends DaoAuthenticationProvider {

 @Autowired
 public void setUserDetailsService(UserDetailsService userDetailsService) {
  super.setUserDetailsService(userDetailsService);
 }


 //不再校验密码
 protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

 }


}

```

`至此认证服务的相关配置就完成啦，我们可以通过POST localhost:8081/oauth/token?client_id=starBlog&client_secret=030321liuxinyu&grant_type=password&username={"account":"账号","authType":"登录方式","password":"密码"}来进行测试`


![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/cf8661a32e914bee9e24ed8e7105e399~tplv-k3u1fbpfcp-watermark.image?)

这就是前端用户点击登录后获得的信息，然后将该token存储在本地，用户每次访问的时候都携带token即可，

`oauth也提供了校验token的方法POST localhost:8081/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsieHVlY2hlbmctcGx1cyJdLCJ1c2VyX25hbWUiOiJ7XCJpZFwiOjE2MTY1MTYxNjUsXCJwaG9uZVwiOlwiMTM2NDg4MzcwOTNcIixcIkVtYWlsXCI6XCIyMDY0OTg5NDAzQHFxLmNvbVwiLFwiYWNjb3VudFwiOlwiMjA2NDk4OTQwM1wifSIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE2NzUyNTI3MjIsImF1dGhvcml0aWVzIjpbInRlc3QiXSwianRpIjoiMDU1MjBlOTYtMTVhOS00MjAzLWExMWItNjRjZTNhNDQ5ZTM4IiwiY2xpZW50X2lkIjoic3RhckJsb2cifQ.I6wM4jZJdSA07gTnzmlP59vzqvm1hxNcNt3ejE0mR8w`

![image.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2343a98e4ad64ee3a756ae144869cc7a~tplv-k3u1fbpfcp-watermark.image?)

`资源服务搭建`
引入配置文件，相比于认证服务，资源服务的配置量少很多，主要有以下两个配置文件

![image.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/27ad3d5c189a4243beae77449c746b46~tplv-k3u1fbpfcp-watermark.image?)
TokenConfig(和认证服务的类似，但不需要token生成)

```
@Configuration
public class TokenConfig {

    String SIGNING_KEY = "key";


    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}

```

`ResouceServerConfig（资源权限的相关配置）`
```
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {


    //资源服务标识
    public static final String RESOURCE_ID = "starBlog";

    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID)//资源 id
                .tokenStore(tokenStore)
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers( "/**").authenticated()//所有/r/**的请求必须认证通过
                .anyRequest().permitAll()
        ;
    }

}
```

`这样资源服务就搭建好了，我们访问资源`

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e8d6f8c7a3704bfaa38e9523ae869698~tplv-k3u1fbpfcp-watermark.image?)

`在资源服务的方法上添加@PreAuthorize("hasAuthority('test')")可以指定拥有某项权限的用户才可以访问`

重写了spring-security-oauth2.0的授权码模式，实现了自定义的授权码模式，实现了用户的单点登录。

- 资源管理服务使用的七牛云的对象存储服务，实现了文件的上传、下载、删除等功能。
  1. 七牛云服务页
  ![img_1.png](assets/img_3.png)
  2. 依赖注入与配置
  ```angular2html
    <!--七牛云-->
    依赖注入
        <dependency>
            <groupId>com.qiniu</groupId>
            <artifactId>qiniu-java-sdk</artifactId>
            <version>[7.7.0, 7.10.99]</version>
        </dependency>

        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>3.14.2</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.5</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>com.qiniu</groupId>
            <artifactId>happy-dns-java</artifactId>
            <version>0.1.6</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!--七牛云-->
    bootstrap.yml相关配置：
    qiniu:
      access-key: 你的access-key
      secret-key: 你的secret-key
      bucket: 你的bucket
      domain: 你的domain
    
  ```
    3. 上传文件
    ```
        @Override
        public String uploadFile(MultipartFile file) {
            //构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(Region.region0());
            //...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
            //...生成上传凭证，然后准备上传
            String accessKey = qiniuConfig.getAccessKey();
            String secretKey = qiniuConfig.getSecretKey();
            String bucket = qiniuConfig.getBucket();
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = null;
            try {
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                try {
                    Response response = uploadManager.put(file.getBytes(), key, upToken);
                    //解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    return qiniuConfig.getDomain() + putRet.key;
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                        //ignore
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    ```
- 内容管理服务实现了用户随笔、博客文章的存储，文章的搜索、分页、排序等功能。
  `此处多为数据库的crud,不做过多介绍`
- 网关服务使用的是spring-cloud-gateway，实现了服务的路由、过滤等功能。
    `结合nacos实现了动态路由`
  ```
    cloud:
    gateway:
      discovery:
        locator:
          #让gateway可以发现nacos中的微服务
          enabled: true
    nacos:
      server-addr: localhost:8848
      discovery:
        namespace: star-blog
        group: star-blog
  ```


## 项目结构

```
├─.idea
│  ├─httpRequests
│  ├─inspectionProfiles
│  └─leetcode
├─.mvn
│  └─wrapper
├─assets
├─service-common
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─star
│  │  │  │          └─servicecommon
│  │  │  │              ├─config
│  │  │  │              ├─constant
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
│      │              ├─constant
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
│  │      │              ├─config
│  │      │              └─web
│  │      └─resources
│  └─target
│      ├─classes
│      │  └─com
│      │      └─star
│      │          └─servicegateway
│      │              ├─config
│      │              └─web
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
│  │  │              ├─mapper
│  │  │              │  └─xml
│  │  │              ├─service
│  │  │              │  └─impl
│  │  │              │      └─used
│  │  │              ├─util
│  │  │              └─web
│  │  │                  ├─config
│  │  │                  ├─controller
│  │  │                  ├─feign
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
│              ├─mapper
│              ├─service
│              │  └─impl
│              │      └─used
│              ├─util
│              └─web
│                  ├─config
│                  ├─controller
│                  ├─feign
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
