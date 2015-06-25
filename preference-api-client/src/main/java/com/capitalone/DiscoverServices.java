package com.capitalone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.SubtypeModule;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.consul.bus.SimpleRemoteEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class DiscoverServices implements ApplicationListener<SimpleRemoteEvent> {

    public static final String CLIENT_NAME = "preferences";

    private static final Logger  log = LoggerFactory.getLogger(DiscoverServices.class);

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("/instances")
    public List<ServiceInstance> instances() {
        return discoveryClient.getInstances(CLIENT_NAME);
    }

    @RequestMapping("/services")
    public List<String> services() {
        return discoveryClient.getServices();
    }

    @Bean
    public SubtypeModule sampleSubtypeModule() {
        return new SubtypeModule(SimpleRemoteEvent.class);
    }

    public void onApplicationEvent(SimpleRemoteEvent event) {
        log.info("Received event: {}", event);
    }

    public static void main(String[] args) {
        SpringApplication.run(DiscoverServices.class, args);
    }
}
