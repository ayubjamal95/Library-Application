package com.librarymanagement.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CsvDataLoaderJob extends ExcelLoaderService {
    @Scheduled(cron = "0 0 0 * * *")
    public void runJob() throws Exception {
        run();
    }
}
