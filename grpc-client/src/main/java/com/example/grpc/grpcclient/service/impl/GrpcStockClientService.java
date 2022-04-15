package com.example.grpc.grpcclient.service.impl;

import com.example.grpc.StockProductNameRequest;
import com.example.grpc.StockRequest;
import com.example.grpc.StockResponse;
import com.example.grpc.StockServiceGrpc;
import com.example.grpc.StockStatisticsResponse;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.Iterator;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrpcStockClientService {
  @GrpcClient("server-stream-server-side")
  private StockServiceGrpc.StockServiceBlockingStub stockServiceBlockingStub;

  @GrpcClient("server-stream-server-side")
  private StockServiceGrpc.StockServiceStub stockServiceStub;

  Logger logger = LoggerFactory.getLogger(GrpcStockClientService.class);

  public StockResponse unaryGetStock(int id) {
    StockRequest request = StockRequest.newBuilder().setId(id).build();
    StockResponse response = StockResponse.newBuilder().build();
    try {
      logger.info("[unaryGetStock] - server return a response ... ");
      response = stockServiceBlockingStub.unaryGetStock(request);
      logger.info("RESPONSE - id #{} {}: ${}/{} ",
          response.getId(),
          response.getProductName(),
          response.getPrice(),
          response.getPrice());
      logger.info("[unaryGetStock] - Finished!");
      return response;
    } catch (StatusRuntimeException e) {
      logger.error("[unaryGetStock] - RPC failed: {}", String.valueOf(e));
    }
    return response;
  }

  public void serverSideStreamingGetStock(String prefix) {
    StockProductNameRequest request = StockProductNameRequest.newBuilder().setPrefix(prefix).build();
    Iterator<StockResponse> stockResponses;
    try {
      logger.info("[serverSideStreamingGetStock] - server return streaming response ... ");
      stockResponses = stockServiceBlockingStub.serverSideStreamingGetStock(request);
      for (int i = 0; stockResponses.hasNext(); i++) {
        StockResponse response = stockResponses.next();
        logger.info("RESPONSE - id #{} {}: ${}/{} ",
            response.getId(),
            response.getProductName(),
            response.getPrice(),
            response.getPrice());
      }
      logger.info("[serverSideStreamingGetStock] - Finished!");
    } catch (StatusRuntimeException e) {
      logger.error("[serverSideStreamingGetStock] - RPC failed: {}", String.valueOf(e));
    }
  }

  public void clientSideStreamingGetStatisticsOfStocks() {
    logger.info("[clientSideStreamingGetStatisticsOfStocks] - server return a response ... ");
    StreamObserver<StockStatisticsResponse> responseStreamObserver = new StreamObserver<StockStatisticsResponse>() {
      @Override
      public void onNext(StockStatisticsResponse value) {
        logger.info("RESPONSE - all stock's total value is ${}", value.getTotalValue());
      }

      @Override
      public void onError(Throwable t) {
        logger.error("[clientSideStreamingGetStatisticsOfStocks] - RPC failed: {}", String.valueOf(t));
      }

      @Override
      public void onCompleted() {
        logger.info("[clientSideStreamingGetStatisticsOfStocks] - Finished!");

      }
    };
    StreamObserver<StockRequest> requestStreamObserver = stockServiceStub.clientSideStreamingGetStatisticsOfStocks(responseStreamObserver);
    try {
      for (int i = 1; i <= 6; i++) {
        requestStreamObserver.onNext(StockRequest.newBuilder().setId(i).build());
      }

    } catch (RuntimeException e) {
      requestStreamObserver.onError(e);
      throw e;
    }
    requestStreamObserver.onCompleted();
  }

  public void bidirectionalStreamingGetListOfStocks() {
    logger.info("[bidirectionalStreamingGetListOfStocks] - server return streaming response ... ");
    StreamObserver<StockResponse> responseStreamObserver = new StreamObserver<StockResponse>() {
      @Override
      public void onNext(StockResponse value) {

        logger.info("RESPONSE - id #{}, {}, ${}, {}", value.getId(), value.getProductName(), value.getPrice(), value.getOfferNumber());

      }

      @Override
      public void onError(Throwable t) {
        logger.error("[bidirectionalStreamingGetListOfStocks] - RPC failed: {}", String.valueOf(t));
      }

      @Override
      public void onCompleted() {
        logger.info("[bidirectionalStreamingGetListOfStocks] - Finished!");
      }
    };

    StreamObserver<StockProductNameRequest> requestStreamObserver = stockServiceStub.bidirectionalStreamingGetListOfStocks(responseStreamObserver);
    try {
      requestStreamObserver.onNext(StockProductNameRequest.newBuilder().setPrefix("n").build());
      requestStreamObserver.onNext(StockProductNameRequest.newBuilder().setPrefix("p").build());
      Thread.sleep(200);
    } catch (RuntimeException | InterruptedException e) {
      requestStreamObserver.onError(e);
    }
    requestStreamObserver.onCompleted();
  }

}

