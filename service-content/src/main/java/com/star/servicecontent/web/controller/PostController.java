package com.star.servicecontent.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.star.servicecommon.domain.Result;
import com.star.servicecommon.exception.BusinessException;
import com.star.servicecommon.util.SecurityUtil;
import com.star.servicecontent.entity.Post;
import com.star.servicecontent.entity.dto.PostDto;
import com.star.servicecontent.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static com.star.servicecommon.msg.CommonCodeMsg.ILLEGAL_OPERATION;

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
    public Result<Page<Post>> postPage(
            @RequestParam(required = true) Long authorId,
            @RequestParam(required = true) Integer step,
            @RequestParam(required = false) String lastUpdateDate,
            @RequestParam(required = false) String category
    ) {
        log.error("step " + step.toString());
        LocalDateTime parseLastUpdateDate = null;
        if (lastUpdateDate != null){
            log.error("lastUpdateDate " + lastUpdateDate.toString());
            //将lastUpdateDate转换为Date
            parseLastUpdateDate = LocalDateTime.parse(lastUpdateDate);
        }


        log.error("category " + category);
        Page<Post> postPage = new Page<>(1, step);
        LambdaQueryWrapper<Post> postWrapper = new LambdaQueryWrapper<>();
        postWrapper.eq(category != null, Post::getCategory, category)
                .eq(Post::getAuthorId, authorId)
                .eq(Post::isOpen, true)
                .lt(lastUpdateDate != null, Post::getUpdateTime, parseLastUpdateDate)
//                .le(lastUpdateDate != null, Post::getUpdateTime, parseLastUpdateDate)
                .orderByDesc(Post::getUpdateTime);
        postPage = postService.page(postPage, postWrapper);
        log.error(postPage.toString());
        return Result.success(postPage);
    }


    @GetMapping("/{id}")
    public Result<PostDto> postDetail(@PathVariable Integer id) {
        // Integer postId = Integer.valueOf(id);
        return Result.success(postService.getDetail(id));
    }

    @PostMapping()
    // @PreAuthorize("hasAuthority('30001')")
    public Result<String> post(@NotNull @RequestBody Post post) {
        SecurityUtil.UserInfo user = SecurityUtil.getUser();
        if (user == null) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        Long id = user.getId();
        log.error(id.toString());
        post.setAuthorId(id);
        if (postService.post(post)) {
            return Result.success();
        } else {
            return Result.defaultError();
        }
    }


}

