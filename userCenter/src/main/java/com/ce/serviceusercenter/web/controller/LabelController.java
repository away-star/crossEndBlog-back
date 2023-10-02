package com.ce.serviceusercenter.web.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ce.servicecommon.annotations.PreAuth;
import com.ce.servicecommon.domain.dto.TokenClaim;
import com.ce.servicecommon.domain.vo.Result;
import com.ce.servicecommon.util.JwtUtil;
import com.ce.serviceusercenter.domain.dto.LabelRequest;
import com.ce.serviceusercenter.domain.entity.Label;
import com.ce.serviceusercenter.service.LabelService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ce.servicecommon.constant.Security.shareAuthPowerCode;

/**
 * <p>
 * 用户label表 前端控制器
 * </p>
 *
 * @author xingxing
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/label")
public class LabelController {
    @Resource
    private LabelService labelService;

    @PutMapping()
    @PreAuth(auth = shareAuthPowerCode)
    public Result<String> updateLabel(@RequestBody LabelRequest labelRequest) {
        Label label = BeanUtil.copyProperties(labelRequest, Label.class);
        if (labelService.updateById(label)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }

    @GetMapping("/all")
    public Result<List<Label>> getAll(@RequestParam(required = false) Long security_info_id, HttpServerRequest request) {
        if (security_info_id != null) {
            return Result.success(labelService.list(new LambdaQueryWrapper<Label>().eq(Label::getSecurityInfoId, security_info_id).eq(Label::getDeleteFlag, 0)));
        } else {
            TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
            security_info_id = tokenClaim.getSecurity_info_id();
            return Result.success(labelService.list(new LambdaQueryWrapper<Label>().eq(Label::getSecurityInfoId, security_info_id).eq(Label::getDeleteFlag, 0)));
        }
    }


    @DeleteMapping("/{id}")
    // @PreAuthorize("hasAuthority('30005')")
    public Result<String> deleteLabel(@PathVariable Long id) {
        if (labelService.removeById(id)) {
            return Result.defaultSuccess();
        }
        return Result.defaultError();
    }

    @PostMapping("/add")
    public Result<String> putLabel(@RequestBody LabelRequest labelRequest, HttpServerRequest request) {
        Label label = BeanUtil.copyProperties(labelRequest, Label.class);
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long security_info_id = tokenClaim.getSecurity_info_id();
        label.setSecurityInfoId(security_info_id);
        if (labelService.save(label)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }
}

