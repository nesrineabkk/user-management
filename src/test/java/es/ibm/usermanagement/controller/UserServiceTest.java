package es.ibm.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ibm.usermanagement.dto.UserRequest;
import es.ibm.usermanagement.entity.User;
import es.ibm.usermanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUuid("abcd1234efgh5678");
        user.setFirstName("Nesrine");
        user.setLastName("Abdelkarim");
        user.setAge(30);
        user.setSubscribed(true);
        user.setPostalCode("12345");
        user.setCreatedAt(LocalDateTime.now());
    }


    @Test
    void testGetAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value("abcd1234efgh5678"))
                .andExpect(jsonPath("$[0].first_name").value("Nesrine"));    }

    @Test
    void testSearchUsers() throws Exception {
        Page<User> page = new PageImpl<>(List.of(user), PageRequest.of(0, 10), 1);
        Mockito.when(userService.searchUsers(any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/users/search")
                        .param("firstName", "Nesrine")
                        .param("age", "30")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].first_name").value("Nesrine"));}

    @Test
    void testCreateUser() throws Exception {
        UserRequest request = new UserRequest();
        request.setFirstName("Tomas");
        request.setLastName("Rodriguez");
        request.setAge(28);
        request.setSubscribed(true);
        request.setPostalCode("75001");

        User createdUser = new User();
        createdUser.setUuid("xyz789xyz123lmno");
        createdUser.setFirstName("Tomas");
        createdUser.setLastName("Cruz");
        createdUser.setAge(28);
        createdUser.setSubscribed(true);
        createdUser.setPostalCode("75001");
        createdUser.setCreatedAt(LocalDateTime.now());

        Mockito.when(userService.createUser(any(UserRequest.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").value("xyz789xyz123lmno"))
                .andExpect(jsonPath("$.first_name").value("Tomas"));    }


    @Test
    void testCreateUser_InvalidInput_ShouldReturnBadRequest() throws Exception {
        UserRequest invalidRequest = new UserRequest();
        invalidRequest.setFirstName("");
        invalidRequest.setLastName("");
        invalidRequest.setAge(-5);
        invalidRequest.setPostalCode("");
        invalidRequest.setSubscribed(false);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

}