package br.com.apexitsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
	
	@Autowired
    private DiscoveryClient discoveryClient;
	
	@RequestMapping("/service")
    public String accounts() {
        return "Hello! This is the response from the service. It is working :)";
    }

}
