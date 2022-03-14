package com.example.surnessdemo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.surnessdemo.SuperTest;
import com.example.surnessdemo.pojo.entity.AuthResourceDO;
import com.example.surnessdemo.service.ResourceService;
import com.mysql.cj.log.Log;
import jdk.nashorn.internal.ir.ReturnNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zxq
 * @date 2022/3/2 9:21 下午
 * @description:
 */
@Slf4j
public class AuthResourceDaoTest extends SuperTest {

    @Resource
    AuthResourceDao authResourceDao;

    @Resource
    ResourceService resourceService;

    @Test
    public void getDisableResourcePathDat() {
        List<String> disableResourcePathData = authResourceDao.getDisableResourcePathData();
        if (disableResourcePathData.size() > 0) {
            disableResourcePathData.forEach(item->{
                System.out.println(item);
            });
        }
    }

    @Test
    public void getEnableResourcePathDat() {
        List<String> enableResourcePathRoleData = authResourceDao.getEnableResourcePathRoleData();
        if (CollectionUtils.isNotEmpty(enableResourcePathRoleData)) {
            System.out.println(enableResourcePathRoleData.size());
        }
    }

    @Test
    public void isResourceExist() {
        AuthResourceDO resourceDO = AuthResourceDO.builder()
                .name("test add resource")
                .code("EST_ADD_RESOURCE")
                .uri("/resource/test/*")
                .type("resource")
                .method("post")
                .status(1)
                .description("test annotation")
                .build();
        boolean resourceExist = resourceService.isResourceExist(resourceDO);
        //log.info("{}",resourceExist);

        AuthResourceDO resource = AuthResourceDO.builder()
                .uri(resourceDO.getUri())
                .method(resourceDO.getMethod())
                .build();
        List<AuthResourceDO> authResourceDOS = authResourceDao.selectList(new QueryWrapper<AuthResourceDO>().lambda()
                .eq(AuthResourceDO::getUri, resource.getUri())
                .eq(AuthResourceDO::getMethod, resource.getMethod()));
        log.info("{}",CollectionUtils.isEmpty(authResourceDOS));
    }
}
