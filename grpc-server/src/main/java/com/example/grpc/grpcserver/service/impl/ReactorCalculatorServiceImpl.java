package com.example.grpc.grpcserver.service.impl;


import com.example.grpc.Input;
import com.example.grpc.Output;
import com.example.grpc.ReactorCalculatorServiceGrpc;
import com.example.grpc.grpcserver.dto.InputRequest;
import com.example.grpc.grpcserver.util.ProtoJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@GrpcService
public class ReactorCalculatorServiceImpl extends ReactorCalculatorServiceGrpc.CalculatorServiceImplBase {

  // requestporto.class --> flux/mono use dao(self defined class) to deal data and add into message--> cast message to rsponseproto.calass
  @Override
  public Mono<Output> findSquare(Mono<Input> request) {
    System.out.println("1111111111111111");
//    return request.map(r -> r.getNumber())
//        .map(i -> Output.newBuilder().setResult(i * i).build());
    return request.log()
        .flatMap(parameter -> {
          InputRequest inputRequest = new InputRequest();
          inputRequest.setNumber(parameter.getNumber());
          if (parameter.getNumber() == 0) {
            // some default operationi
          }
          // return Mono.zip(Mono.just(inputRequest), Mono.just(...)) // return tuple2 --> getT1==inputRequest, getT2==...
          return Mono.just(inputRequest);
        })
        .map(InputRequest::getNumber)
        .map(i -> Output.newBuilder().setResult(i * i).build());
//        .flatMap(result -> {
//          Message message = null;
//          try {
//            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//            String jsonString = null;
//            jsonString = ow.writeValueAsString(result);
//            message = ProtoJsonUtils.toProtoBean(Output.newBuilder(), jsonString);
//          } catch (JsonProcessingException e) {
//            e.printStackTrace();
//          } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//          }
//          return Mono.just((Output) message);
//        });
  }
  @Override
  public Flux<Output> findFactors(Mono<Input> request) {
    System.out.println("2222222222222222");
    return request.log().map(Input::getNumber)
        .filter(i -> i > 0)
        .flatMapMany(input -> Flux.range(2, input / 2)
            .filter(f -> input % f == 0))
        .map(o -> Output.newBuilder().setResult(o).build())
        .subscribeOn(Schedulers.boundedElastic());
  }
//  @Override
//  public Mono<Output> findSum(Flux<Input> request) {
//    return request.map(Input::getNumber)
//        .reduce((acc, next) -> acc + next)
//        .map(i -> Output.newBuilder().setResult(i).build());
//  }
  @Override
  public Mono<Output> findSum(Flux<Input> request) {
    System.out.println("33333333333333333");
    return request.log()
        .map(Input::getNumber)
        .reduce(Integer::sum)
        .map(i -> Output.newBuilder().setResult(i).build())
        .subscribeOn(Schedulers.boundedElastic());
  }
}