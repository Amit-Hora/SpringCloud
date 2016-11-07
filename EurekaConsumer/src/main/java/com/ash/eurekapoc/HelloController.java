package com.ash.eurekapoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Controller
public class HelloController {
	  @Autowired
	    protected RestTemplate restTemplate; 
	
	@RequestMapping(method=RequestMethod.GET,value="/")
    public  @ResponseBody String getGreeting() {
		 // use the "smart" Eureka-aware RestTemplate
        
                String greeting=this.restTemplate.exchange(
                        "http://hello/",
                        HttpMethod.GET,
                        null,new ParameterizedTypeReference<String>() {
                        },
                        (Object) "mstine").getBody();
                return greeting;
        
	}
}
