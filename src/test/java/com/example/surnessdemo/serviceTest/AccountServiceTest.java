package com.example.surnessdemo.serviceTest;

import com.example.surnessdemo.SuperTest;
import com.example.surnessdemo.service.AccountService;
import com.usthe.sureness.provider.SurenessAccount;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zxq
 * @date 2022/3/7 12:10 上午
 * @description:
 */
@Slf4j
public class AccountServiceTest extends SuperTest {

    @Autowired
    AccountService accountService;


    @Test
    public void loadAccountTest() {
        String appId = "admin";
        SurenessAccount surenessAccount = accountService.loadAccount(appId);
        log.info("{}",surenessAccount);

    }
}
