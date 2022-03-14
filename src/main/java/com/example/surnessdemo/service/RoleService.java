package com.example.surnessdemo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.surnessdemo.pojo.entity.AuthResourceDO;
import com.example.surnessdemo.pojo.entity.AuthRoleDO;

import java.util.List;
import java.util.Optional;

/**
 * @author zxq
 * @date 2022/3/2 9:00 下午
 * @description:
 */
public interface RoleService {

    /**
     * Determine whether the role already exists
     * @param authRole role
     * @return existed-true no-false
     */
    boolean isRoleExist(AuthRoleDO authRole);

    /**
     * add role
     * @param authRole role
     * @return add success-true failed-false
     */
    boolean addRole(AuthRoleDO authRole);

    /**
     * update role
     * @param authRole role
     * @return success-true failed-false
     */
    boolean updateRole(AuthRoleDO authRole);

    /**
     * delete role
     * @param roleId role ID
     * @return success-true failed-false
     */
    boolean deleteRole(Long roleId);

    /**
     * get all role list
     * @return role list
     */
    Optional<List<AuthRoleDO>> getAllRole();

    /**
     * get roles page
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of roles
     */
    IPage<AuthRoleDO> getPageRole(Integer currentPage, Integer pageSize);

    /**
     * get pageable resources which this role owned
     * @param roleId role ID
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of resources
     */
    List<AuthResourceDO> getPageResourceOwnRole(Long roleId);

    /**
     * get pageable resources which this role not owned
     * @param roleId role ID
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of resources
     */
    List<AuthResourceDO> getPageResourceNotOwnRole(Long roleId);

    /**
     * authority this resource to this role
     * @param roleId role ID
     * @param resourceId resource ID
     */
    void authorityRoleResource(Long roleId, Long resourceId);

    /**
     * unAuthority this resource in this role
     * @param roleId role ID
     * @param resourceId resource ID
     */
    void deleteAuthorityRoleResource(Long roleId, Long resourceId);

}
