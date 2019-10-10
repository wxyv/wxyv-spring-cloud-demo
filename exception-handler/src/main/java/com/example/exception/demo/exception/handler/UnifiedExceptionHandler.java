package com.example.exception.demo.exception.handler;

import com.example.exception.demo.constant.enums.CommonResponseEnum;
import com.example.exception.demo.exception.BaseException;
import com.example.exception.demo.pojo.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>全局异常处理器</p>
 *
 * @author wangxiaoyu
 * @date 2019/10/2
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class UnifiedExceptionHandler {


    /**
     * 自定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BaseException.class)
    public ErrorResponse handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getResponseEnum().getCode(), e.getMessage());
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(CommonResponseEnum.SERVER_ERROR.getCode(), e.getMessage());
    }

}
