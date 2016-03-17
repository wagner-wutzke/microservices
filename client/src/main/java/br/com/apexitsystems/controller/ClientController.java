package br.com.apexitsystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientController {
	
	@Autowired
    private DiscoveryClient discoveryClient;
	
	@Autowired
	@LoadBalanced
    private RestTemplate restTemplate;
	
	@RequestMapping("/client")
    public String contactService() {
        String url = "http://SERVICE" + "/service";
        return "The client received this response from service: " + 
        		restTemplate.getForObject(url, String.class);
    }
	


}
