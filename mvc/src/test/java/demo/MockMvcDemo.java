package demo;

import demo.config.MvcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes = { MvcConfig.class })
public class MockMvcDemo {

    private WebApplicationContext ctx;

    @Autowired
    public void setCtx(WebApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Test
    public void controllerTest() {
        var controller = ctx.getBean(PostController.class);

        controller.savePost(new Post(1L, "Title", "Text"));
    }

    @Test
    public void mockMvcExampleTest() throws Exception {

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        String json = "{ \"id\": 3, \"title\": \"hello\" }";

        MvcResult result = mvc.perform(
                post("/posts")
                        .content(json)
                        .header("Content-type", "application/json"))
                .andReturn();

        var response = result.getResponse();

        System.out.println(response.getStatus());
        System.out.println(response.getHeader("Content-type"));
        System.out.println(response.getContentAsString());
   }

}