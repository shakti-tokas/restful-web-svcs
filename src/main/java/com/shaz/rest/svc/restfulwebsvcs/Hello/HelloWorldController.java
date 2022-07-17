package com.shaz.rest.svc.restfulwebsvcs.Hello;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {

    //@RequestMapping(method = RequestMethod.GET, path = "/hello")
    @GetMapping(path = "/hello")
    public String helloWorld(){

        return "Hello!! world";
    }

    @GetMapping(path = "/hello-bean")
    public HelloWorldBean helloWorldBean(){

        return new HelloWorldBean("Hello! World Bean");
    }

    @GetMapping(path = "/hello-bean/path-var/{name}")
    public HelloWorldBean helloWorldPath(@PathVariable String name){

        return new HelloWorldBean(String.format("Hello! World : %s", name));
    }
}
