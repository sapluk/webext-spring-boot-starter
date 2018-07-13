package com.pugwoo.webextstarterdemo.service;

import com.pugwoo.webextstarterdemo.entity.UserDO;

/**
 * 演示用
 * @date 2018-07-12
 */
public interface IUserService {

    boolean insertOrUpdate(UserDO userDO);
}
