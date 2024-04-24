package com.librarymanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String lastName;
    private LocalDate memberSince;
    private LocalDate memberTill;
    private String gender;
}
