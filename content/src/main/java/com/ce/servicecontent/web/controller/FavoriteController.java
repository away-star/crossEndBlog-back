package com.ce.servicecontent.web.controller;


import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ce.servicecommon.domain.dto.TokenClaim;
import com.ce.servicecommon.domain.vo.Result;
import com.ce.servicecommon.exception.BusinessException;
import com.ce.servicecommon.util.JwtUtil;
import com.ce.servicecontent.domain.entity.Favorite;
import com.ce.servicecontent.service.FavoriteService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Null;
import org.springframework.web.bind.annotation.*;

import static com.ce.servicecommon.constant.Security.deleted;
import static com.ce.servicecommon.msg.CommonCodeMsg.DATABASE_ERROR;
import static com.ce.servicecommon.msg.CommonCodeMsg.ILLEGAL_OPERATION;

/**
 * <p>
 * 点赞信息 前端控制器
 * </p>
 *
 * @author xingxing
 * @since 2023-10-02
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    @PostMapping("/action/{targetId}")
    public Result<Null> action(@PathVariable Long targetId, HttpServerRequest request) {
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long securityInfoId = tokenClaim.getSecurity_info_id();
        Favorite favorite = new Favorite();
        favorite.setTargetId(targetId);
        favorite.setSecurityInfoId(securityInfoId);
        if (!favoriteService.save(favorite)) {
            throw new BusinessException(DATABASE_ERROR);
        } else {
            return Result.defaultSuccess();
        }
    }

    @DeleteMapping("/action/{targetId}")
    public Result<Null> deleteAction(@PathVariable Long targetId, HttpServerRequest request) {
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long securityInfoId = tokenClaim.getSecurity_info_id();
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<Favorite>().eq(Favorite::getSecurityInfoId, securityInfoId).eq(Favorite::getTargetId, targetId);
        Favorite one = favoriteService.getOne(wrapper);
        if (one == null) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        one.setDeleteFlag(deleted);
        if (!favoriteService.updateById(one)) {
            throw new BusinessException(DATABASE_ERROR);
        } else {
            return Result.defaultSuccess();
        }
    }
}

