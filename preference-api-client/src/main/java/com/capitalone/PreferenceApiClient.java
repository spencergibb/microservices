package com.capitalone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@EnableFeignClients
@RestController
public class PreferenceApiClient {

    @Autowired
    PreferenceClient client;

    @RequestMapping("/hellopreferences")
    public String helloPreferences() {
        return String.format("Server response: %s}", client.hello());
    }


    @FeignClient("preferences")
    interface PreferenceClient {
        @RequestMapping(value = "/hello", method = GET)
        String hello();
    }
}
