package com.sangeng.controller;

import com.sangeng.Exception.SystemException;
import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.entity.User;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.service.BlogLoginService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示:必须传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);

        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }

}
