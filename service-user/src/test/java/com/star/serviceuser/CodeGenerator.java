package com.star.serviceuser;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

/**
 * 一般情况, 需要改动的地方:
 * 32行: 作者, 43、45、46行: 数据源相关, 53行: 生成的代码路径, 62行: 需要映射的表名
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局策略配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir"); // 项目路径
        gc.setOutputDir(projectPath + "/service-user/src/main/java"); // 生成的文件路径
        gc.setAuthor("star");//作者，开发人员
        gc.setOpen(false); // 是否打开生成的目录
        gc.setFileOverride(false); // 是否覆盖已有文件, 默认false
        gc.setServiceName("%sService"); // service 命名方式，自动生成的Service类前面会自动加前缀I, 这里( %s 为占位符)取消I前缀
        gc.setIdType(IdType.AUTO); // 指定生成的主键的ID类型
        gc.setDateType(DateType.ONLY_DATE); // 数据库中的时间类型对应的java类, 此设置表示Date类, 默认是java8的时间类
        gc.setSwagger2(true); //实体属性 Swagger2 注解，需要配置Swagger2依赖
        mpg.setGlobalConfig(gc);

        // 数据源配置，通过该配置，指定需要生成代码的具体数据库
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/content?serverTimezone=GMT");//驱动连接的URL
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");//驱动名称
        dsc.setUsername("root");//数据库连接用户名
        dsc.setPassword("030321");//数据库连接密码
        dsc.setDbType(DbType.MYSQL); // 设置数据库连接的类型
        mpg.setDataSource(dsc);

        // 包名配置，通过该配置，指定生成代码的包路径
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null); // 模块路径(子路径)
        pc.setParent("com.star.serviceuser"); // 生成的代码的父路径
        pc.setEntity("entity"); // 生成实体类所在的包名
        pc.setMapper("mapper");
        pc.setService("service");
//        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("message"); // 要生成的表在数据库中的名称, 每张表名以英文逗号隔开
        strategy.setNaming(NamingStrategy.underline_to_camel); // 表名转换方式: 数据库中的下划线转成java驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 列名转换方式
        strategy.setEntityLombokModel(true); // 自动加上lombok注解
        strategy.setRestControllerStyle(true); // 加上@RestController注解
        strategy.setEntityTableFieldAnnotationEnable(true); // 为实体类的类上加@TableName, 所有字段上加注解
        strategy.setControllerMappingHyphenStyle(false); // RequestMapping种的驼峰是否转成用"-"连接, 默认是false
        //strategy.setTablePrefix("tp_"); // 按照表名生成实体类时去掉表名前面的"tp_"前缀
        // strategy.setLogicDeleteFieldName("deleted"); // 数据库中表示逻辑删除的字段名
        // strategy.setVersionFieldName("version"); // 数据库中表示乐观锁版本号的字段名

        // 自动填充配置: 插入时间, 最后一次更新时间
        ArrayList<TableFill> tableFills = new ArrayList<>();
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT); // 插入时改变的时间纪录, created为表的字段名
        TableFill gmtModified = new TableFill("update_time", FieldFill.INSERT_UPDATE); // 最后一次更新时updated表字段的时间记录
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);

        mpg.setStrategy(strategy);
        mpg.execute();//执行
    }


}

