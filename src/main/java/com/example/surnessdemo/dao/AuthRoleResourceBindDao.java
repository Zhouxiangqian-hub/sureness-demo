package com.example.surnessdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.surnessdemo.pojo.entity.AuthResourceDO;
import com.example.surnessdemo.pojo.entity.AuthRoleResourceBindDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tomsun28
 * @date 16:43 2019-07-27
 */
public interface AuthRoleResourceBindDao extends BaseMapper<AuthRoleResourceBindDO> {


    /**
     * Query the resources owned by the current role
     *
     * @param roleId roleId
     * @return resource list
     */
    //@Query("select rs from AuthResourceDO rs, AuthRoleResourceBindDO bind " + "where rs.id = bind.resourceId and bind.roleId = :roleId")
    @Select("select rs from AuthResourceDO rs, AuthRoleResourceBindDO bind " + "where rs.id = bind.resourceId and bind.roleId = ${roleId}")
    List<AuthResourceDO> findRoleBindResourceList(@Param("roleId") Long roleId);

    /**
     * delete record which roleId and resource equals this
     *
     * @param roleId     roleID
     * @param resourceId resourceId
     */
    //@Modifying
    //@Query("delete from AuthRoleResourceBindDO bind " +"where bind.roleId = :roleId and bind.resourceId = :resourceId")
    @Delete("delete from auth_role_resource_bind where role_id = #{roleId} and resource_id = ${resourceId}")
    void deleteRoleResourceBind(@Param("roleId") Long roleId, @Param("resourceId") Long resourceId);
}
