package com.librarymanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Borrowed")
public class Borrowed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "BookId")
    private Books book;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private Users user;
    @Column(name = "BorrowedFrom")
    private LocalDate borrowedFrom;
    @Column(name = "BorrowedTo")
    private LocalDate borrowedTo;
}
