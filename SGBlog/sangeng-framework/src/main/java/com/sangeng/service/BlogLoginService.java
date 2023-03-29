package com.sangeng.service;

import com.sangeng.demo.ResponseResult;
import com.sangeng.demo.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
