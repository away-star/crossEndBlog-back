package com.star.serviceuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.serviceuser.domain.entity.UserInfo;
import com.star.servicecommon.exception.BusinessException;
import com.star.servicecommon.util.SecurityUtil;
import com.star.serviceuser.domain.dto.BlogSetting;
import com.star.serviceuser.domain.dto.UserInfoDto;
import com.star.serviceuser.domain.entity.*;
import com.star.serviceuser.domain.vo.InitialArgs;
import com.star.serviceuser.mapper.UserInfoMapper;
import com.star.serviceuser.service.*;
import com.star.serviceuser.web.feign.ContentFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.star.servicecommon.msg.CommonCodeMsg.METHOD_ARGUMENT_IN_VALID;
import static com.star.serviceuser.constant.Authority.initialAvatar;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author star
 * @since 2023-01-30
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private ProverbsService proverbsService;
    @Resource
    private LoginInformationService loginInformationService;
    @Resource
    private PowerService powerService;
    @Resource
    private User2powerService user2powerService;

    @Resource
    private ContentFeignApi contentFeignApi;

    @Resource
    private LabelService labelService;


//    @Override
//    public InitialArgs getInitial(Long userId) {
//        Proverbs randProverb = proverbsMapper.getRandProverbs(userId);
//        UserInfo one = this.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getLoginInformationId, userId));
//        InitialArgs userDetail = new InitialArgs();
//        BeanUtil.copyProperties(one, userDetail);
//        userDetail.setProverb(randProverb);
//        return userDetail;
//    }

//    @Override
//    public InitialArgs getInitial(String email) {
//        Proverbs randProverb = proverbsMapper.getRandProverbs(email);
//
//        UserInfo one = this.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::, userId));
//        InitialArgs userDetail = new InitialArgs();
//        BeanUtil.copyProperties(one, userDetail);
//        userDetail.setProverb(randProverb);
//        return null;
//    }


    // todo 根据用户id博客初始数据
    @Override
    public InitialArgs getInitial(Long loginInformationId) {
        log.error("loginInformationId:{}", loginInformationId);
        //todo 一句sql先拿到用户数据（有问题 需要改）
        //Map<String, Object> userInitial = userInfoMapper.getInitial(loginInformationId);
        // todo 先暂时这样用（sql写不出）
        List<Proverbs> proverbsListlist = proverbsService.list(new LambdaQueryWrapper<Proverbs>().eq(Proverbs::getLoginInformationId, loginInformationId));
        log.error("list:{}", proverbsListlist);
        LoginInformation UAA = loginInformationService.getById(loginInformationId);
        List<Label> labels = labelService.list(new LambdaQueryWrapper<Label>().eq(Label::getLoginInformationId, loginInformationId));
        log.error("UAA:{}", UAA);
        UAA.setPassword(null);
        List<User2power> user2powerList = user2powerService.list(new LambdaQueryWrapper<User2power>().eq(User2power::getUserId, loginInformationId));
        List<Power> powers = powerService.listByIds(user2powerList.stream().map(User2power::getPowerId).collect(Collectors.toList()));
        log.error("user2powerList:{}", user2powerList);
        UserInfo userInfo = this.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getLoginInformationId, loginInformationId));
        log.error("userInfo:{}", userInfo);

        InitialArgs initialArgs = new InitialArgs();
        initialArgs.setLabels(labels);
        initialArgs.setLoginInformation(UAA);
        UserInfoDto userInfoDto = modify(userInfo);
        initialArgs.setUserInfoDto(userInfoDto);
        initialArgs.setProverbs(proverbsListlist);
        initialArgs.setPower(powers);
        log.error("initialArgs:{}", initialArgs);
        return initialArgs;
    }


    private UserInfoDto modify(UserInfo userInfo) {
        String slides = userInfo.getSlideShow();
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo, userInfoDto);
        if (slides != null && slides.length() > 0) {
            String[] split = slides.split(";");
            userInfoDto.setSlideVenue(split);
        }
        return userInfoDto;
    }


    @Override
    public UserInfo initialInfo(Long userId, String registerIp) {
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginInformationId(userId);
        userInfo.setAvatar(initialAvatar);
        userInfo.setNickname("用户" + UUID.randomUUID().toString().substring(1, 5));
        this.save(userInfo);
        return userInfo;
    }

    @Override
    public boolean updateInfo(UserInfo userInfo) {
        LambdaQueryWrapper<UserInfo> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(UserInfo::getLoginInformationId, userInfo.getLoginInformationId());
        return this.update(userInfo, userWrapper);


//        String[] labelVenue = userInfoDto.getLabelVenue();
//        String[] slideVenue = userInfoDto.getSlideVenue();
//        if (labelVenue != null && labelVenue.length > 0) {
//            StringBuilder s = new StringBuilder();
//            for (String label : labelVenue) {
//                if (label.length() == 0 || label.contains(";")) {
//                    throw new BusinessException(METHOD_ARGUMENT_IN_VALID);
//                }
//                s.append(label).append(';');
//            }
//            userInfoDto.setLabels(s.toString());
//        }
//
//        if (slideVenue != null && labelVenue.length > 0) {
//            StringBuilder s = new StringBuilder();
//            for (String label : labelVenue) {
//                if (label.length() == 0 || label.contains(";")) {
//                    throw new BusinessException(METHOD_ARGUMENT_IN_VALID);
//                }
//                s.append(label).append(';');
//            }
//            userInfoDto.setSlideShow(s.toString());
//        }
//        userInfoDto.setId(this.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getLoginInformationId, userInfoDto.getLoginInformationId())).getId());
//        return this.updateById(userInfoDto);
    }

    @Override
    @Transactional
    public boolean updateBlogSetting(BlogSetting blogSetting) {

        //更改首页展示图
        String[] slides = blogSetting.getSlides();
        if (slides.length < 1) {
            throw new BusinessException(METHOD_ARGUMENT_IN_VALID);
        }

        log.error("slides:{}", slides);

        StringBuilder s = new StringBuilder();
        for (String slide : slides) {
            if (slide.length() == 0 || slide.contains(";")) {
                throw new BusinessException(METHOD_ARGUMENT_IN_VALID);
            }
            s.append(slide).append(';');
        }

        Long id = Objects.requireNonNull(SecurityUtil.getUser()).getId();
        UserInfo userInfo = this.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getLoginInformationId, id));
        userInfo.setSlideShow(s.toString());
        this.updateById(userInfo);

        //更改标签
        Label[] labels = blogSetting.getLabels();
        if (labels.length < 1) {
            throw new BusinessException(METHOD_ARGUMENT_IN_VALID);
        }
        for (Label label : labels) {
            if (label.getId() != null) {
                labelService.updateById(label);
            } else {
                label.setLoginInformationId(id);
                labelService.save(label);
            }
        }

        //更改首页语录
        Proverbs[] proverbs = blogSetting.getProverbs();
        for (Proverbs proverb : proverbs) {
            if (proverb.getId() != null) {
                proverbsService.updateById(proverb);
            } else {
                if (proverb.getCreatePeople() == null) {
                    proverb.setCreatePeople("匿名");
                }
                proverb.setLoginInformationId(id);
                proverbsService.save(proverb);
            }
        }
        return true;
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        List<UserInfo> list = this.list();
        List<UserInfoDto> collect = list.stream().map(this::modify).collect(Collectors.toList());
        return collect;
    }
}
