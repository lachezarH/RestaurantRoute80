package project.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    public void testNewComment() throws Exception {
        mockMvc.perform(
                post("/comments/save").
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED).
                        param("author", "hello, softuni!!!!!!!").
                        param("description", "hello, softuni!!!!!!! hello, softuni!!!!!!! hello, softuni!!!!!!!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testNewCommentFailed() throws Exception {
        mockMvc.perform(
                post("/comments/save").
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED).
                        param("author", "hello, softuni!!!!!!!").
                        param("description", "sh"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments/new"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testCommentsGetRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/comments"))
                .andExpect(status().isOk())
                .andExpect(view().name("comments"));

    }

    @Test
    @WithMockUser(username = "user")
    public void testNewCommentsGetRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/comments/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("new-comments"));

    }



}
