package com.galvanize.springreqresponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TextService.class)
public class TextServiceTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void testCamelize() throws Exception {

        RequestBuilder rq = MockMvcRequestBuilders.get("/camelize").
                queryParam("original","snake_case").
                queryParam("initialCap","false").
                accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).
                andExpect(status().isOk())
        .andExpect(content().string("snakeCase"));
    }

    @Test
    public void testCamelizeWithInitalCaps() throws Exception {

        RequestBuilder rq = MockMvcRequestBuilders.get("/camelize").
                queryParam("originalString","this_is_a_thing").
                queryParam("isInitialCaps","true").
                accept(MediaType.TEXT_PLAIN);
        this.mvc.perform(rq).
                andExpect(status().isOk())
                .andExpect(content().string("ThisIsAThing"));
    }

}
