package com.librarymanagement.service;

import com.librarymanagement.dto.UserDto;
import com.librarymanagement.dto.UserListResultResponse;
import com.librarymanagement.model.Borrowed;
import com.librarymanagement.model.Users;
import com.librarymanagement.repository.BorrowedRepository;
import com.librarymanagement.repository.UserRepository;
import com.librarymanagement.utils.ApiResponseUtils;
import com.librarymanagement.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    BorrowedRepository borrowedRepository;
    @Autowired
    UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserListResultResponse getUsersWithBorrowedBooks() {
        UserListResultResponse userListResultResponse = new UserListResultResponse();
        try {
            List<Long> userIds = borrowedRepository.findAll().stream()
                    .map(Borrowed::getUser)
                    .map(Users::getId)
                    .distinct()
                    .collect(Collectors.toList());

            List<Users> users = userRepository.findAllById(userIds);
            if (!users.isEmpty()) {
                userListResultResponse.setUsers(users.stream().map(this::mapUserToUserDto).collect(Collectors.toList()));
                ApiResponseUtils.success(Constants.USER_SUCCESS_MESSAGE, userListResultResponse);
            } else {
                ApiResponseUtils.success(Constants.USER_FAIL_MESSAGE, userListResultResponse);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            ApiResponseUtils.failure(Constants.USER_EXCEPTION_MESSAGE, userListResultResponse);
            return userListResultResponse;
        }
        return userListResultResponse;
    }

    public UserListResultResponse getNonTerminatedAndNonBorrowedUsers() {

        UserListResultResponse userListResultResponse = new UserListResultResponse();
        try {
            List<Users> nonTerminatedUsers = userRepository.findNonTerminatedUsers();
            List<Users> nonBorrowingUsers = nonTerminatedUsers.stream()
                    .filter(user -> CollectionUtils.isEmpty(borrowedRepository.findByUserId(user.getId())))
                    .collect(Collectors.toList());

            if (!nonBorrowingUsers.isEmpty()) {
                userListResultResponse.setUsers(nonBorrowingUsers.stream()
                        .map(this::mapUserToUserDto)
                        .collect(Collectors.toList()));

                ApiResponseUtils.success(Constants.USER_SUCCESS_MESSAGE, userListResultResponse);
            } else {
                ApiResponseUtils.success(Constants.USER_FAIL_MESSAGE, userListResultResponse);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            ApiResponseUtils.failure(Constants.USER_EXCEPTION_MESSAGE, userListResultResponse);
        }
        return userListResultResponse;
    }

    public UserListResultResponse getUsersWhoBorrowedOnDate(LocalDate date) {
        UserListResultResponse userListResultResponse = new UserListResultResponse();
        try {
            List<Borrowed> borrowedOnDate = borrowedRepository.findByBorrowedFrom(date);
            List<Long> userIds = borrowedOnDate.stream()
                    .map(Borrowed::getUser)
                    .map(Users::getId)
                    .distinct()
                    .collect(Collectors.toList());

            List<Users> users = userRepository.findAllById(userIds);
            if (!users.isEmpty()) {
                userListResultResponse.setUsers(users.stream()
                        .map(this::mapUserToUserDto)
                        .collect(Collectors.toList()));
                ApiResponseUtils.success(Constants.USER_SUCCESS_MESSAGE, userListResultResponse);
            } else {
                ApiResponseUtils.success(Constants.USER_FAIL_MESSAGE, userListResultResponse);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            ApiResponseUtils.failure(Constants.USER_EXCEPTION_MESSAGE, userListResultResponse);
        }
        return userListResultResponse;
    }

    public UserListResultResponse getUsersWhoBorrowedInDateRange(LocalDate fromDate, LocalDate toDate) {

        UserListResultResponse userListResultResponse = new UserListResultResponse();
        try {
            List<Borrowed> borrowedInRange = borrowedRepository.findByBorrowedDateRange(fromDate, toDate);
            List<Long> userIds = borrowedInRange.stream()
                    .map(Borrowed::getUser)
                    .map(Users::getId)
                    .distinct()
                    .collect(Collectors.toList());

            List<Users> users = userRepository.findAllById(userIds);
            if (!users.isEmpty()) {
                userListResultResponse.setUsers(users.stream()
                        .map(this::mapUserToUserDto)
                        .collect(Collectors.toList()));
                ApiResponseUtils.success(Constants.USER_SUCCESS_MESSAGE, userListResultResponse);
            } else {
                ApiResponseUtils.success(Constants.USER_FAIL_MESSAGE, userListResultResponse);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ApiResponseUtils.failure(Constants.USER_EXCEPTION_MESSAGE, userListResultResponse);
        }
        return userListResultResponse;
    }

    private UserDto mapUserToUserDto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setMemberSince(user.getMemberSince());
        userDto.setMemberTill(user.getMemberTill());
        userDto.setGender(user.getGender());
        return userDto;
    }
}
