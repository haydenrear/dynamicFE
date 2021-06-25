package com.app.backendforfrontend;

import com.app.backendforfrontend.controller.ParseController;
import com.app.backendforfrontend.model.RequestClass;
import com.app.backendforfrontend.parsing.Parser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.File;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class BackendforfrontendApplicationTests {


  @Autowired
  Parser parser;
  @Autowired
  WebTestClient webTestClient;
  @Autowired
  ParseController parseController;
  String testRequest = "{\"hello\": \"goodbye\"}";


  @Test
  void contextLoads() {
  }

  @SneakyThrows
  @Test
  public void testController(){
    StepVerifier.create(parseController.parsedString(testRequest))
      .expectSubscription()
      .consumeNextWith(System.out::println)
      .verifyComplete();
  }

  @SneakyThrows
  @Test
  public void deleteFiles(){
    StepVerifier.create(parseController.parsedString(testRequest))
      .expectSubscription()
      .consumeNextWith(System.out::println)
      .verifyComplete();
    assertThat(Objects.requireNonNull(new File("src/main/resources/dynamic/").listFiles()).length).isEqualTo(0);
  }

  @SneakyThrows
  @Test
  public void testParser(){
    var returned = parser.parse(testRequest);
    assertThat(returned).isPresent();
    returned.map(returnedFound -> {
      assertThat(returnedFound).contains("hello");
      assertThat(returnedFound).contains("String");
      System.out.println(returnedFound);
      return returnedFound;
    });
  }

  @SneakyThrows
  @Test
  public void testParserTwo(){
    var returned = parser.parse(testRequest);
    var returnedTwo = parser.parse("{\"ok\":[1,2,3]}");
    assertThat(returned).isPresent();
    returned.map(returnedFound -> {
      assertThat(returnedFound).contains("hello");
      assertThat(returnedFound).contains("String");
//      System.out.println(returnedFound);
      return returnedFound;
    });
    System.out.println(returnedTwo.get());
  }

  @SneakyThrows
  @Test
  public void testSecurity(){
    var r = new RequestClass();
    r.setRequest(testRequest);
    webTestClient.get()
      .uri("/")
      .exchange()
      .expectStatus()
      .is2xxSuccessful();
    webTestClient.post()
      .uri("/parseJson")
      .contentType(MediaType.APPLICATION_JSON)
      .body(Mono.just(testRequest), String.class)
      .exchange()
      .expectStatus()
      .is4xxClientError();
    webTestClient.mutateWith(mockJwt())
      .mutateWith(SecurityMockServerConfigurers.csrf())
      .post()
      .uri("/parseJson")
      .contentType(MediaType.APPLICATION_JSON)
      .body(Mono.just(testRequest), String.class)
      .exchange()
      .expectStatus()
      .is2xxSuccessful();
  }







}
