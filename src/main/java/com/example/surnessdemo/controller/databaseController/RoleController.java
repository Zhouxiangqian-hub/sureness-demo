package com.example.surnessdemo.controller.databaseController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.surnessdemo.pojo.dto.Message;
import com.example.surnessdemo.pojo.entity.AuthResourceDO;
import com.example.surnessdemo.pojo.entity.AuthRoleDO;
import com.example.surnessdemo.service.RoleService;
import com.usthe.sureness.provider.annotation.RequiresRoles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zxq
 * @date 2022/2/24 9:30 下午
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询当前角色拥有的可用API资源权限
     * @param roleId
     * @return
     */
    @GetMapping("/resource/{roleId}")
    public ResponseEntity<Message> getResourceOwnByRole(@PathVariable @NotBlank Long roleId) {
        List<AuthResourceDO> resources = roleService.getPageResourceOwnRole(roleId);
        Message message = Message.builder().data(resources).build();
        return ResponseEntity.ok().body(message);
    }

    /**
     * 查询当前角色未授权的API资源权限
     * @param roleId
     **/
    @GetMapping("/resource/-/{roleId}")
    public ResponseEntity<Message> getResourceNotOwnByRole(@PathVariable @NotBlank Long roleId) {
        List<AuthResourceDO> resourceList = roleService.getPageResourceNotOwnRole(roleId);
        Message message = Message.builder().data(resourceList).build();
        return ResponseEntity.ok().body(message);
    }

    /**
     * 赋予某个角色一个资源权限
     * @param roleId
     * @param resourceId
     * @return
     */
    @PostMapping("/authority/resource/{roleId}/{resourceId}")
    public ResponseEntity<Message> authorityRoleResource(@PathVariable @NotBlank Long roleId,
                                                         @PathVariable @NotBlank Long resourceId) {
        roleService.authorityRoleResource(roleId,resourceId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 解除某个角色所拥有的具体资源权限
     * @param roleId
     * @param resourceId
     * @return
     */
    @DeleteMapping("/authority/resource/{roleId}/{resourceId}")
    public ResponseEntity<Message> deleteAuthorityRoleResource(@PathVariable @NotBlank Long roleId,
                                                         @PathVariable @NotBlank Long resourceId) {
        roleService.deleteAuthorityRoleResource(roleId,resourceId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 添加角色
     * @param authRole
     * @return
     */
    @PostMapping
    public ResponseEntity<Message> addRole(@RequestBody @Validated AuthRoleDO authRole) {
        if (roleService.addRole(authRole)) {
            if (log.isDebugEnabled()) {
                log.debug("add role success: {}", authRole);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            Message message = Message.builder()
                    .errorMsg("role already exist").build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    /**
     * 更新角色
     * @param authRole
     * @return
     */
    @PutMapping
    public ResponseEntity<Message> updateRole(@RequestBody @Validated AuthRoleDO authRole) {
        if (roleService.updateRole(authRole)) {
            if (log.isDebugEnabled()) {
                log.debug("update role success: {}", authRole);
            }
            return ResponseEntity.ok().build();
        } else {
            Message message = Message.builder()
                    .errorMsg("role not exist").build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    /**
     * 删除指定角色
     * @param roleId
     * @return
     */
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Message> deleteRole(@PathVariable @NotBlank Long roleId) {
        if (roleService.deleteRole(roleId)) {
            if (log.isDebugEnabled()) {
                log.debug("delete role success: {}", roleId);
            }
            return ResponseEntity.ok().build();
        } else {
            Message message = Message.builder()
                    .errorMsg("delete role fail, no this role here").build();
            log.debug("delete role fail: {}", roleId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    /**
     * 查询都有哪些角色
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/{currentPage}/{pageSize}")
    public ResponseEntity<Message> getRole(@PathVariable Integer currentPage, @PathVariable Integer pageSize ) {
        if (Objects.isNull(currentPage) || Objects.isNull(pageSize)) {
            // no pageable
            Optional<List<AuthRoleDO>> roleListOptional = roleService.getAllRole();
            if (roleListOptional.isPresent()) {
                Message message = Message.builder().data(roleListOptional.get()).build();
                return ResponseEntity.ok().body(message);
            } else {
                Message message = Message.builder().data(new ArrayList<>()).build();
                return ResponseEntity.ok().body(message);
            }
        } else {
            // pageable
            IPage<AuthRoleDO> rolePage = roleService.getPageRole(currentPage, pageSize);
            Message message = Message.builder().data(rolePage).build();
            return ResponseEntity.ok().body(message);
        }
    }

}
