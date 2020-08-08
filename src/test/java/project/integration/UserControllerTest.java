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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {



    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testLoginGetRequest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testRegisterGetRequest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser(username = "admin",roles = {"USER","ADMIN"})
    public void testInfoGetRequest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/info"))
                .andExpect(status().isOk())
                .andExpect(view().name("info-user"));
    }
}
