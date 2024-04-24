package com.librarymanagement.service;

import com.librarymanagement.model.Users;
import com.librarymanagement.repository.UserRepository;
import com.librarymanagement.utils.Constants;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class UserExcelProcessor implements ExcelProcessor {

   private final UserRepository userRepository;
   private final DateTimeFormatter formatter =DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
    public UserExcelProcessor(UserRepository userRepository){

        this.userRepository = userRepository;
    }
    @Override
    public void process(String [] row) {
        if (!validateRow(row,Constants.EXPECTED_ROW_INDICES_USERS)){
            return;
        }
        Users user = new Users();
        String name = row[0].trim();
        String lastName = row[1].trim();
        LocalDate memberSince = parseDate(row[2]);
        LocalDate memberTill = parseDate(row[3]);
        String gender = row[4].trim();
        Users existingUser = userRepository.findByNameAndLastName(name, lastName);
        if (ObjectUtils.isEmpty(existingUser)) {
            user.setName(name);
            user.setLastName(lastName);
            user.setMemberSince(memberSince);
            user.setMemberTill(memberTill);
            user.setGender(gender);
            userRepository.save(user);
        }
    }
    @Override
    public boolean validateHeader(String[] headerRow) {

        if (headerRow == null || headerRow.length != Constants.EXPECTED_HEADERS_USERS.length) {
            return false;
        }
        for (int i = 0; i < Constants.EXPECTED_HEADERS_USERS.length; i++) {
            String cell = headerRow[i];
            if (cell == null || !Constants.EXPECTED_HEADERS_USERS[i].equals(cell)) {
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
    private LocalDate parseDate(String dateString) {
        if (!dateString.isEmpty()) {
            return LocalDate.parse(dateString, formatter);
        }
        return null;
    }

}
