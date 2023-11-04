package com.example.sociospring.controller;

import com.example.sociospring.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        // You can use this method to set up any data or configurations for your tests.
        // For example, create test data or configure the service.
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON) // Set the content type to JSON
                        .content("{"
                                + "\"userName\":\"john\","
                                + "\"email\":\"john@example.com\","
                                + "\"hashPassword\":\"ds£$£%£%£$ASAS\","
                                + "\"bio\":\"relentless boy\","
                                + "\"profilePictureUrl\":\"https://www.google.com/url?sa=i&url=https%3A%2F%2Fsproutsocial.com%2Fglossary%2Fprofile-picture%2F&psig=AOvVaw0vn1Wqn8Hk78LPBqU3VXhb&ust=1696108080703000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCKDgopLd0IEDFQAAAAAdAAAAABA\","
                                + "\"updatedAt\":\"2023-10-14T00:00:00.000Z\""
                                + "}"))
                .andExpect(MockMvcResultMatchers.status().is(200));
        // You can add more assertions here to test the response body, headers, etc.
    }
    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON) // Set the content type to JSON
                        .content("{"
                                + "\"userName\":\"zain\""

                                + "}"))
                .andExpect(MockMvcResultMatchers. status().is(200));
        // You can add more assertions here to test the response body, headers, etc.
    }
    // Add more test methods for different scenarios as needed
}
