package com.ash.eureka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class MainServiceController {

	@Bean	
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
	@Autowired
    private DiscoveryClient discoveryClient;
	
	@RequestMapping(value="/addnumbers",method=RequestMethod.POST)
	public int add(@RequestParam("a") int a,@RequestParam("b") int b) throws ClientProtocolException, IOException{
		 
		 
		 int result=0;
		 // get hold of a service instance from Eureka
		 ServiceInstance instance = null;
		 // "addition-service" is the name of the service in Eureka
		 List<ServiceInstance> instances = discoveryClient
		    .getInstances("addition-service");
		 if (instances != null && instances.size() > 0) {
		 instance = instances.get(0); //could be random
		 // Invoke server based on host and port. 
		 // Example using RestTemplate.    
		 URI productUri = URI.create(String
		   .format("http://%s:%s/add/",
		    instance.getHost(), instance.getPort()));
		 System.out.println("****************** "+productUri.toString()+" ********************* ");
		 Map<String, Object> uriVariables = new HashMap<String, Object>();
		 
		 uriVariables.put("a",a);
		 uriVariables.put("b",b);
		 
		 MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("a", a+"");
			map.add("b", b+"");
//		 valueMap.put(key, value)
		// Create the http entity for the request
//		 HttpHeaders requestHeaders = new HttpHeaders();
//		 requestHeaders.setContentType(MediaType.TEXT_PLAIN);
	//	 HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<Map<String, Integer>>(uriVariables, requestHeaders);
		 HttpClient client=HttpClientBuilder.create().build();
		 HttpPost post = new HttpPost(productUri.toString());
		 
		 List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("a", a+""));
			urlParameters.add(new BasicNameValuePair("b", b+""));
			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpEntity entity=new HttpEntity<>(map);
			String templetResult = restTemplate().postForObject(productUri, map, String.class);
			System.out.println("****************** ----------------- "+templetResult);
			
			HttpResponse response = client.execute(post);
			System.out.println("Response Code : " 
		                + response.getStatusLine().getStatusCode());
			BufferedReader rd = new BufferedReader(
			        new InputStreamReader(response.getEntity().getContent()));

			StringBuffer resultBuffer = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				resultBuffer.append(line);
			}
			result=Integer.parseInt(resultBuffer.toString());
		 //result =restTemplate().postForEntity(productUri.toString(), uriVariables,Integer.class).getStatusCode().value();
	//	result =this.restTemplate().postForObject(/*productUri.toString()*/"http://192.168.0.101:9000/add/", uriVariables,Integer.class);
		//ResponseEntity<Integer> exchange		 //exchange(productUri.toString(), HttpMethod.POST, null, Integer.class, a,b);
//		 result= response.getBody();
		 }
		  return result;  
        // use the "smart" Eureka-aware RestTemplate
        /*ResponseEntity<Integer> exchange =
                this.restTemplate.exchange(
                        "http://addition-service/{userId}/bookmarks",
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<Integer>() {
                        },
                        (Object) "mstine");
        */
		
	}
}
