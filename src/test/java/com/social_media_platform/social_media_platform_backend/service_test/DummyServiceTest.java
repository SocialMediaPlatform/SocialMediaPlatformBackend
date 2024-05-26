package com.social_media_platform.social_media_platform_backend.service_test;

import com.social_media_platform.social_media_platform_backend.services.DummyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DummyServiceTest {

  @Autowired private DummyService dummyService;

  @Test
  public void testGreet() {
    String result = dummyService.greet();
    assertThat(result).isEqualTo("Hello, World!");
  }
}
