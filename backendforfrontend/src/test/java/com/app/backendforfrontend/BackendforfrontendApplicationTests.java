package com.app.backendforfrontend;

import com.app.backendforfrontend.repository.TestRepoConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(
  properties = {
    "spring.data.mongodb.uri=mongodb://admin:admin@localhost:9000/app?retryWrites=true&w=majority"
  }
)
class BackendforfrontendApplicationTests extends TestRepoConfig {



}
