package com.example.exception.demo.pojo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>通用返回结果</p>
 *
 * @author wangxiaoyu
 * @date 2019/10/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommonResponse<T> extends BaseResponse {
    /**
     * 数据列表
     */
    protected T data;

    public CommonResponse() {
        super();
    }

    public CommonResponse(T data) {
        super();
        this.data = data;
    }
}
