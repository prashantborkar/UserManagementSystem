package com.assignment.telia;

import com.assignment.telia.model.UserInformation;
import com.assignment.telia.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public UserControllerITests() {
    }

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
    }

    @Test
    public void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception{

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        // given - precondition or setup
        UserInformation user = UserInformation.builder()
                .personalNumber(01234567)
                .emailAddress("pfborkar@gmail.com")
                .dateOfBirth(date)
                .fullName("Test Name Prashant")
                .build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));


        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName",
                        is(user.getFullName())))
                .andExpect(jsonPath("$.emailAddress",
                        is(user.getEmailAddress())));


    }


    @Test
    public void givenListOfUser_whenGetAllUsers_thenReturnUsersList() throws Exception{
        // given - precondition or setup
        List<UserInformation> listOfUsers = new ArrayList<>();
        listOfUsers.add(UserInformation.builder().fullName("Prashant").emailAddress("pfborkar@gmail.com").dateOfBirth(new Date(1996-11-10)).personalNumber(1234566).build());
        listOfUsers.add(UserInformation.builder().fullName("Rokesh").emailAddress("rokesh@gmail.com").dateOfBirth(new Date(1997-11-10)).personalNumber(1238566).build());
        userRepository.saveAll(listOfUsers);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/users"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfUsers.size())));

    }

    @Test
    public void givenInvalidPersonalNumber_whenGetUserByPersonalNumber_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long personalNumber = 1234567;
        UserInformation user = UserInformation.builder()
                .personalNumber(01234567)
                .emailAddress("pfborkar@gmail.com")
                .dateOfBirth(new Date(1996-11-10))
                .fullName("Test Name Prashant")
                .build();

        userRepository.save(user);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/users/{personalNumber}", personalNumber));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void givenInvalidPersonalNumber_whenGetPersonalNumber_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long personalNumber = 12345678;
        UserInformation user = UserInformation.builder()
                .personalNumber(01234567)
                .emailAddress("pfborkar@gmail.com")
                .dateOfBirth(new Date(1996-11-10))
                .fullName("Test Name Prashant")
                .build();

        userRepository.save(user);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/users/{personalNumber}", personalNumber));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void givenUpdatedUser_whenUpdateUser_thenReturnUpdateUserObject() throws Exception{
        // given - precondition or setup
        UserInformation user = UserInformation.builder()
                .personalNumber(01234567)
                .emailAddress("pfborkar@gmail.com")
                .dateOfBirth(new Date(1996-11-10))
                .fullName("Test Name Prashant")
                .build();

        userRepository.save(user);

        UserInformation updatedUser = UserInformation.builder()
                .personalNumber(01234567)
                .emailAddress("pfborkar@gmail.com")
                .dateOfBirth(new Date(1995-11-10))
                .fullName("Test Name Prishu")
                .build();

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/user/{personalId}", user.getPersonalNumber())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.fullName", is(updatedUser.getFullName())))
                .andExpect(jsonPath("$.emailAddress", is(updatedUser.getEmailAddress())));
    }

    @Test
    public void givenUpdatedUser_whenUpdateUser_thenReturn404() throws Exception{
        // given - precondition or setup
        long personalNumber = 12345678;
        UserInformation user = UserInformation.builder()
                .personalNumber(01234567)
                .emailAddress("pfborkar@gmail.com")
                .dateOfBirth(new Date(1996-11-10))
                .fullName("Test Name Prashant")
                .build();

        userRepository.save(user);

        UserInformation updatedUser = UserInformation.builder()
                .personalNumber(01234566)
                .emailAddress("pborkar@gmail.com")
                .dateOfBirth(new Date(1997-11-10))
                .fullName("Test Name Prishu")
                .build();

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/user/{personalId}", personalNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void givenPersonalNumber_whenDeleteUser_thenReturn200() throws Exception{
        // given - precondition or setup
        UserInformation user = UserInformation.builder()
                .personalNumber(342391)
                .emailAddress("pfborkar@gmail.com")
                .dateOfBirth(new Date(1996-11-10))
                .fullName("Test Name Prashant")
                .build();

        userRepository.save(user);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/user/{personalNumber}", user.getPersonalNumber()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}