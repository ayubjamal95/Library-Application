package com.librarymanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.librarymanagement.dto.BookDto;
import com.librarymanagement.dto.BookListResultResponse;
import com.librarymanagement.dto.UserDto;
import com.librarymanagement.dto.UserListResultResponse;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.BorrowedRepository;
import com.librarymanagement.repository.UserRepository;
import com.librarymanagement.service.BookService;

import com.librarymanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;

@WebMvcTest(LibraryManagementController.class)
public class LibraryManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserService userService;
    @MockBean
    private BorrowedRepository borrowedRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryManagementController libraryManagementController;

    @Test
    void getAvailableBooks() throws Exception {
        BookListResultResponse expectedResult = new BookListResultResponse();
        expectedResult.setBooks(Arrays.asList(new BookDto("Title", "Author", "Genre", "Publisher")));
        when(bookService.getAvailableBooks()).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/libraryManagement/books/available")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        BookListResultResponse actualResult = new ObjectMapper().readValue(content, BookListResultResponse.class);
        assertEquals(expectedResult.getBooks().size(), actualResult.getBooks().size());
    }

    @Test
    void getUsers() throws Exception {

        UserListResultResponse expectedResult = new UserListResultResponse();
        expectedResult.setUsers(Arrays.asList(new UserDto("Aexi", "Liam", LocalDate.parse("2024-04-14"), LocalDate.parse("2024-04-14"),"m")));
        when(userService.getUsersWithBorrowedBooks()).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/libraryManagement/users/borrowed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        UserListResultResponse actualResult = mapper.readValue(content, UserListResultResponse.class);
        assertEquals(expectedResult.getUsers().size(), actualResult.getUsers().size());
    }

    @Test
    void getNonTerminatedAndNonBorrowedUsers() throws Exception {

        UserListResultResponse expectedResult = new UserListResultResponse();
        expectedResult.setUsers(Arrays.asList(new UserDto("Aexi", "Liam", LocalDate.parse("2024-04-14"), LocalDate.parse("2024-04-14"),"m")));
        when(userService.getNonTerminatedAndNonBorrowedUsers()).thenReturn(expectedResult);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/libraryManagement/users/non-terminated/not-borrowed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        UserListResultResponse actualResult = mapper.readValue(content, UserListResultResponse.class);

        assertEquals(expectedResult.getUsers().size(), actualResult.getUsers().size());
    }

    @Test
    void getUsersWhoBorrowedOnDate() throws Exception {
        UserListResultResponse expectedResult = new UserListResultResponse();
        expectedResult.setUsers(Arrays.asList(new UserDto("Aexi", "Liam", LocalDate.parse("2024-04-14"), LocalDate.parse("2024-09-14"),"m")));
        when(userService.getUsersWhoBorrowedOnDate(LocalDate.parse("2024-04-14"))).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/libraryManagement/users/borrowed/date")
                        .param("date","2024-04-14")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        UserListResultResponse actualResult = mapper.readValue(content, UserListResultResponse.class);
        assertEquals(expectedResult.getUsers().size(), actualResult.getUsers().size());
    }

    @Test
    void getUsersWhoBorrowedInDateRange() throws Exception {

        UserListResultResponse expectedResult = new UserListResultResponse();
        expectedResult.setUsers(Arrays.asList(new UserDto("Aexi", "Liam", LocalDate.parse("2024-04-14"), LocalDate.parse("2024-09-14"),"m")));
        when(userService.getUsersWhoBorrowedInDateRange(LocalDate.parse("2008-05-14"),LocalDate.parse("2024-01-01"))).thenReturn(expectedResult);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/libraryManagement/users/borrowed/dateRange")
                        .param("fromDate","2008-05-14")
                        .param("toDate","2024-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        UserListResultResponse actualResult = mapper.readValue(content, UserListResultResponse.class);
        assertEquals(expectedResult.getUsers().size(), actualResult.getUsers().size());
    }
}
