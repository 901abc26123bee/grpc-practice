package com.example.grpc.grpcclient.controller;

import com.example.grpc.grpcclient.service.impl.GrpcHelloClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test1")
public class HelloController {
  @Autowired
  GrpcHelloClientService grpcClientService;

  // post http://localhost:8084/test1/hello?name=Atom
  @PostMapping("/hello")
  @ResponseBody
  public String sayHello(@RequestParam(value = "name") String name) {
    grpcClientService.receiveGreeting(name);
    return "OK";
  }
}
