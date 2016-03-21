package br.com.apexitsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientScheduledTask {
	
	private static final String SERVICE_NAME = "SERVICE";

	private static final Logger LOG = LoggerFactory.getLogger(ClientScheduledTask.class);
	
	@Autowired
	@LoadBalanced
    private RestTemplate restTemplate;
	
	@Scheduled(fixedDelay=10000)
	public void callService() {
		String url = "http://" + SERVICE_NAME + "/service";
		String response = restTemplate.getForObject(url, String.class);
		LOG.info("This is the response from the service: [{}]", response);
	}
	
}
