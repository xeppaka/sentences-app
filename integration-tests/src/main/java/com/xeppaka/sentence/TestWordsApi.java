package com.xeppaka.sentence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestWordsApi {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSaveSeveralWords() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String json1 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        ResponseEntity<String> jsonResponse = restTemplate.exchange("/words/Eubicor", HttpMethod.PUT, new HttpEntity<>(json1, headers), String.class);
        assertThat(jsonResponse.getBody(), jsonEquals("{\"word\":{\"word\":\"Eubicor\",\"wordCategories\":[\"VERB\"]}}"));

        final String json2 = "{\"word\":{\"wordCategories\":[\"VERB\",\"NOUN\"]}}";
        jsonResponse = restTemplate.exchange("/words/Porsche", HttpMethod.PUT, new HttpEntity<>(json2, headers), String.class);
        assertThat(jsonResponse.getBody(), jsonEquals("{\"word\":{\"word\":\"Porsche\",\"wordCategories\":[\"VERB\",\"NOUN\"]}}").when(IGNORING_ARRAY_ORDER));

        final String json3 = "{\"word\":{\"wordCategories\":[\"VERB\",\"NOUN\",\"ADJECTIVE\"]}}";
        jsonResponse = restTemplate.exchange("/words/VW", HttpMethod.PUT, new HttpEntity<>(json3, headers), String.class);
        assertThat(jsonResponse.getBody(), jsonEquals("{\"word\":{\"word\":\"VW\",\"wordCategories\":[\"VERB\",\"NOUN\",\"ADJECTIVE\"]}}").when(IGNORING_ARRAY_ORDER));
    }

    @Test
    public void testGetWords() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String json1 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        restTemplate.exchange("/words/Eubicor", HttpMethod.PUT, new HttpEntity<>(json1, headers), String.class);
        final String json2 = "{\"word\":{\"wordCategories\":[\"VERB\",\"NOUN\"]}}";
        restTemplate.exchange("/words/Porsche", HttpMethod.PUT, new HttpEntity<>(json2, headers), String.class);
        final String json3 = "{\"word\":{\"wordCategories\":[\"VERB\",\"NOUN\",\"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/VW", HttpMethod.PUT, new HttpEntity<>(json3, headers), String.class);

        final ResponseEntity<String> eubicorResponse = restTemplate.getForEntity("/words/eubicor", String.class);
        assertThat(eubicorResponse.getBody(), jsonEquals("{\"word\":{\"word\":\"Eubicor\",\"wordCategories\":[\"VERB\"]}}").when(IGNORING_ARRAY_ORDER));

        final ResponseEntity<String> porscheResponse = restTemplate.getForEntity("/words/porsche", String.class);
        assertThat(porscheResponse.getBody(), jsonEquals("{\"word\":{\"word\":\"Porsche\",\"wordCategories\":[\"VERB\",\"NOUN\"]}}").when(IGNORING_ARRAY_ORDER));

        final ResponseEntity<String> vwResponse = restTemplate.getForEntity("/words/vw", String.class);
        assertThat(vwResponse.getBody(), jsonEquals("{\"word\":{\"word\":\"VW\",\"wordCategories\":[\"VERB\",\"NOUN\",\"ADJECTIVE\"]}}").when(IGNORING_ARRAY_ORDER));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testGetAllWords() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String json1 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        restTemplate.exchange("/words/Eubicor", HttpMethod.PUT, new HttpEntity<>(json1, headers), String.class);
        final String json2 = "{\"word\":{\"wordCategories\":[\"VERB\",\"NOUN\"]}}";
        restTemplate.exchange("/words/Porsche", HttpMethod.PUT, new HttpEntity<>(json2, headers), String.class);
        final String json3 = "{\"word\":{\"wordCategories\":[\"VERB\",\"NOUN\",\"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/VW", HttpMethod.PUT, new HttpEntity<>(json3, headers), String.class);
        final String json4 = "{\"word\":{\"wordCategories\":[\"VERB\",\"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/Micro", HttpMethod.PUT, new HttpEntity<>(json4, headers), String.class);

        final ResponseEntity<String> allResponse = restTemplate.getForEntity("/words", String.class);
        assertThat(allResponse.getBody(), jsonEquals("{\"words\":{\"wordsList\":[{\"word\":\"Eubicor\",\"wordCategories\":[\"VERB\"]},{\"word\":\"Porsche\",\"wordCategories\":[\"NOUN\",\"VERB\"]},{\"word\":\"VW\",\"wordCategories\":[\"NOUN\",\"VERB\",\"ADJECTIVE\"]},{\"word\":\"Micro\",\"wordCategories\":[\"VERB\",\"ADJECTIVE\"]}],\"wordsCount\":4}}").when(IGNORING_ARRAY_ORDER));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testGetEmptyWordsList() {
        final ResponseEntity<String> jsonResponse = restTemplate.getForEntity("/words", String.class);
        assertThat(jsonResponse.getBody(), jsonEquals("{\"words\":{\"wordsList\":[],\"wordsCount\":0}}"));
    }

    @Test
    public void testUnknownWordReturns404() {
        final ResponseEntity<String> response = restTemplate.getForEntity("/words/blahblah123", String.class);
        assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void testPostNotAllowed() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.postForEntity("/words", new HttpEntity<>("something", headers), String.class);
        assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.METHOD_NOT_ALLOWED.value()));

        response = restTemplate.postForEntity("/words/word123", new HttpEntity<>("something", headers), String.class);
        assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.METHOD_NOT_ALLOWED.value()));
    }

    @Test
    public void testDeleteNotAllowed() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.exchange("/words", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.METHOD_NOT_ALLOWED.value()));

        response = restTemplate.exchange("/words/word", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.METHOD_NOT_ALLOWED.value()));
    }

    @Test
    public void testPutNotAllowed() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.exchange("/words", HttpMethod.PUT, new HttpEntity<>(headers), String.class);
        assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.METHOD_NOT_ALLOWED.value()));
    }
}
