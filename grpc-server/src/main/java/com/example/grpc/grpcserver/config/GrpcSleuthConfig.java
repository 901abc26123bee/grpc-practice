package com.example.grpc.grpcserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import zipkin2.Span;
import zipkin2.reporter.Reporter;
import brave.Tracing;
import brave.grpc.GrpcTracing;
import net.devh.boot.grpc.server.interceptor.GlobalServerInterceptorConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class GrpcSleuthConfig {
  private static final Logger logger = LoggerFactory.getLogger(GrpcSleuthConfig.class);
  @Bean
  public GrpcTracing grpcTracing(Tracing tracing) {
    return GrpcTracing.create(tracing);
  }

  // Use this for debugging (or if there is no Zipkin server running on port 9411)
  @Bean
  @ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "false")
  public Reporter<Span> spanReporter() {
    return new Reporter<Span>() {
      @Override
      public void report(Span span) {
        logger.info("{}",span);
      }
    };
  }

  @Bean
  public GlobalServerInterceptorConfigurer globalServerInterceptorConfigurer(GrpcTracing grpcTracing) {
    return registry -> registry.add(grpcTracing.newServerInterceptor());
  }
}