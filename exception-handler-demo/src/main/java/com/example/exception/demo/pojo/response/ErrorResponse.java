package com.example.exception.demo.pojo.response;

/**
 * <p>错误返回结果</p>
 *
 * @author wangxiaoyu
 * @date 2019/10/2
 */
public class ErrorResponse extends BaseResponse {

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String message) {
        super(code, message);
    }
}
