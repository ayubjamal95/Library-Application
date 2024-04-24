package com.librarymanagement.utils;

public class Constants {
    public static final String SUCCESS ="Success";
    public static final String FAILED ="Failed";
    public static final String SUCCESS_CODE ="00";
    public static final String FAILED_CODE ="01";
    public static final String USER_SUCCESS_MESSAGE ="Users fetched successfully";
    public static final String USER_FAIL_MESSAGE ="No such user was found";
    public static final String USER_EXCEPTION_MESSAGE ="Exception occurred while fetching users";
    public static final String BOOK_SUCCESS_MESSAGE ="Books fetched successfully";
    public static final String BOOK_FAIL_MESSAGE ="No such book was found";
    public static final String BOOK_EXCEPTION_MESSAGE ="Exception occurred while fetching books";
    public static final String EXCEL_HEADERS_MISMATCH ="Excel sheet headers do not match";
    public static final String[] EXPECTED_HEADERS_USERS = {"Name","First name","Member since", "Member till", "Gender"};
    public static final int EXPECTED_ROW_INDICES_USERS = 5;
    public static final String[] EXPECTED_HEADERS_BOOKS = {"Title","Author","Genre","Publisher"};
    public static final int EXPECTED_ROW_INDICES_BOOK = 4;
    public static final String[] EXPECTED_HEADERS_BORROWED = {"Borrower","Book","borrowed from","borrowed to"};
    public static final int EXPECTED_ROW_INDICES_BORROWED = 4;
    public static final String EXPECTED_EXCEL_EXTENSION = "csv";
    public static final String EXPECTED_EXCEL_EXTENSION_ERROR_MSG = "only .csv files are supported";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
}
