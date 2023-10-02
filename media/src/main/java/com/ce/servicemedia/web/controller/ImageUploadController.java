package com.ce.servicemedia.web.controller;


import com.ce.servicemedia.domain.Vo.R;
import com.ce.servicemedia.utils.QiNiuSupport;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/source")
@Slf4j
public class ImageUploadController {

    @Resource
    private QiNiuSupport qiNiuSupport;

 /*   @PostMapping("/upload")
    public R<String> upload(@RequestPart MultipartFile file) throws Exception {
        //以流的形式上传
        InputStream fileStream = file.getInputStream();
        String url = qiNiuSupport.uploadFileInputStream(fileStream, UUID.randomUUID() + file.getOriginalFilename());

        *//*
         以字节形式上传
         String url=qiNiuSupport.uploadFile(file.getBytes(), UUID.randomUUID() + file.getOriginalFilename());
        *//*

        return R.success(url);
    }*/

    @PostMapping("/upload")
    public R<String> upload(@RequestPart MultipartFile file) throws Exception {
        log.error(file.getOriginalFilename());

        //log.error(file.toString());
        //以流的形式上传
        InputStream fileStream = file.getInputStream();
        String contentType = file.getContentType();
        String url = qiNiuSupport.uploadFileInputStream(fileStream, "blog/" +UUID.randomUUID()+'.'+contentType.substring(contentType.indexOf("/")+1));

        /*
         以字节形式上传
         String url=qiNiuSupport.uploadFile(file.getBytes(), UUID.randomUUID() + file.getOriginalFilename());
        */

        return R.success(url);
    }
}