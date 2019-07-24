package cn.wxyv.nacos.consuer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class NacosConsuerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosConsuerApplication.class, args);
	}

	@Autowired
	HellowService hellowService;

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name") String name){
		return hellowService.hello(name);
	}
}
