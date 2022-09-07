package com.demo.pochi.controller;

import com.demo.pochi.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result<?> test() {
        return new Result<>("操作成功");
    }

}