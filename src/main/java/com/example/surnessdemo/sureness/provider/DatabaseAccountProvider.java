package com.example.surnessdemo.sureness.provider;

import com.example.surnessdemo.service.AccountService;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zxq
 * @date 2022/3/2 9:00 下午
 * @description:
 */
@Component
public class DatabaseAccountProvider implements SurenessAccountProvider {

    @Resource
    AccountService accountService;

    /**
     * 启动时注入processorManager,我们在配置类中手动增加了passwordProcessor时
     * 便绑定了此DatabaseAccountProvider
     * processorManager进行processor时如果匹配到passwordProcessor便会执行该方法，
     * 加载库account信息进行认证。
     */
    @Override
    public SurenessAccount loadAccount(String appId) {
        return accountService.loadAccount(appId);
    }
}
