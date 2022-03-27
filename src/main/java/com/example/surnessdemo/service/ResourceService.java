package com.example.surnessdemo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.surnessdemo.pojo.entity.AuthResourceDO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author zxq
 * @date 2022/3/2 9:20 下午
 * @description:
 */
public interface ResourceService {

    /**
     * add uri resource
     * @param authResource resource
     * @return success-true failed-false
     */
    boolean addResource(AuthResourceDO authResource);

    /**
     * Determine whether the resource already exists
     * @param authResource resource
     * @return existed-true no-false
     */
    boolean isResourceExist(AuthResourceDO authResource);

    /**
     * update uri resource
     * @param authResource resource
     * @return success-true failed-false
     */
    boolean updateResource(AuthResourceDO authResource);

    /**
     * delete uri resource
     * @param resourceId resource ID
     * @return success-true no existed-false
     */
    boolean deleteResource(Long resourceId);

    /**
     * get all resources
     * @return resource list
     */
    Optional<List<AuthResourceDO>> getAllResource();

    /**
     * get resource by page
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of resource
     */
    IPage<AuthResourceDO> getPageResource(Integer currentPage, Integer pageSize);

    /**
     * get enabled resource-path-role eg: /api/v2/host===post===[role2,role3,role4]
     * @return resource-path-role
     */
    Set<String> getAllEnableResourcePath();

    /**
     * get disable resource-path-role eg: /api/v2/host===post===[role2,role3,role4]
     * @return resource-path-role
     */
    Set<String> getAllDisableResourcePath();
}
