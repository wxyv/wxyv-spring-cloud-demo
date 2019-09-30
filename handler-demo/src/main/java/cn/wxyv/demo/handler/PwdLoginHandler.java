package cn.wxyv.demo.handler;

import org.springframework.stereotype.Component;

@Component(PwdLoginHandler.HANDLER_NAME)
public class PwdLoginHandler implements LoginHandler {
    public static final String HANDLER_NAME = "pwdLoginHandler";

    @Override
    public boolean handler() {
        System.out.println("密码登录处理");
        return true;
    }
}
