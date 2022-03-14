package com.example.surnessdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.surnessdemo.pojo.entity.AuthRoleDO;
import com.example.surnessdemo.pojo.entity.AuthUserRoleBindDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tomsun28
 * @date 16:44 2019-07-27
 */
public interface AuthUserRoleBindDao extends BaseMapper<AuthUserRoleBindDO> {

    /**
     * Query the role owned by the current user
     * @param userId userId
     * @return role list
     */
    /*@Query("select ar from AuthRoleDO ar, AuthUserRoleBindDO bind " +
            "where ar.id = bind.roleId and bind.userId = :userId")*/
    List<AuthRoleDO> findUserBindRoleList(@Param("userId") Long userId);

    /**
     * delete record which roleId and userId equals this
     * @param roleId roleID
     * @param userId userId
     */
    /*@Query("delete from AuthUserRoleBindDO bind " +
            "where bind.roleId = :roleId and bind.userId = :userId")*/
    void deleteRoleResourceBind(@Param("roleId") Long roleId, @Param("userId") Long userId);
}
