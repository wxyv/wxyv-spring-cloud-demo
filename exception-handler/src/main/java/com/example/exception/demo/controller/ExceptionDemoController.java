package com.example.exception.demo.controller;

import com.example.exception.demo.constant.enums.BusinessResponseEnum;
import com.example.exception.demo.constant.enums.CommonResponseEnum;
import com.example.exception.demo.pojo.User;
import com.example.exception.demo.pojo.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xywang
 */
@RestController
public class ExceptionDemoController {
    private static Map<String,User> map = new HashMap<>();

    static{
        User user = new User("admin","1234");
        map.put(user.getUsername(),user);
    }

    /**
     * 异常
     * @return
     */
    @GetMapping("/user")
    public R biz(String username){
        // 通用异常，判断字符串是否为空
        CommonResponseEnum.NOT_NULL.assertNotEmpty(username);
        User user = map.get(username);
        // 业务异常，判断用户是否为空
        BusinessResponseEnum.USER_NOT_NULL.assertNotNull(user);
        return new R(user);
    }

    /**
     * 未定义的异常
     * @return
     */
    @GetMapping("/divide")
    public R divide(double x, double y) {
        BigDecimal b1 = new BigDecimal(Double.toString(x));
        BigDecimal b2 = new BigDecimal(Double.toString(y));
        DecimalFormat df = new DecimalFormat("0.00");
        return new R(df.format(b1.divide(b2,2,BigDecimal.ROUND_HALF_UP)));
    }
}
