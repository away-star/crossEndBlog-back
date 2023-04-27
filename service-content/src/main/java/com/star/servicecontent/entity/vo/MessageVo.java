package com.star.servicecontent.entity.vo;

import com.star.servicecontent.entity.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author star
 * @date 2023/4/21 19:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageVo extends Message {
    private String nickname;
    private String avatar;

}
