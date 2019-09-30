package cn.wxyv.demo.handler;

import org.springframework.stereotype.Component;

@Component(WxLoginHandler.HANDLER_NAME)
public class WxLoginHandler implements LoginHandler {
    public static final String HANDLER_NAME = "wxLoginHandler";

    @Override
    public boolean handler() {
        System.out.println("微信登录处理");
        return true;
    }
}
