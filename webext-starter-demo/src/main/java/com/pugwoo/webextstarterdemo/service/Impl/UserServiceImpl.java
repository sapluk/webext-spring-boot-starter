package com.pugwoo.webextstarterdemo.service.Impl;

import com.pugwoo.webextstarterdemo.entity.UserDO;
import com.pugwoo.webextstarterdemo.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * 演示用
 *  不进行真正的数据库等操作
 * @date 2018-07-12
 */
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public boolean insertOrUpdate(UserDO userDO) {
        if (userDO == null) {
            return false;
        }
        // 进行数据库等操作
        System.out.println(userDO);
        return true;
    }
}
