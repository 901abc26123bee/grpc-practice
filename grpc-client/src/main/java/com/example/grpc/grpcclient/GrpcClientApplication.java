package com.example.grpc.grpcclient;

import com.example.grpc.Input;
import com.example.grpc.Output;
import com.example.grpc.grpcclient.service.impl.ReactiveCaculatorServiceGrpcClient;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
	}
//	@Autowired
//	private ReactiveCaculatorServiceGrpcClient reactiveCaculatorServiceGrpcClient;
//
//	@PostConstruct
//	public void doStuff() throws InterruptedException {
//		System.out.println("**********************");
//		reactiveCaculatorServiceGrpcClient.findSquare(5).map(Output::getResult).doOnNext(System.out::println);
//		Thread.sleep(200);
//		reactiveCaculatorServiceGrpcClient.findFactors(55).map(Output::getResult).doOnNext(System.out::println);
//		Thread.sleep(200);
//		reactiveCaculatorServiceGrpcClient.sumAll(22, 33).map(Output::getResult).doOnNext(System.out::println);
//		System.out.println("**********************");
//	}

}
