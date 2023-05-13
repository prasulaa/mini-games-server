package pl.prasulaspzoo.server.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    @Value("${servers.pool.size:4}")
    private int poolSize;

    @Bean
    public ScheduledExecutorService getScheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(poolSize);
    }

}
