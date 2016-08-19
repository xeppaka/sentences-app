package com.xeppaka.sentence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test111 {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testtest() {
        final String response = restTemplate.getForObject("/words", String.class);
        System.out.println(response);

        // Assert.assertEquals("test", response);
    }
}
