package com.company.producthub;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

/**
 * Represents application's main gateway and configuration class.
 * 
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
@SpringBootApplication
@EnableAsync
public class App {
    
    @Value("${executor.threadpool.size.core}")
    private int executorPoolSizeCore;
    
    @Value("${executor.threadpool.size.max}")
    private int executorPoolSizeMax;
    
    @Value("${executor.queue.capacity}")
    private int executorQueueCapacity;
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    /**
     * Creates a RestTemplate bean by using 
     * auto-configured RestTemplateBuilder and makes it
     * available in application's context
     * 
     * @param builder the RestTemplate builder
     * @return the RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    
    /**
     * Creates an Executor bean to be used by
     * application's asynchronous programming model.
     * The executor is configured by passing thread pool's
     * core and maximum size as well as queue's capacity.
     * Makes it available in application's context.
     * 
     * @return the Executor
     */
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorPoolSizeCore);
        executor.setMaxPoolSize(executorPoolSizeMax);
        executor.setQueueCapacity(executorQueueCapacity);
        executor.initialize();
        
        return executor;
    }
}
