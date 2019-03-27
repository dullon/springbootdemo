package com.dullon.demoboot.controller;

import com.dullon.demoboot.DemoBootApplication;
import com.dullon.demoboot.mapper.DemoBootRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoBootApplication.class)
@WebAppConfiguration//(value = "templates")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DemoBootControllerTest {

    public static final String module = DemoBootControllerTest.class.getName();
    Logger logger = LogManager.getLogger(module);

    @Resource
    private DemoBootRepository dbr ;

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    //设置mockMvc
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void hello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/abc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("demo"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("readerList"))
                .andDo(MockMvcResultHandlers.print());

                /*.andExpect(MockMvcResultMatchers.model().attribute("readerList", Matchers.is(Matchers.empty()))*/

    }

    /*@Test(expected = HttpClientErrorException.class)
    public void readerBooks() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject("http://localhost:8080/test/abc",String.class);
            fail("Should result in HTTP 404");
        }catch (HttpClientErrorException he){
            assertEquals(HttpStatus.NO_CONTENT,he.getStatusCode());
            throw he;
        }

    }*/

    @Test
    public void addToReadingList() {
    }
}