package com.star.servicecontent.entity.dto;

import com.star.servicecontent.entity.Essay;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author star
 * @date 2023/2/12 21:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EssayDto extends Essay {
    private String[] urls;
}
