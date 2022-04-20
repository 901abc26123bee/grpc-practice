package com.example.grpc.grpcclient.controller;


import com.example.grpc.grpcclient.dto.InputRequest;
import com.example.grpc.grpcclient.dto.OutputResponse;
import com.example.grpc.grpcclient.service.impl.ReactiveCaculatorServiceGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CaculatorController {

  @Autowired
  ReactiveCaculatorServiceGrpcClient reactiveCaculatorServiceGrpcClient;


  @RequestMapping(value = "/square", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping("/square")
  @ResponseBody
  public Mono<OutputResponse> unaryFindSquare(@RequestBody InputRequest inputRequest) {
    return reactiveCaculatorServiceGrpcClient.findSquare(inputRequest.getNumber());
//    Mono<OutputResponse> output = reactiveCaculatorServiceGrpcClient.findSquare(inputRequest.getNumber());
//    return output.map( res -> {
//      OutputResponse outputResponse = null;
//      ObjectMapper mapper = new ObjectMapper();
//      try {
//        String str = ProtoJsonUtils.toJson((Message) output);
//        outputResponse = mapper.readValue(str, OutputResponse.class);
//      } catch (InvalidProtocolBufferException | JsonProcessingException e) {
//        e.printStackTrace();
//      }
//      return outputResponse;
//    }).subscribeOn(Schedulers.boundedElastic());
  }

  @RequestMapping(value = "factor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Flux<OutputResponse> serverStreamFind(@RequestParam(value = "num")  int num) {
    return reactiveCaculatorServiceGrpcClient.findFactors(num);
  }


  @RequestMapping(value = "sum", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Mono<OutputResponse> clientStreamSumAll(@RequestBody InputRequest inputRequest) {
    return reactiveCaculatorServiceGrpcClient.sumAll(inputRequest.getNumber(), inputRequest.getNumber() + 10);
  }
}
