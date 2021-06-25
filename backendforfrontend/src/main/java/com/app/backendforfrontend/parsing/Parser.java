package com.app.backendforfrontend.parsing;

import com.***REMOVED***.dynamicparse.parse.DynamicParseJson;
import com.***REMOVED***.dynamicparse.parse.DynamicParsingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Log4j2
public class Parser {

  DynamicParseJson dynamicParseJson;

  public Parser(DynamicParseJson dynamicParseJson) {
    this.dynamicParseJson = dynamicParseJson;
  }

  public Optional<String> parse(String json) throws DynamicParsingException, IOException {

    String directoryTo = UUID.randomUUID().toString().replace("/", "");
    String directory = "src/main/resources/dynamic/"+directoryTo;
    String fileName = UUID.randomUUID().toString();


    var toReturn = dynamicParseJson.dynamicParse(json, fileName, Optional.empty(), Optional.of(directory))
      .map(clzz -> dynamicParseJson.decompile(clzz.clzz().getName()));

    File file = new File(directory+".class");
    file.delete();

    return toReturn;

  }

}