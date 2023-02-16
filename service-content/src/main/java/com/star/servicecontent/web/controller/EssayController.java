package com.star.servicecontent.web.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.servicecommon.domain.Result;
import com.star.servicecontent.entity.Essay;
import com.star.servicecontent.entity.dto.EssayDto;
import com.star.servicecontent.service.EssayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * essay表 前端控制器
 * </p>
 *
 * @author star
 * @since 2023-02-12
 */
@RestController
@Slf4j
@RequestMapping("/essay")
public class EssayController {

    @Resource
    private EssayService essayService;

    @PostMapping()
    // @PreAuthorize("hasAuthority('30001')")
    public Result<String> post(@NotNull @RequestBody EssayDto essay) {
        /*SecurityUtil.UserInfo user = SecurityUtil.getUser();
        if (user==null){
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        Long id = user.getId();
        post.setAuthorId(id);*/
        essay.setAuthorId(123L);

        //对图片进行处理
        String[] urls = essay.getUrls();
        StringBuilder s = new StringBuilder();
        for (String url : urls) {
            s.append(url).append(' ');
        }
        essay.setCoverUrl(String.valueOf(s));

        if (essayService.save(essay)){
            return Result.success();
        }else {
            return Result.defaultError();
        }
    }

    @GetMapping("/page")
    public Result<Page> postPage(@RequestParam(required = true) Integer step,
                                       @RequestParam(required = false) Date lastUpdateDate,
                                       @RequestParam(required = false) String category) {
        log.error(step.toString());
        Page<Essay> postPage = new Page<>(0,step);
        LambdaQueryWrapper<Essay> essayWrapper = new LambdaQueryWrapper<>();
        essayWrapper.eq(Essay::isOpen,true).lt(lastUpdateDate != null, Essay::getUpdateTime, lastUpdateDate).orderByDesc(Essay::getUpdateTime);
        Page<Essay> page = essayService.page(postPage, essayWrapper);

        List<Essay> records = page.getRecords();

        //将url从字符串变为数组
        List<EssayDto> list=records.stream().map((item)->{
            EssayDto essayDto = new EssayDto();
            BeanUtil.copyProperties(item,essayDto);
            String[] urls = StringUtils.split(item.getCoverUrl(), ' ');
            essayDto.setUrls(urls);
            return essayDto;
        }).collect(Collectors.toList());
        Page<EssayDto> rPage = new Page<>();
        //拷贝属性
        BeanUtils.copyProperties(page,rPage,"records");
        rPage.setRecords(list);
        return Result.success(rPage);
    }
}

