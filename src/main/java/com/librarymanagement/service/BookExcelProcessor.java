package com.librarymanagement.service;

import com.librarymanagement.model.Books;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.utils.Constants;
import org.springframework.util.ObjectUtils;

public class BookExcelProcessor implements ExcelProcessor {

    private final BookRepository bookRepository;
    public BookExcelProcessor(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Override
    public void process(String[] row) {

        if (!validateRow(row, Constants.EXPECTED_ROW_INDICES_BOOK)){
            return;
        }
        Books book = new Books();
        String title = row[0].trim();
        String author = row[1].trim();
        String genre = row[2].trim();
        String publisher = row[3].trim();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublisher(publisher);
        Books existingBookFound = bookRepository.findByTitle(title);
        if (ObjectUtils.isEmpty(existingBookFound)){
            bookRepository.save(book);
        }
    }
    @Override
    public boolean validateHeader(String[] headerRow) {

        if (headerRow == null || headerRow.length != Constants.EXPECTED_HEADERS_BOOKS.length) {
            return false;
        }
        for (int i = 0; i < Constants.EXPECTED_HEADERS_BOOKS.length; i++) {
            String cell = headerRow[i];
            if (cell == null || !Constants.EXPECTED_HEADERS_BOOKS[i].equals(cell)) {
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
}
