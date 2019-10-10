package com.example.exception.demo.constant;

/**
 * <pre>
 *  异常返回码枚举接口
 * </pre>
 *
 * @author wangxiaoyu
 * @date 2019/10/2
 */
public interface IResponseEnum {
    /**
     * 获取返回码
     * @return 返回码
     */
    int getCode();

    /**
     * 获取返回信息
     * @return 返回信息
     */
    String getMessage();
}
