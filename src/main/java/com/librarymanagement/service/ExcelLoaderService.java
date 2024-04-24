package com.librarymanagement.service;

import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.BorrowedRepository;
import com.librarymanagement.repository.UserRepository;
import com.librarymanagement.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;



public class ExcelLoaderService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ExcelLoaderService.class);
    @Value("${path.excel.users}")
    private String usersExcelPath;

    @Value("${path.excel.books}")
    private String booksExcelPath;

    @Value("${path.excel.borrowed}")
    private String borrowedExcelPath;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BorrowedRepository borrowedRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUsersData(usersExcelPath);
        loadBooksData(booksExcelPath);
        loadBorrowedData(borrowedExcelPath);
    }
    private void loadUsersData(String usersExcelPath) {
        loadExcelFile(usersExcelPath, new UserExcelProcessor(userRepository));
    }
    private void loadBooksData(String booksExcelPath) {
        loadExcelFile(booksExcelPath, new BookExcelProcessor(bookRepository));
    }
    private void loadBorrowedData(String borrowedExcelPath) {
        loadExcelFile(borrowedExcelPath, new BorrowedExcelProcessor(userRepository,bookRepository,borrowedRepository));
    }
    private void loadExcelFile(String excelFilePath, ExcelProcessor processor)  {
        try{
            String fileExtension = excelFilePath.substring(excelFilePath.lastIndexOf(".") + 1);
            if (!fileExtension.equalsIgnoreCase(Constants.EXPECTED_EXCEL_EXTENSION)){
                throw new RuntimeException(Constants.EXPECTED_EXCEL_EXTENSION_ERROR_MSG);
            }
            InputStream inputStream = getClass().getResourceAsStream(excelFilePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String headerRow = reader.readLine();
            String[] headerRowArray = headerRow.split(",");
            boolean headersMatched =processor.validateHeader(headerRowArray);
            if (!headersMatched){
                throw new RuntimeException(Constants.EXCEL_HEADERS_MISMATCH);
            }
            String row;
            while ((row = reader.readLine()) != null && !row.isEmpty()) {

                String [] rowArray =row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int i = 0; i < rowArray.length; i++) {
                    rowArray[i] = rowArray[i].replaceAll("^\"|\"$", "");
                }
                processor.process(rowArray);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
