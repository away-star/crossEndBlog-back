package com.star.servicemedia.web.controller;


import com.star.servicemedia.Vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@Slf4j
public class postController {

    @PostMapping("/666")
    public R<String> post(){
        return R.success("发表成功");
    }
}
