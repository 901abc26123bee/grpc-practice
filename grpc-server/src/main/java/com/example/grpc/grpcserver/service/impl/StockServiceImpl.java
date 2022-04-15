package com.example.grpc.grpcserver.service.impl;

import com.example.grpc.StockProductNameRequest;
import com.example.grpc.StockRequest;
import com.example.grpc.StockResponse;
import com.example.grpc.StockServiceGrpc;
import com.example.grpc.StockStatisticsResponse;
import com.example.grpc.grpcserver.dao.StockDao;
import com.example.grpc.grpcserver.entity.Stock;
import io.grpc.stub.StreamObserver;
import java.util.List;
import javax.transaction.Transactional;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@GrpcService
@Transactional
public class StockServiceImpl extends StockServiceGrpc.StockServiceImplBase {

  @Autowired
  private StockDao stockDao;

  Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

  @Override
  public void unaryGetStock(StockRequest request,
      StreamObserver<StockResponse> responseObserver) {
    logger.info("[unaryGetStock] - client send a request");
    logger.info("REQUEST - id: {}", request.getId());
    Stock stock = stockDao.getById(request.getId());
    StockResponse response = StockResponse.newBuilder()
        .setId(stock.getId())
        .setProductName(stock.getProductName())
        .setPrice(stock.getPrice())
        .setOfferNumber(stock.getOfferNumber())
        .build();
    logger.info("[unaryGetStock] - Finished!");
    responseObserver.onNext(response);
    responseObserver.onCompleted();

  }

  @Override
  public void serverSideStreamingGetStock(StockProductNameRequest request,
      StreamObserver<StockResponse> responseObserver) {
    logger.info("[serverSideStreamingGetStock] - client send a request");
    logger.info("REQUEST - prefix: {}", request.getPrefix());
    List<Stock> stocks = stockDao.findStockByProductNamePrefix(request.getPrefix());
    for (Stock stock : stocks) {
      StockResponse response = StockResponse.newBuilder()
          .setId(stock.getId())
          .setProductName(stock.getProductName())
          .setPrice(stock.getPrice())
          .setOfferNumber(stock.getOfferNumber())
          .build();
      responseObserver.onNext(response);
    }
    logger.info("[serverSideStreamingGetStock] - Finished!");
    responseObserver.onCompleted();

  }

  @Override
  public StreamObserver<StockRequest> clientSideStreamingGetStatisticsOfStocks(
      StreamObserver<StockStatisticsResponse> responseObserver) {
    logger.info("[clientSideStreamingGetStatisticsOfStocks] - client send streaming requests ... ");
    return new StreamObserver<StockRequest>() {

      int totalValue = 0;

      @Override
      public void onNext(StockRequest request) {
        Stock stock = stockDao.getById(request.getId());
        int value = (int) (stock.getOfferNumber() * stock.getPrice());
        totalValue += value;
        logger.info("REQUEST - id: {} , value = {}", String.valueOf(stock.getId()), String.valueOf(value));
      }

      @Override
      public void onError(Throwable t) {
        logger.error("[ERROR] Stream Error!");
      }

      @Override
      public void onCompleted() {
        logger.info("[clientSideStreamingGetStatisticsOfStocks] - Finished!");
        responseObserver.onNext(StockStatisticsResponse.newBuilder().setTotalValue(totalValue).build());
        responseObserver.onCompleted();
      }
    };
  }

  @Override
  public StreamObserver<StockProductNameRequest> bidirectionalStreamingGetListOfStocks(
      StreamObserver<StockResponse> responseObserver) {
    logger.info("[bidirectionalStreamingGetListOfStocks] - client send streaming requests ... ");
    return new StreamObserver<StockProductNameRequest>() {
      @Override
      public void onNext(StockProductNameRequest request) {
        logger.info("REQUEST - prefix: {}", request.getPrefix());
        List<Stock> stocks = stockDao.findStockByProductNamePrefix(request.getPrefix());
        for (Stock s : stocks) {
          StockResponse response = StockResponse.newBuilder()
              .setId(s.getId())
              .setPrice(s.getPrice())
              .setProductName(s.getProductName())
              .setOfferNumber(s.getOfferNumber())
              .build();
          responseObserver.onNext(response);
        }
      }

      @Override
      public void onError(Throwable t) {
        logger.error("[ERROR] Stream Error!");
      }

      @Override
      public void onCompleted() {
        logger.info("[bidirectionalStreamingGetListOfStocks] - Finished!");
        responseObserver.onCompleted();
      }
    };
  }

}
