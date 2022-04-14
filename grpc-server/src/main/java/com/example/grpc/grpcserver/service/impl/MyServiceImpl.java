package com.example.grpc.grpcserver.service.impl;

import com.example.grpc.HelloReply;
import com.example.grpc.HelloRequest;
import com.example.grpc.MyServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class MyServiceImpl extends MyServiceGrpc.MyServiceImplBase {

  @Override
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
    HelloReply reply = HelloReply.newBuilder()
        .setMessage("Hello " + request.getName())
        .build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

}