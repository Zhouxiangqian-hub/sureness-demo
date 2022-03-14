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
 * user-role mapping entity
 * @author tomsun28
 * @date 00:30 2019-07-27
 */

@TableName(value = "auth_user_role_bind")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserRoleBindDO extends Model<AuthUserRoleBindDO> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotNull(message = "userId can not null")
    private Long userId;

    @NotNull(message = "roleId can not null")
    private Long roleId;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}
