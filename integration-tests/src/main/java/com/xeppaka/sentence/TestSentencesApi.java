package com.xeppaka.sentence;

import com.xeppaka.sentence.sentences.SentenceDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonPartEquals;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonPartMatches;
import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestSentencesApi {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testGenerateSentence() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String json1 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        restTemplate.exchange("/words/Is", HttpMethod.PUT, new HttpEntity<>(json1, headers), String.class);
        final String json2 = "{\"word\":{\"wordCategories\":[\"NOUN\"]}}";
        restTemplate.exchange("/words/Summer", HttpMethod.PUT, new HttpEntity<>(json2, headers), String.class);
        final String json3 = "{\"word\":{\"wordCategories\":[\"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/Nice", HttpMethod.PUT, new HttpEntity<>(json3, headers), String.class);

        final ResponseEntity<String> jsonSentence = restTemplate.postForEntity("/sentences/generate", new HttpEntity<>(headers), String.class);
        assertThat(jsonSentence.getBody(), jsonPartEquals("sentence.text", "Summer Is Nice"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testGetAllSentences() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String json1 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        restTemplate.exchange("/words/Is", HttpMethod.PUT, new HttpEntity<>(json1, headers), String.class);
        final String json2 = "{\"word\":{\"wordCategories\":[\"NOUN\"]}}";
        restTemplate.exchange("/words/Summer", HttpMethod.PUT, new HttpEntity<>(json2, headers), String.class);
        final String json3 = "{\"word\":{\"wordCategories\":[\"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/Nice", HttpMethod.PUT, new HttpEntity<>(json3, headers), String.class);
        final String json4 = "{\"word\":{\"wordCategories\":[\"NOUN\", \"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/Cookie", HttpMethod.PUT, new HttpEntity<>(json4, headers), String.class);
        final String json5 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        restTemplate.exchange("/words/Stay", HttpMethod.PUT, new HttpEntity<>(json5, headers), String.class);
        final String json6 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        restTemplate.exchange("/words/Run", HttpMethod.PUT, new HttpEntity<>(json6, headers), String.class);
        final String json7 = "{\"word\":{\"wordCategories\":[\"NOUN\", \"VERB\"]}}";
        restTemplate.exchange("/words/Act", HttpMethod.PUT, new HttpEntity<>(json7, headers), String.class);
        final String json8 = "{\"word\":{\"wordCategories\":[\"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/Cold", HttpMethod.PUT, new HttpEntity<>(json8, headers), String.class);

        final int N = 50;
        for (int i = 0; i < N; ++i) {
            restTemplate.postForEntity("/sentences/generate", new HttpEntity<>(headers), String.class);
        }
        final ResponseEntity<String> jsonSentences = restTemplate.getForEntity("/sentences", String.class);

        assertThatJson(jsonSentences.getBody()).node("sentences.sentencesCount").isEqualTo(N);
        assertThatJson(jsonSentences.getBody()).node("sentences.sentenceList").isArray();
        assertThatJson(jsonSentences.getBody()).node("sentences.sentenceList").matches(everyItem(
                jsonPartMatches("text", anyOf(startsWith("Summer"), startsWith("Cookie"), startsWith("Act"))))
        );
        assertThatJson(jsonSentences.getBody()).node("sentences.sentenceList").matches(everyItem(
                jsonPartMatches("text", anyOf(endsWith("Cold"), endsWith("Nice"), endsWith("Cookie"))))
        );
        assertThatJson(jsonSentences.getBody()).node("sentences.sentenceList").matches(everyItem(
                jsonPartMatches("text", anyOf(containsString("Is"), containsString("Stay"), containsString("Run"), containsString("Act"))))
        );
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testGetYodaSentences() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String json1 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        restTemplate.exchange("/words/Is", HttpMethod.PUT, new HttpEntity<>(json1, headers), String.class);
        final String json2 = "{\"word\":{\"wordCategories\":[\"NOUN\"]}}";
        restTemplate.exchange("/words/Summer", HttpMethod.PUT, new HttpEntity<>(json2, headers), String.class);
        final String json3 = "{\"word\":{\"wordCategories\":[\"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/Nice", HttpMethod.PUT, new HttpEntity<>(json3, headers), String.class);
        final String json4 = "{\"word\":{\"wordCategories\":[\"NOUN\", \"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/Cookie", HttpMethod.PUT, new HttpEntity<>(json4, headers), String.class);
        final String json5 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        restTemplate.exchange("/words/Stay", HttpMethod.PUT, new HttpEntity<>(json5, headers), String.class);
        final String json6 = "{\"word\":{\"wordCategories\":[\"VERB\"]}}";
        restTemplate.exchange("/words/Run", HttpMethod.PUT, new HttpEntity<>(json6, headers), String.class);
        final String json7 = "{\"word\":{\"wordCategories\":[\"NOUN\", \"VERB\"]}}";
        restTemplate.exchange("/words/Act", HttpMethod.PUT, new HttpEntity<>(json7, headers), String.class);
        final String json8 = "{\"word\":{\"wordCategories\":[\"ADJECTIVE\"]}}";
        restTemplate.exchange("/words/Cold", HttpMethod.PUT, new HttpEntity<>(json8, headers), String.class);

        final int N = 50;
        for (int i = 0; i < N; ++i) {
            ResponseEntity<SentenceDto> generatedSentence = restTemplate.postForEntity("/sentences/generate", new HttpEntity<>(headers), SentenceDto.class);
            ResponseEntity<String> yodaFormResponse = restTemplate.getForEntity(String.format("/sentences/%d/yodaTalk", generatedSentence.getBody().getId()), String.class);
            String yodaTalk = yodaFormResponse.getBody();

            assertThatJson(yodaTalk).node("sentence.text").matches(anyOf(containsString("Cold"), containsString("Nice"), containsString("Cookie")));
            assertThatJson(yodaTalk).node("sentence.text").matches(anyOf(startsWith("Summer"), startsWith("Cookie"), startsWith("Act")));
            assertThatJson(yodaTalk).node("sentence.text").matches(anyOf(endsWith("Is"), endsWith("Stay"), endsWith("Run"), endsWith("Act")));
        }
    }
}
