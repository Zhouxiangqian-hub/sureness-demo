package com.example.surnessdemo.dao;

import com.example.surnessdemo.SuperTest;
import com.example.surnessdemo.pojo.entity.AuthUserDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author zxq
 * @date 2022/3/2 9:00 下午
 * @description:
 */
@Slf4j
public class AuthUserDaoTest extends SuperTest {

    @Resource
    private AuthUserDao authUserDao;

    @Test
    void contextLoads() {
        Optional<AuthUserDO> authUserOptional = authUserDao.findAuthUserByUsername("admin");
        if (authUserOptional.isPresent()) {
            log.info("123");
        }
        AuthUserDO authUser = authUserOptional.get();


    }

}
