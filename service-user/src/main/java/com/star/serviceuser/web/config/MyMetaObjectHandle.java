package com.star.serviceuser.web.config;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author star
 * @date 2023/2/3 1:07
 */
@Component
@Slf4j
public class MyMetaObjectHandle implements MetaObjectHandler {

    /**
     * 插入操作自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充 [insert]...");
        metaObject.setValue("id",new Snowflake(1,1,true).nextId());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

       // this.setFieldValByName("id",new Snowflake(1,1,true) , metaObject);
    }

    /**
     * 更新操作自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充 [update]...");
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
