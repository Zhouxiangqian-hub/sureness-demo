package com.example.surnessdemo.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * user entity
 * @author tomsun28
 * @date 00:29 2019-07-27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "auth_user")
public class AuthUserDO extends Model<AuthUserDO> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "username can not null")
    @Length(min = 3, max = 100, message = "name length in 3-100")
    private String username;

    @NotBlank(message = "password can not null")
    @Length(min = 3, max = 100, message = "password length in 3-100")
    private String password;

    private String salt;

    private String avatar;

    private String phone;

    private String email;

    private Integer sex;

    @Range(min = 0, max = 9, message = "1 enable, 2 locked, 3 deleted, 4 illegal")
    private Integer status;

    private Integer createWhere;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;
}









