package com.ce.servicecontent.domain.vo;

import com.ce.servicecontent.domain.entity.Essay;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: xingxing
 * @create: 2023-10-02 22:22
 * @Description: 随笔
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EssayVo extends Essay {
    private String[] urls;

    private int favoriteCount;
}
