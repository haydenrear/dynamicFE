package com.app.backendforfrontend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Printer;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Properties;

@Configuration
@EnableWebFluxSecurity
public class Config {

  @Value("${google.auth.cert.enpoint}")
  String cert;
  @Value("${app.javamail.host}")
  private String mailHost;
  @Value("${app.javamail.port}")
  private int mailPort;
  @Value("${app.javamail.username}")
  private String mailUsername;
  @Value("${app.javamail.password}")
  private String mailPassword;

  @Bean
  ReactiveClientRegistrationRepository registrationRepository() {
    return new InMemoryReactiveClientRegistrationRepository(clientRegistrationGoogle());
  }

  @Bean
  public ObjectMapper om(){
    return new ObjectMapper();
  }

  @Bean
  ClientRegistration clientRegistrationGoogle() {
    return CommonOAuth2Provider.GOOGLE
      .getBuilder("google")
      .clientId("684390368964-6nfsrd7fi33ff4ac9uu686f1k3ve3rsl.apps.googleusercontent.com")
      .clientSecret("***REMOVED***")
      .build();
  }

  @Bean
  SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
    http.oauth2Client().and().csrf().disable()
      .authorizeExchange(authorize -> authorize
        .pathMatchers(HttpMethod.GET, "/**").authenticated()
        .pathMatchers(HttpMethod.POST, "/parseJson/**").authenticated()
        .pathMatchers(HttpMethod.POST, "/parseJsonRequest/**").permitAll()
      ).oauth2Login()
      .clientRegistrationRepository(registrationRepository())
      .and().oauth2ResourceServer().jwt();
    return http.build();
  }

  @Bean
  ReactiveJwtDecoder decoder() {
    return NimbusReactiveJwtDecoder.withJwkSetUri(cert).build();
  }

  @Bean
  public WebClient.Builder builder() {
    return WebClient.builder();
  }

  @Bean
  public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
    ReactiveClientRegistrationRepository clientRegistrationRepository,
    ReactiveOAuth2AuthorizedClientService authorizedClientService) {
    ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
      ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
        .clientCredentials()
        .build();
    AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
      new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
        clientRegistrationRepository, authorizedClientService);
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

    return authorizedClientManager;
  }

  @Bean
  public JavaMailSender javaMail() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost(mailHost);
    javaMailSender.setPort(mailPort);
    javaMailSender.setUsername(mailUsername);
    javaMailSender.setPassword(mailPassword);

    Properties properties = javaMailSender.getJavaMailProperties();
    properties.put("mail.transport.protocol", "smtp");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.debug", "true");
    return javaMailSender;
  }

}
