package com.star.serviceuser.domain.dto;

import com.star.serviceuser.domain.entity.Label;
import com.star.serviceuser.domain.entity.Proverbs;
import lombok.Data;

/**
 * @author star
 * @date 2023/4/17 21:04
 */

@Data
public class BlogSetting {
    private Label[] labels;
    private Proverbs[] proverbs;
    private String[] slides;
}
