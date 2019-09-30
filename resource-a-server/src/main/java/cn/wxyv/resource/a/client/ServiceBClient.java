package cn.wxyv.resource.a.client;

import cn.wxyv.resource.a.config.FeignConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(url = "http://localhost:8105",name = "b-server", configuration = FeignConfig.class,
        fallback = ServiceBClient.ServiceBClientFallback.class)
public interface ServiceBClient {

    @GetMapping(value = "/")
    String printServiceB();

    @GetMapping(path = "/current")
    String getCurrentAccount();

    @Component
    class ServiceBClientFallback implements ServiceBClient {

        private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBClientFallback.class);

        @Override
        public String printServiceB() {
            LOGGER.info("异常发生，进入fallback方法");
            return "SERVICE B FAILED! - FALLING BACK";
        }

        @Override
        public String getCurrentAccount() {
            LOGGER.info("异常发生，进入fallback方法");
            return "SERVICE B FAILED! - FALLING BACK";
        }
    }
}