package com.librarymanagement.service;

import com.librarymanagement.dto.BookDto;
import com.librarymanagement.dto.BookListResultResponse;
import com.librarymanagement.model.Books;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.utils.ApiResponseUtils;
import com.librarymanagement.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    public BookListResultResponse getAvailableBooks(){
        BookListResultResponse bookListResultResponse = new BookListResultResponse();
        try {
            List<Books> availableBooks = bookRepository.findAllAvailableBooks();
            if (!availableBooks.isEmpty()) {
                bookListResultResponse.setBooks(availableBooks.stream()
                        .map(this::mapBookToBookDto)
                        .collect(Collectors.toList()));
                ApiResponseUtils.success(Constants.BOOK_SUCCESS_MESSAGE, bookListResultResponse);
            } else {
                ApiResponseUtils.success(Constants.BOOK_FAIL_MESSAGE, bookListResultResponse);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ApiResponseUtils.failure(Constants.BOOK_EXCEPTION_MESSAGE, bookListResultResponse);
        }
        return  bookListResultResponse;
    }

    private BookDto mapBookToBookDto(Books book){
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPublisher(book.getPublisher());
        bookDto.setGenre(book.getGenre());
        bookDto.setTitle(book.getTitle());
        return bookDto;
    }
}
