package com.app.backendforfrontend.controller;

import com.app.backendforfrontend.model.RequestClass;
import com.app.backendforfrontend.parsing.Parser;
import com.hayden.dynamicparse.parse.DynamicParsingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class ParseController {

  private final Parser parser;

  public ParseController(Parser parser) {
    this.parser = parser;
  }

  @PostMapping("/parseJsonRequest")
  public Mono<ResponseEntity<RequestClass>> parsedString(@RequestBody String toParse) throws DynamicParsingException, IOException {
    var r = new RequestClass();
    r.setRequest(parser.parse(toParse).get());
    return Mono.just(ResponseEntity.ok(r));
  }

  @PostMapping("/isLoggedIn")
  public Mono<Boolean> isNotLoggedIn(@AuthenticationPrincipal AuthenticatedPrincipal principal) throws DynamicParsingException, IOException {
    return Mono.just(principal == null);
  }

}
