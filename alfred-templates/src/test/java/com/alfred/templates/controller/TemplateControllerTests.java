package com.alfred.templates.controller;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TemplateControllerTests {

    @MockBean
    private AmazonSimpleEmailServiceAsync ses;

    @Autowired
    private WebTestClient client;

    @Test
    public void test_get_to_list_templates() throws Exception {

        TemplateMetadata aPrettyName = new TemplateMetadata()
                .withName("aPrettyName")
                .withCreatedTimestamp(new Date());

        Future<ListTemplatesResult> listTemplatesResultCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return new ListTemplatesResult()
                    .withTemplatesMetadata(Collections.singletonList(aPrettyName));
        });

        when(ses.listTemplatesAsync(any(ListTemplatesRequest.class))).thenReturn(listTemplatesResultCompletableFuture);

        client.get()
                .uri("/template/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isNotEmpty()
                .jsonPath("$[0].created_timestamp").isNotEmpty()
                .jsonPath("$", hasSize(1));
    }

    @Test
    public void test_get_template() throws Exception {

        Template template = new Template()
                .withTemplateName("aPrettyName")
                .withSubjectPart("text")
                .withHtmlPart("html")
                .withTextPart("test");

        Future<GetTemplateResult> listTemplatesResultCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return new GetTemplateResult()
                    .withTemplate(template);
        });

        when(ses.getTemplateAsync(any(GetTemplateRequest.class))).thenReturn(listTemplatesResultCompletableFuture);

        client.get()
                .uri("/template/forgetPassword")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.template_name").isNotEmpty()
                .jsonPath("$.subject_part").isNotEmpty()
                .jsonPath("$.text_part").isNotEmpty()
                .jsonPath("$.html_part").isNotEmpty()
                .jsonPath("$", hasSize(4));
    }

    @Test
    public void test_post_templates() throws Exception {
        Future<CreateTemplateResult> updateTemplateResultCompletableFuture = CompletableFuture.supplyAsync(CreateTemplateResult::new);
        when(ses.createTemplateAsync(any(CreateTemplateRequest.class))).thenReturn(updateTemplateResultCompletableFuture);

        String jsonForm = "{\"template_name\": \"aMockTemplate\", \"subject_part\": \"aMockTemplate\", \"text_part\": \"aMockTemplate\", \"html_part\": \"aMockTemplate\"}";

        client.post()
                .uri("/template/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(jsonForm))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void test_put_template() throws Exception {
        Future<UpdateTemplateResult> updateTemplateResultCompletableFuture = CompletableFuture.supplyAsync(UpdateTemplateResult::new);
        when(ses.updateTemplateAsync(any(UpdateTemplateRequest.class))).thenReturn(updateTemplateResultCompletableFuture);

        String jsonForm = "{\"template_name\": \"aMockTemplate\", \"subject_part\": \"aMockTemplate\", \"text_part\": \"aMockTemplate\", \"html_part\": \"aMockTemplate\"}";

        client.put()
                .uri("/template/forgetPassword")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(jsonForm))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void test_post_templates_error_with_empty_fields() throws Exception {
        Future<CreateTemplateResult> updateTemplateResultCompletableFuture = CompletableFuture.supplyAsync(CreateTemplateResult::new);
        when(ses.createTemplateAsync(any(CreateTemplateRequest.class))).thenReturn(updateTemplateResultCompletableFuture);

        String jsonForm = "{\"template_name\": \"\", \"subject_part\": \"\", \"text_part\": \"\", \"html_part\": \"\"}";

        client.post()
                .uri("/template/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(jsonForm))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void test_put_template_error_with_empty_fields() throws Exception {
        Future<UpdateTemplateResult> updateTemplateResultCompletableFuture = CompletableFuture.supplyAsync(UpdateTemplateResult::new);
        when(ses.updateTemplateAsync(any(UpdateTemplateRequest.class))).thenReturn(updateTemplateResultCompletableFuture);

        String jsonForm = "{\"template_name\": \"\", \"subject_part\": \"\", \"text_part\": \"\", \"html_part\": \"\"}";

        client.put()
                .uri("/template/forgetPassword")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(jsonForm))
                .exchange()
                .expectStatus().isBadRequest();
    }
}
