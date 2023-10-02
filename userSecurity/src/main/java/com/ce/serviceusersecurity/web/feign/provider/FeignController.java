//package com.ce.serviceusersecurity.web.feign.provider;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.ce.serviceusersecurity.domain.entity.Userinfo;
//import com.ce.serviceusersecurity.service.UserinfoService;
//import com.ce.servicecommon.domain.vo.Result;
//import io.swagger.v3.oas.annotations.Parameter;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Tag;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @author xingxing
// * @date 2023/4/21 19:11
// */
//
//@RestController
//@RequestMapping("/feign")
//@Slf4j
//public class FeignController {
//    @Resource
//    private UserinfoService userInfoService;
//
//    //@ApiOperation(response = InitialArgs.class, value = "feign内部接口获取用户信息")
//    @Tag(value = "feign内部接口获取用户信息")
//    @Parameter(name = "securityInfoId", description = "登录信息id")
//    @GetMapping("/userInfos")
//    public Result<List<Userinfo>> getUserInfos(@RequestParam(required = true) List<Long> ids) {
//        log.error("securityInfoId:{}", ids);
//        LambdaQueryWrapper<Userinfo> userinfoWrapper = new LambdaQueryWrapper<>();
//        userinfoWrapper.in(Userinfo::getSecurityInfoId, ids);
//        List<Userinfo> userInfos = userInfoService.list(userinfoWrapper);
//        log.error("userInfos:{}", userInfos);
//        return Result.success(userInfos);
//    }
//}
