package com.example.grpc.grpcclient.controller;

import com.example.grpc.grpcclient.dto.Rights;
import com.example.grpc.grpcclient.dto.User;
import com.example.grpc.grpcclient.service.RedisReactiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private RedisReactiveService redisReactiveService;

  @GetMapping("/{id}")
  public Mono<User> get(@PathVariable long id) {
    return redisReactiveService.get(id);
  }

  /*
  http://localhost:8084/user/save
  {
      "id": 1,
      "name": "Lala",
      "lable": "golden",
      "deliveryAddressLon": 135.5,
      "deliveryAddressLat": 23.5,
      "lastSigninDay": "20220505",
      "score": 55,
      "rights":[
          {
          "id": 1,
          "userId": 2,
          "name": "Lala"
          },
          {}
      ]
  }
  */
  @PostMapping("/save")
  @ResponseBody
  public Mono<Boolean> post(@RequestBody User user) {
    return redisReactiveService.save(user);
  }

  @GetMapping("/initWarehouse")
  public Mono<String> initWarehouse() {
    redisReactiveService.initWarehouse();
    return Mono.just("ok");
  }

  @GetMapping("/warehouse")
  public Flux<GeoResult<GeoLocation<String>>> getWarehouse(@RequestBody User user, @RequestParam double distance) {
    return redisReactiveService.getWarehouseInDist(user, distance);
  }

  @PostMapping("/rights")
  public Mono<RecordId> addRights(@RequestBody Rights rights) {
    return redisReactiveService.addRights(rights);
  }
}
