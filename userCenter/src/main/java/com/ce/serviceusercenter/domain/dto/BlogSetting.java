package com.ce.serviceusercenter.domain.dto;

import com.ce.serviceusercenter.domain.entity.Label;
import com.ce.serviceusercenter.domain.entity.Proverb;
import lombok.Data;

/**
 * @author xingxing
 * @date 2023/4/17 21:04
 */

@Data
public class BlogSetting {
    private Label[] labels;
    private Proverb[] Proverb;
    private String[] slides;
}
