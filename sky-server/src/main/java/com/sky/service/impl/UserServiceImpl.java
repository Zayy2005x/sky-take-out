package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.constant.WeChatLoginConstant;
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


    public static final String WX_LOGIN ="https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO);

        //判断openid是否为空
        if(openid == null){
            //抛出登录失败异常
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断用户是否为新用户
        User user = userMapper.getByOpenId(openid);

        //若为新用户进行注册
        if(user == null){
            //插入信息到数据库中
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        //返回数据
        return user;
    }

    /**
     * 获得openid
     * @param userLoginDTO
     * @return
     */
    private String getOpenid(UserLoginDTO userLoginDTO) {
        //调用微信开发者服务器接口,获得用户的openid
        Map<String, String> map = new HashMap<>();
        map.put(WeChatLoginConstant.APPID,weChatProperties.getAppid());
        map.put(WeChatLoginConstant.SECRET,weChatProperties.getSecret());
        map.put(WeChatLoginConstant.JS_CODE,userLoginDTO.getCode());
        map.put(WeChatLoginConstant.GRANT_TYPE,"authorization_code");   //授权类型
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        //解析json,获得openid
        JSONObject jsonObject = JSONObject.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
