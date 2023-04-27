package com.star.servicecontent.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.star.servicecommon.domain.Result;
import com.star.servicecommon.domain.UserInfo;
import com.star.servicecommon.exception.BusinessException;
import com.star.servicecommon.util.SecurityUtil;
import com.star.servicecontent.entity.Message;
import com.star.servicecontent.entity.vo.MessageVo;
import com.star.servicecontent.service.MessageService;
import com.star.servicecontent.web.feign.UserFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.star.servicecommon.msg.CommonCodeMsg.ILLEGAL_OPERATION;

/**
 * @author star
 * @date 2023/4/20 20:55
 */
@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {

    @Resource
    private MessageService messageService;

    @Resource
    private UserFeignApi userFeignApi;

    @PostMapping()
    // @PreAuthorize("hasAuthority('30001')")
    public Result<String> leaveMessage(@NotNull @RequestBody Message message) {
        SecurityUtil.UserInfo user = SecurityUtil.getUser();
        if (user == null) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        Long id = user.getId();
        message.setOpen(true);
        message.setAuthorId(id);
        if (messageService.save(message)) {
            return Result.success();
        } else {
            return Result.defaultError();
        }
    }

    @PutMapping()
    //@PreAuthorize("hasAuthority('30001')")
    public Result<String> responseMessage(@NotNull @RequestBody Message message) {
        SecurityUtil.UserInfo user = SecurityUtil.getUser();
        if (user == null) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        Long id = user.getId();
        if (!message.getHostId().equals(id)) {
            throw new BusinessException(ILLEGAL_OPERATION);
        }
        if (messageService.updateById(message)) {
            return Result.success();
        } else {
            return Result.defaultError();
        }
    }


    @GetMapping("/page")
    public Result<Page<MessageVo>> messagePage(
            @RequestParam(required = true) Long loginInformationId,
            @RequestParam(required = true) Integer step,
            @RequestParam(required = false) String lastUpdateDate
    ) {
        log.error("请求进来了");
        LocalDateTime parseLastUpdateDate = null;
        if (lastUpdateDate != null) {
            log.error("lastUpdateDate " + lastUpdateDate.toString());
            //将lastUpdateDate转换为Date
            parseLastUpdateDate = LocalDateTime.parse(lastUpdateDate);
        }
        Page<Message> messagePage = new Page<>(1, step);
        Page<MessageVo> page = new Page<>();
        LambdaQueryWrapper<Message> postWrapper = new LambdaQueryWrapper<>();
        postWrapper
                .eq(Message::getHostId, loginInformationId)
                .eq(Message::isOpen, true)
                .lt(lastUpdateDate != null, Message::getUpdateTime, parseLastUpdateDate)
                .orderByDesc(Message::getUpdateTime);
        messagePage = messageService.page(messagePage, postWrapper);
        BeanUtils.copyProperties(messagePage, page, "records");
        List<Message> records = messagePage.getRecords();
        List<Long> ids = records.stream().map(Message::getAuthorId).collect(Collectors.toList());
        Result<List<UserInfo>> userInfos = userFeignApi.getUserInfos(ids);

        log.error("userinfos" + userInfos.getData());
        List<UserInfo> data = userInfos.getData();
        //将用户信息填充到message中
        List<MessageVo> messageVoList = records.stream().map(message -> {
            MessageVo messageVo = new MessageVo();
            BeanUtils.copyProperties(message, messageVo);
            data.forEach(userInfo -> {
                if (userInfo.getLoginInformationId().equals(message.getAuthorId())) {
                    messageVo.setAvatar(userInfo.getAvatar());
                    messageVo.setNickname(userInfo.getNickname());
                }
            });
            log.error("messageVo" + messageVo);
            return messageVo;
        }).collect(Collectors.toList());
        page.setRecords(messageVoList);
        return Result.success(page);
    }
}
