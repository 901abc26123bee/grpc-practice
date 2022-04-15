package com.example.grpc.grpcclient.controller;

import com.example.grpc.StockResponse;
import com.example.grpc.grpcclient.service.impl.GrpcStockClientService;
import com.example.grpc.grpcclient.util.ProtoJsonUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

  @Autowired
  private GrpcStockClientService grpcStockClientService;

  @RequestMapping("/unaryGetStock")
  public ResponseEntity<com.example.grpc.grpcclient.dto.StockResponse> unaryGetStock(@RequestParam(value = "id") String id) {
    try {
      StockResponse response = grpcStockClientService.unaryGetStock(Integer.parseInt(id));
      String json = ProtoJsonUtil.toJson(response);
      com.example.grpc.grpcclient.dto.StockResponse result = new Gson().fromJson(json, com.example.grpc.grpcclient.dto.StockResponse.class);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping("/serverSideStreamingGetStock")
  public ResponseEntity<String> serverSideStreamingGetStock(@RequestParam(value = "prefix") String prefix) {
    try {
      grpcStockClientService.serverSideStreamingGetStock(prefix);
      return new ResponseEntity<>("success", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping("/clientSideStreamingGetStatisticsOfStocks")
  public ResponseEntity<String> clientSideStreamingGetStatisticsOfStocks() {
    try {
      grpcStockClientService.clientSideStreamingGetStatisticsOfStocks();
      return new ResponseEntity<>("success", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping("/bidirectionalStreamingGetListOfStocks")
  public ResponseEntity<String> bidirectionalStreamingGetListOfStocks() {
    try {
      grpcStockClientService.bidirectionalStreamingGetListOfStocks();
      return new ResponseEntity<>("success", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}

