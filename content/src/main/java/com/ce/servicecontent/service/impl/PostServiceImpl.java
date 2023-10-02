package com.ce.servicecontent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ce.servicecommon.exception.BusinessException;
import com.ce.servicecontent.domain.entity.Comment;
import com.ce.servicecontent.domain.entity.Favorite;
import com.ce.servicecontent.domain.entity.Post;
import com.ce.servicecontent.domain.entity.Reply;
import com.ce.servicecontent.domain.vo.CommentVo;
import com.ce.servicecontent.domain.vo.PostDetailVo;
import com.ce.servicecontent.mapper.PostMapper;
import com.ce.servicecontent.service.CommentService;
import com.ce.servicecontent.service.FavoriteService;
import com.ce.servicecontent.service.PostService;
import com.ce.servicecontent.service.ReplyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ce.servicecommon.constant.Security.alive;
import static com.ce.servicecommon.msg.CommonCodeMsg.ILLEGAL_OPERATION;

/**
 * <p>
 * post表 服务实现类
 * </p>
 *
 * @author xingxing
 * @since 2023-02-06
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {


    @Resource
    private CommentService commentService;

    @Resource
    private ReplyService replyService;

    @Resource
    private FavoriteService favoriteService;

    @Override
    @Transactional
    public PostDetailVo getPostDetailVo(Integer postId) {
        Post post = this.getById(postId);
        log.error(post.toString());
        if (post.getDeleteFlag() == 1) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, postId).eq(Comment::getDeleteFlag, alive).orderByDesc(Comment::getUpdateTime).last("limit " + 5);
        List<Comment> commentList = commentService.list(wrapper);
        List<Long> commentIds = commentList.stream().map(Comment::getId).toList();
        ArrayList<CommentVo> commentVoList = new ArrayList<>();
        for (Long commentId : commentIds) {
            LambdaQueryWrapper<Reply> reply = new LambdaQueryWrapper<Reply>().eq(Reply::getDeleteFlag, alive).eq(Reply::getTargetId, commentId).orderByDesc(Reply::getUpdateTime).last("limit " + 5);

        }
        for (Comment comment : commentList) {
            LambdaQueryWrapper<Reply> wrapper1 = new LambdaQueryWrapper<Reply>().eq(Reply::getDeleteFlag, alive).eq(Reply::getTargetId, comment.getId()).orderByDesc(Reply::getUpdateTime).last("limit " + 5);
            List<Reply> replies = replyService.list(wrapper1);
            LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper = new LambdaQueryWrapper<Favorite>().eq(Favorite::getTargetId, comment.getId()).eq(Favorite::getDeleteFlag, alive);
            int favoriteCount = (int)favoriteService.count(favoriteLambdaQueryWrapper);
            CommentVo commentVo = new CommentVo();
            commentVo.setReplies(replies);
            commentVo.setFavoriteCount(favoriteCount);
            commentVoList.add(commentVo);
        }
        LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper = new LambdaQueryWrapper<Favorite>().eq(Favorite::getTargetId, postId).eq(Favorite::getDeleteFlag, alive);
        int favoriteCount = (int)favoriteService.count(favoriteLambdaQueryWrapper);
        PostDetailVo postDetailVo = new PostDetailVo();
        postDetailVo.setPost(post);
        postDetailVo.setFavoriteCount(favoriteCount);
        postDetailVo.setCommentVos(commentVoList);
        return postDetailVo;
    }
}
