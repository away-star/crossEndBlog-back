package com.star.servicecontent.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.servicecommon.domain.Result;
import com.star.servicecontent.entity.Post;
import com.star.servicecontent.entity.dto.PostDto;
import com.star.servicecontent.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * post表 前端控制器
 * </p>
 *
 * @author star
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {

    @Resource
    private PostService postService;

    @GetMapping("/page")
    public Result<Page> postPage(@RequestParam(required = true) Integer step,
                                 @RequestParam(required = false) Date lastUpdateDate,
                                 @RequestParam(required = false) String category) {
        log.error(step.toString());
        Page<Post> postPage = new Page<>(0,step);
        LambdaQueryWrapper<Post> postWrapper = new LambdaQueryWrapper<>();
        postWrapper.eq(category!=null,Post::getCategory,category).eq(Post::isOpen,true).lt(lastUpdateDate != null, Post::getUpdateTime, lastUpdateDate).orderByDesc(Post::getUpdateTime);
        Page<Post> page = postService.page(postPage, postWrapper);
        return Result.success(page);
    }


    @GetMapping("/{id}")
    public Result<PostDto> postDetail(@PathVariable Integer id) {
       // Integer postId = Integer.valueOf(id);
        return Result.success(postService.getDetail(id));
    }

    @PostMapping()
   // @PreAuthorize("hasAuthority('30001')")
    public Result<String> post(@NotNull @RequestBody Post post) {
        /*SecurityUtil.UserInfo user = SecurityUtil.getUser();
        if (user==null){
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        Long id = user.getId();
        post.setAuthorId(id);*/
        post.setAuthorId(123L);
        if (postService.post(post)){
            return Result.success();
        }else {
            return Result.defaultError();
        }
    }
}

