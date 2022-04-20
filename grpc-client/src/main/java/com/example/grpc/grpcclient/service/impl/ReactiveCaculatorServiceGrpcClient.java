package com.example.grpc.grpcclient.service.impl;

import com.example.grpc.Input;
import com.example.grpc.ReactorCalculatorServiceGrpc.ReactorCalculatorServiceStub;
import com.example.grpc.grpcclient.dto.OutputResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ReactiveCaculatorServiceGrpcClient {

  @GrpcClient("server-stream-server-side")
  private ReactorCalculatorServiceStub reactorCalculatorServiceStub;

  public Mono<OutputResponse> findSquare(int num) {
    Mono<Input> input = Mono.just(
        Input.newBuilder().setNumber(num).build());

    return reactorCalculatorServiceStub.findSquare(input)
        .flatMap(result -> {
          OutputResponse outputResponse = new OutputResponse();
          outputResponse.setNum(result.getResult());
          return Mono.just(outputResponse);
        });
  }

  public Flux<OutputResponse> findFactors(int num) {
    Input input = Input.newBuilder()
        .setNumber(num)
        .build();
    return reactorCalculatorServiceStub.findFactors(input)
        .flatMap(result -> {
          OutputResponse outputResponse = new OutputResponse();
          outputResponse.setNum(result.getResult());
          return Mono.just(outputResponse);
        });
  }

  public Mono<OutputResponse> sumAll(int start, int end) {
    Flux<Input> inputFlux = Flux.range(start, end)
        .map(n -> Input.newBuilder().setNumber(n).build());
    return reactorCalculatorServiceStub.findSum(inputFlux)
        .flatMap(result -> {
          OutputResponse outputResponse = new OutputResponse();
          outputResponse.setNum(result.getResult());
          return Mono.just(outputResponse);
        });
  }

}
