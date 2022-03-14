package com.example.surnessdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.surnessdemo.pojo.entity.AuthResourceDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * @author zxq
 * @date 2022/2/24 下午
 * @description:
 */
public interface AuthResourceDao extends BaseMapper<AuthResourceDO> {

    /**
     * Get uri resource and resource-role relationship chain, eg: /api/v2/host===post===[role2,role3,role4]
     * @return resource-role chain set
     */
   /* @Query(value = "SELECT  CONCAT(LOWER(res.uri),\"===\",LOWER(res.method),\"===[\",IFNULL(GROUP_CONCAT(DISTINCT role.code),\"\"),\"]\") " + "FROM auth_resource res " +
            "LEFT JOIN auth_role_resource_bind bind on res.id = bind.resource_id " +
            "LEFT JOIN auth_role role on role.id = bind.role_id " +
            "where res.status = 1 " +
            "group by res.id", nativeQuery = true)*/
    @Select("SELECT  CONCAT(LOWER(res.uri),\"===\",LOWER(res.method),\"===[\",IFNULL(GROUP_CONCAT(DISTINCT role.code),\"\"),\"]\") " + "FROM auth_resource res " +
            "LEFT JOIN auth_role_resource_bind bind on res.id = bind.resource_id " +
            "LEFT JOIN auth_role role on role.id = bind.role_id " +
            "where res.status = 1 " +
            "group by res.id")
    List<String> getEnableResourcePathRoleData();



    /**
     * Get disabled uri resources eg: /api/v2/host===post
     * @return resouce set
     */
    /*@Query("select CONCAT(LOWER(resource.uri),'===', resource.method) " +
            "from AuthResourceDO resource where resource.status = 9 order by resource.id")*/
    //Optional<List<String>> getDisableResourcePathData();
    List<String> getDisableResourcePathData();

    /**
     * Get the available API resources owned by the current role in the form of paging
     * @param roleId roleId
     * @param request page
     * @return api resource list
     */
    @Select("select * from auth_resource resource " +
            " where resource.id in " +
            "(select distinct bind.resource_id from auth_role_resource_bind bind where bind.role_id = #{roleId}) " +
            "order by resource.id asc ")
    List<AuthResourceDO> findRoleOwnResource(@Param("roleId") Long roleId);

    /**
     * Get not the available API resources owned by the current role in the form of paging
     * @param roleId roleId
     * @return api resource list
     */
    @Select("select * from auth_resource resource " +
            " where resource.id not in " +
            "(select distinct bind.resource_id from auth_role_resource_bind bind where bind.role_id = #{roleId}) " +
            "order by resource.id asc ")
    List<AuthResourceDO> findRoleNotOwnResource(@Param("roleId") Long roleId);
}
