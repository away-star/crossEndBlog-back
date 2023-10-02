package com.ce.servicecontent.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ce.servicecommon.domain.dto.TokenClaim;
import com.ce.servicecommon.domain.vo.Result;
import com.ce.servicecommon.exception.BusinessException;
import com.ce.servicecommon.util.JwtUtil;
import com.ce.servicecontent.domain.dto.PostRequest;
import com.ce.servicecontent.domain.entity.Favorite;
import com.ce.servicecontent.domain.entity.Post;
import com.ce.servicecontent.domain.vo.PostDetailVo;
import com.ce.servicecontent.domain.vo.PostSimpleVo;
import com.ce.servicecontent.service.FavoriteService;
import com.ce.servicecontent.service.PostService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.ce.servicecommon.constant.Security.alive;
import static com.ce.servicecommon.constant.Security.deleted;
import static com.ce.servicecommon.msg.CommonCodeMsg.ILLEGAL_OPERATION;

/**
 * <p>
 * post表 前端控制器
 * </p>
 *
 * @author xingxing
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {

    @Resource
    private PostService postService;

    @Resource
    private FavoriteService favoriteService;


    @GetMapping("/page")
    public Result<Page<PostSimpleVo>> postPage(
            @RequestParam(required = true) Long authorId,
            @RequestParam(required = true) Integer step,
            @RequestParam(required = false) String lastUpdateDate,
            @RequestParam(required = false) String category
    ) {
        log.error("step " + step.toString());
        LocalDateTime parseLastUpdateDate = null;
        if (lastUpdateDate != null) {
            log.error("lastUpdateDate " + lastUpdateDate);
            //将lastUpdateDate转换为Date
            parseLastUpdateDate = LocalDateTime.parse(lastUpdateDate);
        }
        log.error("category " + category);
        Page<Post> postPage = new Page<>(1, step);
        LambdaQueryWrapper<Post> postWrapper = new LambdaQueryWrapper<>();
        postWrapper.eq(category != null, Post::getCategory, category)
                .eq(Post::getAuthorId, authorId)
                .eq(Post::getDeleteFlag, true)
                .lt(lastUpdateDate != null, Post::getUpdateTime, parseLastUpdateDate) //小于
//                .le(lastUpdateDate != null, Post::getUpdateTime, parseLastUpdateDate)
                .orderByDesc(Post::getUpdateTime);
        postPage = postService.page(postPage, postWrapper);
        Page<PostSimpleVo> postPageRes = new Page<>(1, step);
        BeanUtil.copyProperties(postPage, postPageRes);
        List<PostSimpleVo> records = postPageRes.getRecords();
        for (PostSimpleVo postSimpleVo : records) {
            LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper = new LambdaQueryWrapper<Favorite>().eq(Favorite::getTargetId, postSimpleVo.getId()).eq(Favorite::getDeleteFlag, alive);
            int count = (int) favoriteService.count(favoriteLambdaQueryWrapper);
            postSimpleVo.setFavoriteCount(count);
        }
        log.error(postPage.toString());
        return Result.success(postPageRes);
    }


    @GetMapping("/{postId}")
    public Result<PostDetailVo> postDetail(@PathVariable Integer postId) {
        // Integer postId = Integer.valueOf(id);
        return Result.success(postService.getPostDetailVo(postId));
    }

    @PostMapping()
    public Result<String> post(@NotNull @RequestBody PostRequest PostRequest, HttpServerRequest request) {
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long securityInfoId = tokenClaim.getSecurity_info_id();
        Post post = BeanUtil.copyProperties(PostRequest, Post.class);
        log.error(securityInfoId.toString());
        post.setAuthorId(securityInfoId);
        if (postService.save(post)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }

    //编辑发布过的post
    @PutMapping()
    public Result<String> updatePost(@RequestBody PostRequest postRequest, HttpServerRequest request) {
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long securityInfoId = tokenClaim.getSecurity_info_id();
        Post post = BeanUtil.copyProperties(postRequest, Post.class);
        if (!Objects.equals(securityInfoId, post.getAuthorId())) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        if (postService.updateById(post)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }

    @DeleteMapping("/{postId}")
    public Result<String> deletePost(@PathVariable Integer postId, HttpServerRequest request) {
        TokenClaim tokenClaim = JwtUtil.parseJWT(request.getHeader("Authorization"));
        Long securityInfoId = tokenClaim.getSecurity_info_id();
        Post post = postService.getById(postId);
        if (!Objects.equals(securityInfoId, post.getAuthorId())) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        post.setDeleteFlag(deleted);
        if (postService.updateById(post)) {
            return Result.defaultSuccess();
        } else {
            return Result.defaultError();
        }
    }
}

