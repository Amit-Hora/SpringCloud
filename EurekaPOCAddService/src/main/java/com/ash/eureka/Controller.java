package com.ash.eureka;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Controller {

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public int add(@RequestParam("a") int a,@RequestParam("b") int b){
		return a+b;
		
	}
}
