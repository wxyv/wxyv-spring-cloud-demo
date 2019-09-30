package cn.wxyv.demo.enums;

import cn.wxyv.demo.factory.LoginHandlerFactory;
import cn.wxyv.demo.handler.LoginHandler;
import cn.wxyv.demo.handler.PwdLoginHandler;
import cn.wxyv.demo.handler.SmsLoginHandler;
import cn.wxyv.demo.handler.WxLoginHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginModeEnum {
    //密码登录
    PWD(PwdLoginHandler.HANDLER_NAME),
    //微信登录
    WX(WxLoginHandler.HANDLER_NAME),
    //短信登录s
    SMS(SmsLoginHandler.HANDLER_NAME);

    String value;

    public LoginHandler getLoginHandler() {
        return LoginHandlerFactory.getHandler(this);
    }
}
