package com.example.exception.demo.constant.enums;

import com.example.exception.demo.exception.BaseException;
import com.example.exception.demo.exception.assertion.BusinessExceptionAssert;
import com.example.exception.demo.exception.assertion.CommonExceptionAssert;
import com.example.exception.demo.pojo.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>业务异常返回结果</p>
 *
 * @author wangxiaoyu
 * @date 2019/10/2
 */
@Getter
@AllArgsConstructor
public enum BusinessResponseEnum implements BusinessExceptionAssert {

    PASSWD_ERROR(4001, "密码错误"),

    USER_NOT_NULL(4002, "用户不存在"),
    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

    /**
     * 校验返回结果是否成功
     * @param response 远程调用的响应
     */
    public static void assertSuccess(BaseResponse response) {
        int code = response.getCode();
        String msg = response.getMessage();
        throw new BaseException(code, msg);

    }
}
