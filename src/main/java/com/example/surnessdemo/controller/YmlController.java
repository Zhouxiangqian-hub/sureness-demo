package com.example.surnessdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zxq
 * @date 2022/2/24 8:43 下午
 * @description: 模拟一些restful api
 */
@RestController
@RequestMapping("/yml/api/v1")
public class YmlController {
    /** access success message **/
    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource success";

    @GetMapping("/source1")
    public ResponseEntity<String> api1Mock1() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @PutMapping("/source1")
    public ResponseEntity<String> api1Mock3() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @DeleteMapping("/source1")
    public ResponseEntity<String> api1Mock4() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @GetMapping("/source2")
    public ResponseEntity<String> api1Mock5() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @GetMapping("/source2/{var1}/{var2}")
    public ResponseEntity<String> api1Mock6(@PathVariable String var1, @PathVariable Integer var2 ) {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @PostMapping("/v2/source3/{var1}")
    public ResponseEntity<String> api1Mock7(@PathVariable String var1) {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @GetMapping("/source3")
    public ResponseEntity<String> api1Mock11(HttpServletRequest request) {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

    @GetMapping("/v2/source2")
    public ResponseEntity<String> api1Mock12() {
        return ResponseEntity.ok(SUCCESS_ACCESS_RESOURCE);
    }

}
