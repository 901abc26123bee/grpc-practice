package com.example.grpc.grpcclient.controller;

import com.example.grpc.grpcclient.dto.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
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
@RequestMapping("/redis")
public class RedisController {

  @Autowired
  private ReactiveRedisTemplate myreactiveRedisTemplate;

  @GetMapping("/put/{key}/{val}")
  @ResponseBody
  public Mono put(@PathVariable("key") String key, @PathVariable("val") String val) {
    return myreactiveRedisTemplate.opsForValue().set(key, val);
  }
  
//@GetMapping("/put")
//@ResponseBody
//public Mono put(@RequestParam("key") String key, @RequestParam("val") String val) {
//  return myreactiveRedisTemplate.opsForValue().set(key, val);
//}

  @GetMapping("/get")
  @ResponseBody
  public Mono get(@RequestParam("key") String key) {
    return myreactiveRedisTemplate.opsForValue().get(key);
  }

  @GetMapping("/addList")
  @ResponseBody
  public Mono<Long> addList(@RequestParam("key") String key, @RequestParam("val") String val) {
    return myreactiveRedisTemplate.opsForList().rightPush(key, val);
  }

  @GetMapping("/getList")
  @ResponseBody
  public Flux<String> getList(@RequestParam("key") String key) {
    return myreactiveRedisTemplate.opsForList().range(key, 0L, Long.MAX_VALUE);
  }

  @GetMapping("/popList")
  @ResponseBody
  public Mono<String> popList(@RequestParam("key") String key) {
    return myreactiveRedisTemplate.opsForList().rightPop(key);
  }

  @GetMapping("/setHash")
  @ResponseBody
  public Mono<Boolean> setHash(@RequestParam("key") String key, @RequestParam("hashKey") String hashKey, @RequestParam("val") String val) {
    return myreactiveRedisTemplate.opsForHash().put(key, hashKey, val);
  }

  @GetMapping("/getHash")
  @ResponseBody
  public Flux<Map.Entry<String, String>> getHash(@RequestParam("key") String key) {
    return myreactiveRedisTemplate.opsForHash().entries(key);
  }

}
