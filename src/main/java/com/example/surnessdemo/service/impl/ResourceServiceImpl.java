package com.example.surnessdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.surnessdemo.dao.AuthResourceDao;
import com.example.surnessdemo.pojo.entity.AuthResourceDO;
import com.example.surnessdemo.service.ResourceService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private AuthResourceDao authResourceDao;


    @Override
    public boolean addResource(AuthResourceDO authResource) {
        if (isResourceExist(authResource)) {
            return false;
        } else {
            authResourceDao.insert(authResource);
            return true;
        }
    }

    @Override
    public boolean isResourceExist(AuthResourceDO authResource) {
        AuthResourceDO resource = AuthResourceDO.builder()
                .uri(authResource.getUri())
                .method(authResource.getMethod())
                .build();
        List<AuthResourceDO> authResourceDOS = authResourceDao.selectList(new QueryWrapper<AuthResourceDO>().lambda()
                .eq(AuthResourceDO::getUri, resource.getUri())
                .eq(AuthResourceDO::getMethod, resource.getMethod()));
        return CollectionUtils.isNotEmpty(authResourceDOS);
    }

    @Override
    public boolean updateResource(AuthResourceDO authResource) {
        if (authResourceDao.existsById(authResource.getId())) {
            authResourceDao.updateById(authResource);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean deleteResource(Long resourceId) {
        if (authResourceDao.existsById(resourceId)) {
            authResourceDao.deleteById(resourceId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<List<AuthResourceDO>> getAllResource() {
        List<AuthResourceDO> authResourceDOS = authResourceDao.selectList(null);
        return Optional.ofNullable(authResourceDOS);
    }

    @Override
    public IPage<AuthResourceDO> getPageResource(Integer currentPage, Integer pageSize) {
        Page<AuthResourceDO> page = new Page<>(currentPage, pageSize);
        return authResourceDao.selectPage(page, null);
    }

    @Override
    public Set<String> getAllEnableResourcePath() {
        List<String> enableResourcePathRoleData = authResourceDao.getEnableResourcePathRoleData();
        return Optional.ofNullable(enableResourcePathRoleData).<Set<String>>map(HashSet::new).orElseGet(() -> new HashSet<>(0));
    }

    @Override
    public Set<String> getAllDisableResourcePath() {
        List<String> disableResourcePathData = authResourceDao.getDisableResourcePathData();
        return Optional.ofNullable(disableResourcePathData).<Set<String>>map(HashSet::new).orElseGet(() -> new HashSet<>(0));
    }
}
