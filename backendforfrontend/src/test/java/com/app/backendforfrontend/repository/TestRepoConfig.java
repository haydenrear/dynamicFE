package com.app.backendforfrontend.repository;

import com.app.backendforfrontend.config.Config;
import com.app.backendforfrontend.repo.PostRepo;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
public class TestRepoConfig {

  @Container
  public static GenericContainer<?> containerCreated = new GenericContainer<>(DockerImageName.parse("mongo:latest"));

  @Test
  public void test(){
    System.out.println("hello");
  }

  @BeforeAll
  public static void before(){
    containerCreated.setExposedPorts(List.of(9000));
    containerCreated.addEnv("MONGO_INITDB_ROOT_USERNAME", "admin");
    containerCreated.addEnv("MONGO_INITDB_ROOT_PASSWORD", "admin");
  }

}
