package com.alfred.templates.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(ctx)
            .build();
    }

    @Test
    public void test_get_to_list_templates() throws Exception {
        this.mockMvc.perform(get("/template/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_get_template() throws Exception {
        this.mockMvc.perform(get("/template/forgetPassword"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_post_templates() throws Exception {
        this.mockMvc.perform(post("/template/"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_put_template() throws Exception {
        this.mockMvc.perform(put("/template/forgetPassword"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
