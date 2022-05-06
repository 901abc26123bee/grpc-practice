package com.example.grpc.grpcclient.service;


import com.example.grpc.grpcclient.dto.Rights;
import com.example.grpc.grpcclient.dto.User;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.stream.RecordId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RedisReactiveService {
  Mono<User> get(long id);
  Mono<Boolean>  save(User user);
  void initWarehouse();
  Mono<RecordId> addRights(Rights r);
  Flux<GeoResult<GeoLocation<String>>> getWarehouseInDist(User u, double dist);
}
