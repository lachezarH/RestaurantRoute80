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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user")
    public void testHomeGetRequest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/home"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testContactsGetRequest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(view().name("contacts"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testAboutUsGetRequest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/about-us"))
                .andExpect(status().isOk())
                .andExpect(view().name("about-us"));
    }
    @Test
    @WithMockUser(username = "user")
    public void testGalleryGetRequest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/gallery"))
                .andExpect(status().isOk())
                .andExpect(view().name("gallery"));
    }
}
