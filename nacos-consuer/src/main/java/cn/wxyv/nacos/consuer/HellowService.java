package cn.wxyv.nacos.consuer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "nacos-provider")
public interface HellowService {

    @GetMapping("/hello")
    String hello(@RequestParam(value = "name") String name);
}
