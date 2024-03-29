package com.star.servicecommon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author star
 * @date 2023/5/8 9:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String mail;
    private String captcha;
    private String content;
}
