package com.librarymanagement.controller;

import com.librarymanagement.dto.BookListResultResponse;
import com.librarymanagement.dto.UserListResultResponse;
import com.librarymanagement.service.BookService;
import com.librarymanagement.service.UserService;
import com.librarymanagement.utils.RequestMappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;

@RestController
@RequestMapping(RequestMappingConstants.LIBRARY_MANAGEMENT_CONTROLLER)
public class LibraryManagementController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @GetMapping(value = RequestMappingConstants.USER_BORROWED_BOOKS)
    public UserListResultResponse getUsers (){
        UserListResultResponse userListResultResponse = userService.getUsersWithBorrowedBooks();
        return userListResultResponse;
    }
    @GetMapping(value = RequestMappingConstants.USER_NON_TERMINATED_NOT_BORROWED_BOOKS)
    public UserListResultResponse getNonTerminatedAndNonBorrowedUsers (){
        UserListResultResponse userListResultResponse = userService.getNonTerminatedAndNonBorrowedUsers();
        return userListResultResponse;
    }

    @GetMapping(value = RequestMappingConstants.USER_BORROWED_BOOKS_DATE)
    public UserListResultResponse getUsersWhoBorrowedOnDate (@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        UserListResultResponse userListResultResponse = userService.getUsersWhoBorrowedOnDate(date);
        return userListResultResponse;
    }
    @GetMapping(value = RequestMappingConstants.USER_BORROWED_BOOKS_DATE_RANGE)
    public UserListResultResponse getUsersWhoBorrowedInDateRange (@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                  @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate ){
        UserListResultResponse userListResultResponse = userService.getUsersWhoBorrowedInDateRange(fromDate,toDate);
        return userListResultResponse;
    }
    @GetMapping(value = RequestMappingConstants.NOT_BORROWED_BOOKS)
    public BookListResultResponse getAvailableBooks (){
        BookListResultResponse bookListResultResponse = bookService.getAvailableBooks();
        return bookListResultResponse;
    }

}
