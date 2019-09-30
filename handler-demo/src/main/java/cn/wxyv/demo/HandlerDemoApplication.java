package cn.wxyv.demo;

import cn.wxyv.demo.enums.LoginModeEnum;
import cn.wxyv.demo.handler.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@SpringBootApplication
public class HandlerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandlerDemoApplication.class, args);
	}

	@GetMapping("/v2/login")
	public String login(@PathParam("type") String type){
		LoginHandler login = LoginModeEnum.valueOf(type.toUpperCase()).getLoginHandler();
		if(login.handler()){
			return "登录成功";
		}
		return "登录失败";
	}

	@Autowired
	LoginHandler pwdLoginHandler;

	@Autowired
	LoginHandler wxLoginHandler;

	@Autowired
	LoginHandler smsLoginHandler;

	@GetMapping("/v1/login")
	public String login2(@PathParam("type") String type){
		type = type.toUpperCase();
		if(type.equals("WX")){
			wxLoginHandler.handler();
			return "登录成功";
		}else if(type.equals("PWD")){
			pwdLoginHandler.handler();
			return "登录成功";
		}else if(type.equals("SMS")){
			smsLoginHandler.handler();
			return "登录成功";
		}else {
			return "类型错误";
		}
	}
}
