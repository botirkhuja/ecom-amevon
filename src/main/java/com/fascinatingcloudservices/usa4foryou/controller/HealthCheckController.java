package com.fascinatingcloudservices.usa4foryou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class HealthCheckController {
  @GetMapping("/health")
  public String healthCheck() {
    return "OK";
  }

  @GetMapping("/ping")
  public String ping() {
    return "PONG";
  }

  @GetMapping("/status")
  public String status() {
    return "UP";
  }
  @GetMapping("/alive")
  public String alive() {
    return "ALIVE";
  }
}
