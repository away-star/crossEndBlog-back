package com.ce.servicecontent.domain.dto;


import lombok.Data;

/**
 * @author: xingxing
 * @create: 2023-10-02 21:18
 * @Description: post请求类
 */

@Data
public class PostRequest {

    private Integer id;

    private String title;

    private String content;

    private Long authorId;

    private String category;

    private Boolean isPublic;

    private String coverUrl;

    private String description;
}
