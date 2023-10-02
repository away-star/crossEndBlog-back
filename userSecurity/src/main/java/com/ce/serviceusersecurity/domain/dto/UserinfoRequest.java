package com.ce.serviceusersecurity.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author: xingxing
 * @create: 2023-10-02 10:19
 * @Description: 更改用户信息请求体
 */
@Data
public class UserinfoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String nickname;

    private LocalDate birthday;


    private String avatar;

    private String gender;

    private String address;

    private Long qq;

    private String slideShow;

    private String idiograph;


    private String welcomeText;


    private String githubAddr;


    private String juejinAddr;

    private String csdnAddr;

    private String subname;

}
