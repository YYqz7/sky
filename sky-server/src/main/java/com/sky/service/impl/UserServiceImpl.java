package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final String MINI_PROGRAM_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDTO dto) {

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", weChatProperties.getAppid());
        paramMap.put("secret", weChatProperties.getSecret());
        paramMap.put("js_code", dto.getCode());
        paramMap.put("grant_type", "authorization_code");

        String s = HttpClientUtil.doGet(MINI_PROGRAM_LOGIN_URL, paramMap);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String openID = (String) jsonObject.get("openid");
        if (openID == null || openID.isEmpty()) {
            throw new LoginFailedException(MessageConstant.USER_NOT_LOGIN);
        }

        User user = userMapper.selectByOpenID(openID);
        if (user == null) { // 如果是新用户
            user = new User();
            user.setOpenid(openID);
            user.setCreateTime(LocalDateTime.now());
            user.setName(openID.substring(0, 5));
            userMapper.insert(user);
        }

        return user;
    }
}
