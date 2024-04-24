package com.librarymanagement.repository;

import com.librarymanagement.model.Borrowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowedRepository extends JpaRepository<Borrowed,Long> {
    List<Borrowed> findByBorrowedFrom(LocalDate date);
    @Query("SELECT b FROM Borrowed b WHERE b.borrowedFrom BETWEEN ?1 AND ?2")
    List<Borrowed> findByBorrowedDateRange(LocalDate fromDate,LocalDate toDate);
    @Query("SELECT b FROM Borrowed b WHERE b.user.id = ?1 AND b.borrowedTo >= CURRENT_DATE")
    List<Borrowed> findByUserId(Long id);
}
