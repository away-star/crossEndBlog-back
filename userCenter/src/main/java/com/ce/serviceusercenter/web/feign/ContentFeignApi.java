package com.ce.serviceusercenter.web.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ce.servicecommon.domain.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author xingxing
 * @date 2023/1/28 12:03
 */

@FeignClient(name = "service-content")
public interface ContentFeignApi {

    @GetMapping("/post/page")
    Result<Page> postPage(
            //此处不加value会报错
            @RequestParam(value = "authorId",required = true) Long authorId,
            @RequestParam(value = "step",required = true) Integer step,
            @RequestParam(value = "lastUpdateDate",required = false) Date lastUpdateDate,
            @RequestParam(value = "category",required = false) String category
    );
}
