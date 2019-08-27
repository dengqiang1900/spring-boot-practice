package com.oliver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class SpringbootApplication {
  public static void main(String[] args){
    SpringApplication.run(SpringbootApplication.class,args);
  }
}
