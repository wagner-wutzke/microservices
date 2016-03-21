package br.com.apexitsystems.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ServiceController.class);
	
	@RequestMapping("/service")
    public String accounts() {
		LOG.info("Received request on /service enpoint.");
        return "Hello! The service is working :)";
    }

}
