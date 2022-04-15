package com.example.grpc.grpcclient;

import com.example.grpc.grpcclient.service.impl.GrpcStockClientService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
	}

	@Autowired
	private GrpcStockClientService service;

	@PostConstruct
	public void doStuff() throws InterruptedException {
		// the service will have been initialized and wired into the field by now
		service.unaryGetStock(1);
		Thread.sleep(200);
		service.serverSideStreamingGetStock("p");
		Thread.sleep(200);
		service.clientSideStreamingGetStatisticsOfStocks();
		Thread.sleep(200);
		service.bidirectionalStreamingGetListOfStocks();
	}
}
