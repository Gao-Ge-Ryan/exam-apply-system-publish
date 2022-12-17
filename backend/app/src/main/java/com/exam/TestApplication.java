package com.exam;


import com.exam.controller.websocket.server.WebsocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication

@EnableSwagger2
@EnableScheduling
public class TestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestApplication.class, args);
        // 解决Websocket空注入问题
        WebsocketServer.setApplicationContext(applicationContext);

    }
}
