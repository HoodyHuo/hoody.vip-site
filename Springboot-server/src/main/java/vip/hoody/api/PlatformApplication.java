package vip.hoody.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Hoody
 * SpringBoot启动类
 */

@EnableAsync // 开启异步任务
@EnableCaching //开启缓存
@EnableScheduling //开启计划
@SpringBootApplication
@EnableJpaAuditing
public class PlatformApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PlatformApplication.class);
    }
    public static void main(String[] args) {

        SpringApplication.run(PlatformApplication.class, args);
    }

}
