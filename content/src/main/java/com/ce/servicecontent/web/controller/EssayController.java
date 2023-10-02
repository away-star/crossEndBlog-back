package com.ce.servicecontent.web.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ce.servicecommon.domain.dto.TokenClaim;
import com.ce.servicecommon.domain.vo.Result;
import com.ce.servicecommon.exception.BusinessException;
import com.ce.servicecommon.util.JwtUtil;
import com.ce.servicecontent.domain.dto.EssayRequest;
import com.ce.servicecontent.domain.entity.Essay;
import com.ce.servicecontent.domain.entity.Favorite;
import com.ce.servicecontent.domain.vo.EssayVo;
import com.ce.servicecontent.service.EssayService;
import com.ce.servicecontent.service.FavoriteService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static com.ce.servicecommon.constant.Security.alive;
import static com.ce.servicecommon.constant.Security.deleted;
import static com.ce.servicecommon.msg.CommonCodeMsg.ILLEGAL_OPERATION;

/**
 * <p>
 * essay表 前端控制器
 * </p>
 *
 * @author xingxing
 * @since 2023-02-12
 */
@RestController
@Slf4j
@RequestMapping("/essay")
public class EssayController {

    @Resource
    private FavoriteService favoriteService;
    @Resource
    private EssayService essayService;

    @PostMapping()
    public Result<String> saveEssay(@RequestBody EssayRequest essayRequest, HttpServerRequest request) {
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long securityInfoId = tokenClaim.getSecurity_info_id();
        String[] coverUrls = essayRequest.getCoverUrls();
        StringBuilder s = new StringBuilder();
        if (coverUrls != null) {
            for (String url : coverUrls) {
                s.append(url).append(";");
            }
        }
        Essay essay = BeanUtil.copyProperties(essayRequest, Essay.class);
        essay.setCoverUrl(s.toString());
        essay.setAuthorId(securityInfoId);
        if (essayService.save(essay)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }


    @GetMapping("/page")
    public Result<Page<EssayVo>> essayPage(
            @RequestParam(required = true) Long authorId,
            @RequestParam(required = true) Integer step,
            @RequestParam(required = false) Date lastUpdateDate) {
        log.error("step " + step.toString());
        LocalDateTime parseLastUpdateDate = null;
        Page<Essay> essayPage = new Page<>(1, step);
        LambdaQueryWrapper<Essay> essayWrapper = new LambdaQueryWrapper<>();
        essayWrapper
                .eq(Essay::getAuthorId, authorId)
                .eq(Essay::getDeleteFlag, alive)
                .lt(lastUpdateDate != null, Essay::getUpdateTime, parseLastUpdateDate) //小于
                .orderByDesc(Essay::getUpdateTime);
        essayPage = essayService.page(essayPage, essayWrapper);
        Page<EssayVo> essayPageRes = new Page<>(1, step);
        BeanUtil.copyProperties(essayPage, essayPageRes);
        for (EssayVo essayVo : essayPageRes.getRecords()) {
            LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper = new LambdaQueryWrapper<Favorite>().eq(Favorite::getTargetId, essayVo.getId()).eq(Favorite::getDeleteFlag, alive);
            int count = (int) favoriteService.count(favoriteLambdaQueryWrapper);
            essayVo.setFavoriteCount(count);
        }
        log.error(essayPage.toString());
        return Result.success(essayPageRes);
    }


    //编辑发布过的essay
    @PutMapping()
    public Result<Null> updateEssay(@RequestBody EssayRequest essayRequest, HttpServerRequest request) {
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long securityInfoId = tokenClaim.getSecurity_info_id();
        Essay essay = BeanUtil.copyProperties(essayRequest, Essay.class);
        if (!Objects.equals(securityInfoId, essay.getAuthorId())) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        if (essayService.updateById(essay)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }

    @DeleteMapping("/{essayId}")
    public Result<String> deleteEssay(@PathVariable Integer essayId, HttpServerRequest request) {
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long securityInfoId = tokenClaim.getSecurity_info_id();
        Essay essay = essayService.getById(essayId);
        if (!Objects.equals(securityInfoId, essay.getAuthorId())) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        essay.setDeleteFlag(deleted);
        if (essayService.updateById(essay)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }
}

