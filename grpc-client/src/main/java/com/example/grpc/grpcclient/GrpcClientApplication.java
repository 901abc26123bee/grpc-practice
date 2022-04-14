package com.example.grpc.grpcclient;

import com.example.grpc.grpcclient.service.impl.GrpcHelloClientService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
	}

}
