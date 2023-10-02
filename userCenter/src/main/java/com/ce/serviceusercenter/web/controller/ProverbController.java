package com.ce.serviceusercenter.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ce.servicecommon.annotations.PreAuth;
import com.ce.servicecommon.domain.dto.TokenClaim;
import com.ce.servicecommon.domain.vo.Result;
import com.ce.servicecommon.util.JwtUtil;
import com.ce.serviceusercenter.domain.dto.ProverbRequest;
import com.ce.serviceusercenter.domain.entity.Proverb;
import com.ce.serviceusercenter.service.ProverbService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ce.servicecommon.constant.Security.shareAuthPowerCode;

/**
 * @author xingxing
 * @date 2023/2/6 21:36
 */
@RestController
@RequestMapping("/Proverb")
@Slf4j
public class ProverbController {

    @Resource
    private ProverbService proverbService;

    @PutMapping()
    @PreAuth(auth = shareAuthPowerCode)
    public Result<String> updateProverb(@RequestBody ProverbRequest proverbRequest) {
        Proverb proverb = BeanUtil.copyProperties(proverbRequest, Proverb.class);
        if (proverbService.updateById(proverb)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }

    @GetMapping("/all")
    public Result<List<Proverb>> getAll(@RequestParam(required = false) Long security_info_id, HttpServerRequest request) {
        if (security_info_id != null) {
            return Result.success(proverbService.list(new LambdaQueryWrapper<Proverb>().eq(Proverb::getSecurityInfoId, security_info_id).eq(Proverb::getIsActive, 1).eq(Proverb::getDeleteFlag, 0)));
        } else {
            TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
            security_info_id = tokenClaim.getSecurity_info_id();
            return Result.success(proverbService.list(new LambdaQueryWrapper<Proverb>().eq(Proverb::getSecurityInfoId, security_info_id).eq(Proverb::getIsActive, 1).eq(Proverb::getDeleteFlag, 0)));
        }
    }


    @DeleteMapping("/{id}")
    // @PreAuthorize("hasAuthority('30005')")
    public Result<String> deleteProverb(@PathVariable Long id) {
        if (proverbService.removeById(id)) {
            return Result.defaultSuccess();
        }
        return Result.defaultError();
    }

    @PostMapping("/add")
    public Result<String> putProverb(@RequestBody ProverbRequest proverbRequest, HttpServerRequest request) {
        Proverb proverb = BeanUtil.copyProperties(proverbRequest, Proverb.class);
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long security_info_id = tokenClaim.getSecurity_info_id();
        proverb.setSecurityInfoId(security_info_id);
        if (proverbService.save(proverb)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }
}
