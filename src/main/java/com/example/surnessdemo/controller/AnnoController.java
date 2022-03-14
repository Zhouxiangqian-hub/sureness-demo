package com.example.surnessdemo.controller;

import com.usthe.sureness.provider.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxq
 * @date 2022/2/25 10:51 上午
 * @description: 注解rest api
 */
@RestController
@RequestMapping("/anno/api/v1")
public class AnnoController {

    /** access success message **/
    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource success";

    @GetMapping("/source1")
    @RequiresRoles(roles = {"role19"}, mapping = "/source1", method = "get")
    public ResponseEntity<String> api1Mock1() {
        //return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
        return ResponseEntity.ok("123");
    }

    @RequiresRoles(roles = {"role1","role2"}, mapping = "/source1", method = "put")
    public ResponseEntity<String> api1Mock3() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }



}
