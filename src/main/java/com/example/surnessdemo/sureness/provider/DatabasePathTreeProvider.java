package com.example.surnessdemo.sureness.provider;

import com.example.surnessdemo.service.ResourceService;
import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.util.SurenessCommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author zxq
 * @date 2022/3/2 9:00 下午
 * @description:
 */
@Component
public class DatabasePathTreeProvider implements PathTreeProvider {

    @Resource
    private ResourceService resourceService;

    /**
     * 启动时注入SurnessConfiguration时加载 pathRoleMatcher
     * 就执行该方法，加载数据库EnableRoleResource，即status=1 的资源，生成字典树
     */
    @Override
    public Set<String> providePathData() {
        return SurenessCommonUtil.attachContextPath(getContextPath(), resourceService.getAllEnableResourcePath());
    }

    /**
     * 启动时注入SurnessConfiguration时加载 pathRoleMatcher
     * 就执行该方法，加载数据库DisableRoleResource，即status=9 的资源，生成字典树
     */
    @Override
    public Set<String> provideExcludedResource() {
        return SurenessCommonUtil.attachContextPath(getContextPath(), resourceService.getAllDisableResourcePath());
    }

}
