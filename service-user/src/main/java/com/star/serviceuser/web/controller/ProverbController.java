package com.star.serviceuser.web.controller;

import com.example.servicecommon.domain.Result;
import com.example.servicecommon.util.SecurityUtil;
import com.star.serviceuser.domain.entity.Proverbs;
import com.star.serviceuser.service.ProverbsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author star
 * @date 2023/2/6 21:36
 */

@RestController
@RequestMapping("/proverbs")
@Slf4j
public class ProverbController {

    @Resource
    private ProverbsService proverbsService;

    @PutMapping()
    @PreAuthorize("hasAuthority('30005')")
    public Result<String> updateProverb(@RequestBody Proverbs proverb) {

        if (proverbsService.updateById(proverb)){
            return Result.success();
        }else {
            return Result.defaultError();
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('30005')")
    public Result<List<Proverbs>> getAll(){
        return Result.success(proverbsService.list());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('30005')")
    public Result<String> deleteProverb(@PathVariable Long id) {
        if (proverbsService.removeById(id)){
            return Result.success();
        }
        return Result.defaultError();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('30005')")
    public Result<String> putProverb(@RequestBody Proverbs proverb) {
        Long id = SecurityUtil.getUser().getId();

        if (StringUtils.isBlank(proverb.getCreatePeople())){
            proverb.setCreatePeople("star");
        }

        proverb.setLoginInformationId(id);

        if (proverbsService.save(proverb)){
            return Result.success();
        }else {
            return Result.defaultError();
        }
    }
}
