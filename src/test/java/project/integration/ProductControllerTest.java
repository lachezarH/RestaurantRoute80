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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user")
    public void testAddConfirm() throws Exception {
        mockMvc.perform(
                post("/products/add").
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED).
                        param("name", "hello").
                        param("description", "hello, softuni!!!!!!! hello, softuni!!!!!!! hello, softuni!!!!!!!").
                        param("category", "SALADS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/menu"));
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = {"USER","ADMIN"})
    public void testAddGetRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-add"));

    }
}
