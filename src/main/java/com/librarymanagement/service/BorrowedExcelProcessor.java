package com.librarymanagement.service;

import com.librarymanagement.model.Books;
import com.librarymanagement.model.Borrowed;
import com.librarymanagement.model.Users;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.BorrowedRepository;
import com.librarymanagement.repository.UserRepository;
import com.librarymanagement.utils.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BorrowedExcelProcessor implements ExcelProcessor {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BorrowedRepository borrowedRepository;
    private final DateTimeFormatter formatter =DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);

    public BorrowedExcelProcessor (UserRepository userRepository,
                                   BookRepository bookRepository,
                                   BorrowedRepository borrowedRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.borrowedRepository = borrowedRepository;
    }
    @Override
    public void process(String[] row) {

        if (!validateRow(row, Constants.EXPECTED_ROW_INDICES_BORROWED)){
            return;
        }
        Borrowed borrowed = new Borrowed();
        borrowed.setUser(referenceUser(row[0].trim()));
        borrowed.setBook(referenceBook(row[1].trim()));
        borrowed.setBorrowedFrom(parseDate(row[2]));
        borrowed.setBorrowedTo(parseDate(row[3]));
        try {
            borrowedRepository.save(borrowed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean validateHeader(String[] headerRow) {

        if (headerRow == null || headerRow.length != Constants.EXPECTED_HEADERS_BORROWED.length) {
            return false;
        }
        for (int i = 0; i < Constants.EXPECTED_HEADERS_BORROWED.length; i++) {
            String cell = headerRow[i];
            if (cell == null || !Constants.EXPECTED_HEADERS_BORROWED[i].equals(cell)) {
                return false;
            }
        }
        return true;
    }
    public boolean validateRow(String[] row, int requiredCellIndices){

        if (row.length!= requiredCellIndices && row == null) {
            return false;
        }
        return true;
    }

    private Users referenceUser(String userName ) {
        String[] nameParts = userName.split(",");
        Users userFound= userRepository.findByNameAndLastName(nameParts[0],nameParts[1]);
        return userFound;
    }
    private Books referenceBook(String bookName) {
       Books bookFound= bookRepository.findByTitle(bookName);
       return  bookFound;
    }
    private LocalDate parseDate(String dateString) {
        if (!dateString.isEmpty()) {
            return LocalDate.parse(dateString, formatter);
        }
        return null;
    }
}
