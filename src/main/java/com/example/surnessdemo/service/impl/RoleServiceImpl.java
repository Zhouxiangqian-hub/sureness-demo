package com.example.surnessdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.surnessdemo.dao.AuthResourceDao;
import com.example.surnessdemo.dao.AuthRoleDao;
import com.example.surnessdemo.dao.AuthRoleResourceBindDao;
import com.example.surnessdemo.pojo.entity.AuthResourceDO;
import com.example.surnessdemo.pojo.entity.AuthRoleDO;
import com.example.surnessdemo.pojo.entity.AuthRoleResourceBindDO;
import com.example.surnessdemo.service.RoleService;
import com.usthe.sureness.matcher.TreePathRoleMatcher;
import net.sf.jsqlparser.statement.select.Distinct;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private AuthRoleDao authRoleDao;

    @Autowired
    private AuthResourceDao authResourceDao;

    @Autowired
    private AuthRoleResourceBindDao roleResourceBindDao;

    @Autowired
    private TreePathRoleMatcher treePathRoleMatcher;

    @Override
    public boolean isRoleExist(AuthRoleDO authRole) {
//        AuthRoleDO role = AuthRoleDO.builder()
//                .name(authRole.getName()).code(authRole.getCode()).build();
        AuthRoleDO authRoleDO = authRoleDao.selectOne(new QueryWrapper<AuthRoleDO>().lambda().eq(AuthRoleDO::getName, authRole.getName()).eq(AuthRoleDO::getCode, authRole.getCode()));
        return Optional.ofNullable(authRoleDO).isPresent();
    }

    @Override
    public boolean addRole(AuthRoleDO authRole) {
        if (isRoleExist(authRole)) {
            return false;
        } else {
            authRoleDao.insert(authRole);
            return true;
        }
    }

    @Override
    public boolean updateRole(AuthRoleDO authRole) {
        AuthRoleDO authRoleDO = authRoleDao.selectById(authRole.getId());
        if (ObjectUtils.isNotEmpty(authRoleDO)) {
            authRoleDao.update(authRole,new UpdateWrapper<AuthRoleDO>().lambda().eq(AuthRoleDO::getId, authRole.getId()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteRole(Long roleId) {
        if (ObjectUtils.isNotEmpty(roleId)) {
            authRoleDao.deleteById(roleId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<List<AuthRoleDO>> getAllRole() {
        List<AuthRoleDO> roleList = authRoleDao.selectList(null);
        return Optional.of(roleList);
    }

    @Override
    public IPage<AuthRoleDO> getPageRole(Integer currentPage, Integer pageSize) {
        Page<AuthRoleDO> page = new Page<>(currentPage,pageSize);
        return authRoleDao.selectPage(page, null);
    }

    @Override
    public List<AuthResourceDO> getPageResourceOwnRole(Long roleId) {
        List<AuthResourceDO> roleOwnResource = authResourceDao.findRoleOwnResource(roleId);
        return roleOwnResource;
    }

    @Override
    public List<AuthResourceDO> getPageResourceNotOwnRole(Long roleId) {
        return authResourceDao.findRoleNotOwnResource(roleId);
    }

    @Override
    public void authorityRoleResource(Long roleId, Long resourceId) {

        // Determine whether this resource and role exist
        if (authRoleDao.selectById(roleId)==null || authResourceDao.selectById(resourceId)==null) {
            throw new DataConflictException("roleId or resourceId not exist");
        }
        // insert it in database, if existed the unique index will work
        AuthRoleResourceBindDO bind = AuthRoleResourceBindDO
                .builder().roleId(roleId).resourceId(resourceId).build();
        roleResourceBindDao.insert(bind);
        // refresh resource path data tree
        treePathRoleMatcher.rebuildTree();
    }

    @Override
    public void deleteAuthorityRoleResource(Long roleId, Long resourceId) {
        roleResourceBindDao.deleteRoleResourceBind(roleId, resourceId);
        // refresh resource path data tree
        treePathRoleMatcher.rebuildTree();
    }
}
