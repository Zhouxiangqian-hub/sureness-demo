package com.example.surnessdemo.dao;

import com.example.surnessdemo.SuperTest;
import com.example.surnessdemo.pojo.entity.AuthRoleDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author zxq
 * @date 2022/3/13 1:37 上午
 * @description:
 */
@Slf4j
public class AuthRoleDaoTest extends SuperTest {
    @Resource
    AuthRoleDao authRoleDao;

    @Test
    public void selectById() {
        Long id = 1L;
        AuthRoleDO authRoleDO = authRoleDao.selectById(id);
        System.out.println(authRoleDO);
    }
}
