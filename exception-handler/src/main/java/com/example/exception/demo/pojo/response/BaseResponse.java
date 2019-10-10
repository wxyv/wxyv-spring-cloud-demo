package com.example.exception.demo.pojo.response;

import com.example.exception.demo.constant.IResponseEnum;
import com.example.exception.demo.constant.enums.CommonResponseEnum;
import lombok.Data;

/**
 * <p>基础返回结果</p>
 *
 * @author wangxiaoyu
 * @date 2019/10/2
 */
@Data
public class BaseResponse {
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
    protected String message;

    public BaseResponse() {
        // 默认创建成功的回应
        this(CommonResponseEnum.SUCCESS);
    }

    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
