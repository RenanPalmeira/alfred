package com.alfred.templates.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TemplateControllerTests {

    @Autowired
    private WebTestClient client;

    @Test
    public void test_get_to_list_templates() throws Exception {
        client.get()
                .uri("/template/")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void test_get_template() throws Exception {
        client.get()
                .uri("/template/forgetPassword")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void test_post_templates() throws Exception {
        client.post()
                .uri("/template/")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void test_put_template() throws Exception {
        client.put()
                .uri("/template/forgetPassword")
                .exchange()
                .expectStatus().isOk();
    }
}
