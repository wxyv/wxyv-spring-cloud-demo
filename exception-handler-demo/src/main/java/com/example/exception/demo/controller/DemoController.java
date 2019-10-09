package com.example.exception.demo.controller;

import com.example.exception.demo.constant.enums.CommonResponseEnum;
import com.example.exception.demo.pojo.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/test")
    public R demo1(String name){
        CommonResponseEnum.NOT_NULL.assertNotNull(name);
        return new R();
    }

}
