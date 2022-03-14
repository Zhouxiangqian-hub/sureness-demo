package com.example.surnessdemo.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * resource-role mapping entity
 * @author tomsun28
 * @date 00:28 2019-07-27
 */
@TableName(value = "auth_role_resource_bind")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRoleResourceBindDO extends Model<AuthRoleResourceBindDO> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotNull(message = "roleId can not null")
    private Long roleId;

    @NotNull(message = "resourceId can not null")
    private Long resourceId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
