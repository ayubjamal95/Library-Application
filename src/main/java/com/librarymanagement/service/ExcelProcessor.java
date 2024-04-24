package com.librarymanagement.service;


public interface ExcelProcessor {
    void process(String[] row);
    boolean validateHeader(String [] headerRow);
}
