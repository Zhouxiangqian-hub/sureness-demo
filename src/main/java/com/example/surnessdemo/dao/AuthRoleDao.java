package com.example.surnessdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.surnessdemo.pojo.entity.AuthRoleDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author zxq
 * @date 2022/3/2 9:00 下午
 * @description:
 */
public interface AuthRoleDao extends BaseMapper<AuthRoleDO> {

    /**
     * Query the role owned by the current user
     * @param username username
     * @return role list
     */
    //@Query("select ar.name from AuthRoleDO ar, AuthUserDO au, AuthUserRoleBindDO bind " + "where ar.id = bind.roleId and au.id = bind.userId and au.username = :username")
    @Select("select ar.name from auth_role ar, auth_user au, auth_user_role_bind bind " + "where ar.id = bind.roleId and au.id = bind.userId and au.username = ${username}")
    List<String> findAccountOwnRoles(@Param("username") String username);

    @Select("select id from auth_role where id = #{roleId}")
    Set<String> fatchById(@Param("id") String roleId);
}
