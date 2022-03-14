package com.example.surnessdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.surnessdemo.pojo.entity.AuthUserDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 16:43 2019-07-27
 */
public interface AuthUserDao extends BaseMapper<AuthUserDO> {

    /**
     * Get user by username
     * @param username username
     * @return user
     */
    //@Query("select au from AuthUserDO au where au.username = :username")
    @Select("select * from auth_user au where au.username = #{username}")
    Optional<AuthUserDO> findAuthUserByUsername(@Param("username") String username);

    /**
     * Query the role owned by the current user
     * @param username username
     * @return role list
     */
    //@Query("select ar.code from AuthRoleDO ar, AuthUserDO au, AuthUserRoleBindDO bind " +"where ar.id = bind.roleId and au.id = bind.userId and au.username = :username")
    @Select("select ar.code from auth_role ar, auth_user au, auth_user_role_bind bind "
            +"where ar.id = bind.role_id and au.id = bind.user_id and au.username = #{username}")
    List<String> findAccountOwnRoles(@Param("username") String username);
}
