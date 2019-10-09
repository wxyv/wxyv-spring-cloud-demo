package com.example.exception.demo.exception.assertion;

import com.example.exception.demo.constant.IResponseEnum;
import com.example.exception.demo.exception.BaseException;
import com.example.exception.demo.exception.BusinessException;

import java.text.MessageFormat;

/**
 * <p>业务异常断言</p>
 *
 * @author wangxiaoyu
 * @date 2019/10/2
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg, t);
    }

}
