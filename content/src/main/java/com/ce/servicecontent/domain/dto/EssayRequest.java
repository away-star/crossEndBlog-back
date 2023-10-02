package com.ce.servicecontent.domain.dto;

import lombok.Data;

/**
 * @author: xingxing
 * @create: 2023-10-02 22:16
 * @Description: essay请求体
 */
@Data
public class EssayRequest {
    private String[] coverUrls;

    private Long id;

    private String content;

    private String mood;

    private Boolean isPublic;

}
