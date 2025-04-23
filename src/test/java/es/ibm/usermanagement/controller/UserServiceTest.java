package es.ibm.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ibm.usermanagement.dto.UserRequest;
import es.ibm.usermanagement.entity.User;
import es.ibm.usermanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        User user = new User();
        user.setUuid("1234567890abcdef");
        user.setFirstName("Nesrine");

        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].first_name").value("Nesrine"))
                .andExpect(jsonPath("$[0].uuid").value("1234567890abcdef"));
    }

    @Test
    void testSearchUsers() throws Exception {
        User user = new User();
        user.setUuid("1234567890abcdef");
        user.setFirstName("Nesrine");
        user.setAge(26);

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(List.of(user), pageable, 1);

        Mockito.when(userService.searchUsers(eq("Nesrine"), eq(26), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/users/search")
                        .param("firstName", "Nesrine")
                        .param("age", "26")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].first_name").value("Nesrine"))
                .andExpect(jsonPath("$.content[0].age").value(26))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void testCreateUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Nesrine");
        userRequest.setLastName("Abdelkarim");
        userRequest.setAge(26);
        userRequest.setPostalCode("12345");

        User savedUser = new User();
        savedUser.setUuid("1234567890abcdef");
        savedUser.setFirstName("Nesrine");
        savedUser.setLastName("Abdelkarim");
        savedUser.setAge(26);
        savedUser.setPostalCode("12345");

        Mockito.when(userService.createUser(any(UserRequest.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("Nesrine"))
                .andExpect(jsonPath("$.last_name").value("Abdelkarim"))
                .andExpect(jsonPath("$.age").value(26))
                .andExpect(jsonPath("$.postal_code").value("12345"));
    }


}