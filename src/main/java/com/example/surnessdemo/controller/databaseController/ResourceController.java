package com.example.surnessdemo.controller.databaseController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.surnessdemo.pojo.dto.Message;
import com.example.surnessdemo.pojo.entity.AuthResourceDO;
import com.example.surnessdemo.service.ResourceService;
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
@RequestMapping("/resource")
@RestController
@Slf4j
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/testadd")
    @RequiresRoles(roles = {"role_guest"}, mapping = "/testadd", method = "post")
    public ResponseEntity<Message> addResource(@RequestBody @Validated AuthResourceDO authResource) {
        if (resourceService.addResource(authResource)) {
            if (log.isDebugEnabled()) {
                log.debug("add resource success: {}", authResource);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            Message message = Message.builder().errorMsg("resource already exist").build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    /**
     * 更新资源
     * @param authResource
     * @return
     */
    @PutMapping
    public ResponseEntity<Message> updateResource(@RequestBody @Validated AuthResourceDO authResource) {
        if (resourceService.updateResource(authResource)) {
            if (log.isDebugEnabled()) {
                log.debug("update resource success: {}", authResource);
            }
            return ResponseEntity.ok().build();
        } else {
            Message message = Message.builder().errorMsg("resource not exist").build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    /**
     * 删除资源
     * @param resourceId
     * @return
     */
    @DeleteMapping("/{resourceId}")
    public ResponseEntity<Message> deleteResource(@PathVariable @NotBlank Long resourceId ) {
        if (resourceService.deleteResource(resourceId)) {
            if (log.isDebugEnabled()) {
                log.debug("delete resource success: {}", resourceId);
            }
            return ResponseEntity.ok().build();
        } else {
            Message message = Message.builder().errorMsg("delete resource fail, please try again later").build();
            log.error("delete resource fail: {}", resourceId);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(message);
        }
    }

    /**
     * 查询全部资源-可分页
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/{currentPage}/{pageSize}")
    public ResponseEntity<Message> getResource(@PathVariable Integer currentPage, @PathVariable Integer pageSize ) {
        if (Objects.isNull(currentPage) || Objects.isNull(pageSize)) {
            // no pageable
            Optional<List<AuthResourceDO>> resourceListOptional = resourceService.getAllResource();
            if (resourceListOptional.isPresent()) {
                Message message = Message.builder().data(resourceListOptional.get()).build();
                return ResponseEntity.ok().body(message);
            } else {
                Message message = Message.builder().data(new ArrayList<>(0)).build();
                return ResponseEntity.ok().body(message);
            }
        } else {
            // pageable
            IPage<AuthResourceDO> resourcePage = resourceService.getPageResource(currentPage, pageSize);
            Message message = Message.builder().data(resourcePage).build();
            return ResponseEntity.ok().body(message);
        }
    }

}
